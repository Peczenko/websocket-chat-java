package com.alibou.websocket.chat;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class ChatAvatar {
    public HashMap<String,String> avatarHash = new HashMap<>();
    public void setAvatar(String sender, String url){
        avatarHash.put(sender, url);
    }
    public String getAvatar( String sender){
        return (String) avatarHash.get(sender);
    }
}
