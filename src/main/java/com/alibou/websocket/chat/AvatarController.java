package com.alibou.websocket.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AvatarController{
    @Autowired
    private ChatAvatar chatAvatar;

    @MessageMapping("/avatar.setAvatar")
    public void setAvatar(@Payload AvatarRequest avatarRequest){
        chatAvatar.setAvatar(avatarRequest.getUsername(), avatarRequest.getAvatarUrl());
    }
    @MessageMapping("/avatar.getAvatar")
    @SendTo("/topic/public")
    public AvatarRequest getAvatar(@Payload AvatarRequest avatarRequest) {
        String avatarUrl = chatAvatar.getAvatar(avatarRequest.getUsername());
        avatarRequest.setAvatarUrl(avatarUrl);
        return avatarRequest;
    }
}
