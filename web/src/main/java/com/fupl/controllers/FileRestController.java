package com.fupl.controllers;

import com.fupl.entityes.FileEntity;
import com.fupl.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class FileRestController {

    @Autowired
    StorageService storageService;

    @GetMapping(value = "/show/{filename}")
    public String showFileContent(@PathVariable(value = "filename") String fileName, HttpServletResponse response) {
        return storageService.showFileContent(storageService.fullFileName(fileName));
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping("/all")
    public List<FileEntity> findall() {
        return storageService.findAll();
    }

}

