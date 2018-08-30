package com.fupl.storage;

import com.fupl.entityes.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void init();

    String store(MultipartFile file);

//    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    List<String> findAllNames();

    String showFileContent(String name);

    List<FileEntity> findAll();

    String fullFileName(String fileName);

}
