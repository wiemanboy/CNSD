package com.wiemanboy.producer.presentation;

import com.wiemanboy.producer.application.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.fileDownload(file.getInputStream());
        return "File uploaded successfully";
    }
}
