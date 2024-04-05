package org.example.socket.socketcontroller.message.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.exception.InvalidMoveException;
import org.example.model.PlayerColor;
import org.example.service.GameKeeper;
import org.example.socket.socketcontroller.message.in.GetPossibleMovesRequestMessage;
import org.example.socket.socketcontroller.message.in.MakeMoveMessage;
import org.example.socket.socketcontroller.message.out.BoardMessage;
import org.example.socket.socketcontroller.message.out.GetPossibleMovesResponseMessage;
import org.example.socket.socketcontroller.message.out.JsonMessageFactory;
import org.example.utils.UriUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class GameMessageHandler {

    private final GameKeeper gameKeeper;
    private final JsonMessageFactory jsonMessageFactory;
    private final ObjectMapper objectMapper;
    private final Logger logger = Logger.getLogger(GameMessageHandler.class.getName());

    public void handleGetPossibleMovesMessage(String payload, WebSocketSession session) throws IOException {
        GetPossibleMovesRequestMessage message;
        try {
            message = objectMapper.readValue(payload, GetPossibleMovesRequestMessage.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid message type");
        }

        long gameId = Long.parseLong(UriUtils.extractPathAttributes(Objects.requireNonNull(session.getUri()), "gameId"));
        var possibleMoves = gameKeeper.getPossibleMoves(gameId, message.getFrom());
        GetPossibleMovesResponseMessage responseMessage = new GetPossibleMovesResponseMessage(possibleMoves, gameId);
        session.sendMessage(jsonMessageFactory.createMessage(responseMessage));
    }

    public void handleMakeMoveMessage(String payload, WebSocketSession session) throws IOException {
        MakeMoveMessage message;
        try {
            message = objectMapper.readValue(payload, MakeMoveMessage.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid message type");
        }
        String gameId = UriUtils.extractPathAttributes(session.getUri(), "gameId");
        PlayerColor color = PlayerColor.getEnum(UriUtils.extractPathAttributes(session.getUri(), "color"));
        try {
            gameKeeper.makeMove(Long.parseLong(gameId), message.getFrom(), message.getTo(), color);
        } catch (InvalidMoveException e) {
            logger.warning("Move is invalid");
        }
        var gameSession = gameKeeper.getGameSession(Long.parseLong(gameId));
        gameSession.getPlayers().forEach(
                playerSession -> {
                    BoardMessage newBoard = new BoardMessage(gameSession.getGame().getBoard().getState(), gameSession.getGame().getId(), gameSession.getGame().getCurrentPlayerColor());
                    try {
                        playerSession.getSession().sendMessage(jsonMessageFactory.createMessage(newBoard));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
