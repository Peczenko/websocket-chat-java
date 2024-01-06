package com.alibou.websocket.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AvatarRequest {
    private String username;
    private String avatarUrl;

}
