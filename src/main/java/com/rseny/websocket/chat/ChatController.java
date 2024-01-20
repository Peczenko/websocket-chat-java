package com.rseny.websocket.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        if (chatMessage.getAvatarUrl() != null && chatMessage.getAvatarUrl().contains("http://localhost:8080/uploads/")){
            ChatMessage.setAvatarMap(chatMessage.getSender(), chatMessage.getAvatarUrl());
        }
        System.out.println("Map" + ChatMessage.getAvatarMap());
        System.out.println("Users" + ChatMessage.getUsers());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        ChatMessage.setUsers(chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @PostMapping("/usernameCheck")
    @ResponseBody
    public boolean checkUsername(@RequestBody String username) {
        return ChatMessage.checkUsername(username);
    }
}
