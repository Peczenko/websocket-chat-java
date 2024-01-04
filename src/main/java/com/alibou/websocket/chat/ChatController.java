package com.alibou.websocket.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;



@Controller
public class ChatController {
private final ChatAvatar chatAvatar = new ChatAvatar();
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
//    @MessageMapping("/chat.setAvatar")
//    public void setAvatar(@Payload ChatMessage chatMessage){
//        chatAvatar.setAvatar(chatMessage.getSender(),chatMessage.getContent());
//    }
//    @MessageMapping("/chat.getAvatar")
//    @SendToUser
//    public ChatMessage getAvatar(@Payload ChatMessage chatMessage) {
//        String avatarUrl = chatAvatar.getAvatar(chatMessage.getSender());
//        return new ChatMessage(MessageType.AVATAR, avatarUrl, chatMessage.getSender());
//    }
}
