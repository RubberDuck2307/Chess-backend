package org.example.socket.socketcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.model.PlayerColor;
import org.example.service.GameKeeper;
import org.example.service.model.GameSession;
import org.example.service.model.PlayerSession;
import org.example.socket.socketcontroller.message.MakeMoveMessage;
import org.example.utils.UriUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;


@RequiredArgsConstructor
@Controller
public class GameSocketController extends TextWebSocketHandler {

    private final GameKeeper gameKeeper;
    private final ObjectMapper objectMapper;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        Object payload = message.getPayload();
        System.out.println(session.getId() + " sent: " + payload);
        if (payload instanceof String) {
            String gameId = UriUtils.extractPathAttributes(session.getUri(), "gameId");
            MakeMoveMessage makeMoveMessage = objectMapper.readValue((String) payload, MakeMoveMessage.class);
            PlayerColor color = PlayerColor.getEnum(UriUtils.extractPathAttributes(session.getUri(), "color"));
            gameKeeper.makeMove(Long.parseLong(gameId), makeMoveMessage.getFrom(), makeMoveMessage.getTo(), color);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //@TODO Authentication

        String gameId = UriUtils.extractPathAttributes(session.getUri(), "gameId");
        PlayerColor color = PlayerColor.getEnum(UriUtils.extractPathAttributes(session.getUri(), "playerColor"));

        if (gameId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        gameKeeper.connectToGame(Long.parseLong(gameId), new PlayerSession(session.getId()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }


}
