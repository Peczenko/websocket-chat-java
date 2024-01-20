package com.rseny.websocket.chat;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @Getter
    private static HashMap<String, String> avatarMap = new HashMap<>();
    @Getter
    private static ArrayList<String> users = new ArrayList<>();
    private MessageType type;
    private String content;
    private String sender;
    private String avatarUrl;

    public static void setAvatarMap(String sender, String avatarUrl) {
            avatarMap.put(sender, avatarUrl);
    }

    public static String getAvatarUrlMap(String sender) {
        return avatarMap.get(sender);
    }

    public static void deleteAvatarMap(String sender) {
        avatarMap.remove(sender);
    }
    public static void setUsers(String sender) {
        users.add(sender);
    }
    public static boolean checkUsername(String username) {
        return !users.contains(username);
    }
    public static void deleteUsers(String sender) {
        users.remove(sender);
    }
}
