package com.rseny.websocket.chat;

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
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return createFileUrl(saveFile(file));
        } catch (Exception e) {
            return null;
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);
        return path.toString();
    }

    private String createFileUrl(String filePath) {
        String urlPath = filePath.replace("\\", "/");
        return "http://localhost:8080/" + urlPath;
    }

    public static void deleteAvatar(String sender) {
        String avatarUrl = UsersInfo.getAvatarMap(sender);
        Path path = Paths.get(avatarUrl.replace("http://localhost:8080/", ""));
        try {
            Files.delete(path);
            System.out.println("URL DELETED");
        } catch (IOException e) {
            System.out.println("there is no such file");
        }
        UsersInfo.deleteAvatarMap(sender);
    }
}




