package com.alibou.websocket.chat;
import java.util.HashMap;

public class ChatAvatar {
    private HashMap<String,String> avatarHash = new HashMap<>();
    public void setAvatar(String sender, String url){
        avatarHash.put(sender, url);
    }
    public String getAvatar( String sender){
        return (String) avatarHash.get(sender);

    }
}
