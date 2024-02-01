package com.rseny.websocket.chat;


import java.util.ArrayList;
import java.util.HashMap;


public class UsersInfo {
    private static HashMap<String, String> avatarMap = new HashMap<>();
    private static ArrayList<String> users = new ArrayList<>();

    public static void setUsers(String sender) {
        users.add(sender);
    }

    public static boolean checkUsername(String username) {
        return !users.contains(username);
    }

    public static void deleteUsers(String sender) {
        users.remove(sender);
    }

    public static void setAvatarMap(String sender, String avatarUrl) {
        avatarMap.put(sender, avatarUrl);
    }

    public static String getAvatarMap(String sender) {
        return avatarMap.get(sender);
    }


    public static void deleteAvatarMap(String sender) {
        avatarMap.remove(sender);
    }


}
