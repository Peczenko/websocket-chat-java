package com.alibou.websocket.chat;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
public class AvatarLoad {
    private static String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = saveFile(file);
            String fileUrl = createFileUrl(filePath);
            System.out.println(fileUrl);
            return fileUrl;
        } catch (Exception e) {
            return null;
        }
    }

        private String saveFile(MultipartFile file) throws IOException{
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);
            return path.toString();
        }
        private String createFileUrl(String filePath){
            String urlPath = filePath.replace("\\", "/");
            return "http://localhost:8080/" + urlPath;
    }
    }



