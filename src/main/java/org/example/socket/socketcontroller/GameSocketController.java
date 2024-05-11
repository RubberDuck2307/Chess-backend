package org.example.socket.socketcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.model.Game;
import org.example.model.PlayerColor;
import org.example.service.GameKeeper;
import org.example.service.model.PlayerSession;
import org.example.socket.socketcontroller.message.handler.GameMessageHandler;
import org.example.socket.socketcontroller.message.SocketMessage;
import org.example.socket.socketcontroller.message.out.BoardMessage;
import org.example.socket.socketcontroller.message.out.JsonMessageFactory;
import org.example.utils.UriUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.logging.Logger;


@RequiredArgsConstructor
@Controller
public class GameSocketController extends TextWebSocketHandler {

    private final GameKeeper gameKeeper;
    private final ObjectMapper objectMapper;
    private final JsonMessageFactory jsonMessageFactory;
    private final GameMessageHandler gameMessageHandler;
    private final Logger logger = Logger.getLogger(GameSocketController.class.getName());
    public static final String GAME_ID = "gameId";
    public static final String COLOR = "color";
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        Object payload = message.getPayload();
        logger.info("Received message: " + payload);

        if (payload instanceof String messageString) {
            SocketMessage parsedMessage;
            try {
                parsedMessage = objectMapper.readValue(messageString, SocketMessage.class);
            } catch (JsonProcessingException e) {
                logger.warning("Received message is not a valid message error:" + e.getMessage());
                return;
            }
            try {
                switch (parsedMessage.getType()) {
                    case GET_POSSIBLE_MOVES:
                        gameMessageHandler.handleGetPossibleMovesMessage(messageString, session);
                        break;
                    case MAKE_MOVE:
                        gameMessageHandler.handleMakeMoveMessage(messageString, session);
                    default:
                        logger.warning("Received message is not a valid message");
                }
            } catch (Exception e) {
                logger.warning("Error occurred while handling a message" + e);
            }
        } else {
            logger.warning("Received message is not a string");
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //@TODO Authentication

        String gameId = UriUtils.extractPathAttributes(session.getUri(), GAME_ID);
        String color = UriUtils.extractPathAttributes(session.getUri(), COLOR);
        PlayerColor playerColor = PlayerColor.valueOf(color.toUpperCase());
        if (gameId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        Game game = gameKeeper.connectToGame(Long.parseLong(gameId), new PlayerSession(session.getId(), session, playerColor));
        BoardMessage boardMessage = new BoardMessage(game.getBoardState(), game.getId(), game.getCurrentPlayerColor());
        session.sendMessage(jsonMessageFactory.createMessage(boardMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    }




}
