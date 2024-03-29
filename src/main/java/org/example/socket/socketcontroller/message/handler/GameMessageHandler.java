package org.example.socket.socketcontroller.message.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.service.GameKeeper;
import org.example.socket.socketcontroller.message.in.GetPossibleMovesRequestMessage;
import org.example.socket.socketcontroller.message.out.GetPossibleMovesResponseMessage;
import org.example.socket.socketcontroller.message.out.JsonMessageFactory;
import org.example.utils.UriUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class GameMessageHandler {

    private final GameKeeper gameKeeper;
    private final JsonMessageFactory jsonMessageFactory;
    private final ObjectMapper objectMapper;

    public void handleGetPossibleMovesMessage(String payload, WebSocketSession session) throws IOException {
        GetPossibleMovesRequestMessage message;
        try {
            message = objectMapper.readValue(payload, GetPossibleMovesRequestMessage.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid message type");
        }

        Long gameId = Long.parseLong(UriUtils.extractPathAttributes(Objects.requireNonNull(session.getUri()), "gameId"));
        var possibleMoves = gameKeeper.getPossibleMoves(gameId, message.getFrom());
        GetPossibleMovesResponseMessage responseMessage = new GetPossibleMovesResponseMessage(possibleMoves, gameId);
        session.sendMessage(jsonMessageFactory.createMessage(responseMessage));
    }

}
