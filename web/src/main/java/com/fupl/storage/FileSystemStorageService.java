package com.fupl.storage;

import com.fupl.dao.repositories.FileRepository;
import com.fupl.entityes.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final String fileSufix;

    FileRepository fileRepository;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, FileRepository fileRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileSufix = properties.getFileSufix();
        this.fileRepository = fileRepository;
    }


    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String message = "You successfully uploaded ";
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        try {
            if (!(fileRepository.existsByName(filename))) {
                fileRepository.save(new FileEntity(filename, file.getBytes()));
            } else {
                message = "File is already present with specified name. Uploading is rejected ";
            }
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            saveFileToPath(file, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public List<String> findAllNames() {
        return fileRepository.findAllNames();
    }

    @Override
    public String showFileContent(String fileName) {

        String contentString = "";
        if (!(fileName == "")) {
            FileEntity fileEntity = fileRepository.findByName(fileName);
            Path path = load(fileName);
            saveFileToPath(path, fileName);
            contentString = getContentString(path);
        }

        return contentString;
    }

    private String getContentString(Path path) {
        String contentString = "";
        try {
            contentString = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentString;
    }

    private void saveFileToPath(Path path, String fileName) {
        File file = path.toFile();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileRepository.findByName(fileName).getContent());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String fullFileName(String fileName) {
        return fileName + fileSufix;
    }

    @Override
    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }


}
