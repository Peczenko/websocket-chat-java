package com.rseny.websocket.config;

import com.rseny.websocket.chat.AvatarLoad;
import com.rseny.websocket.chat.ChatMessage;
import com.rseny.websocket.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            try {
                if (ChatMessage.getAvatarUrlMap(username).contains("http://localhost:8080/uploads/") && ChatMessage.getAvatarUrlMap(username) != null) {
                    AvatarLoad.deleteAvatar(username);
                }
            } catch (Exception e) {
                log.info("Avatar not found");
            }
            ChatMessage.deleteUsers(username);
            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
