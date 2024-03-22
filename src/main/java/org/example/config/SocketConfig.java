package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.socket.socketcontroller.GameSocketController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class SocketConfig implements WebSocketConfigurer {

    private final GameSocketController gameSocketController;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameSocketController, "/websocket")
                .setAllowedOrigins("*");
    }
}