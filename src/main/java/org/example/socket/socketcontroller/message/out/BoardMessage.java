package org.example.socket.socketcontroller.message.out;

import lombok.*;
import org.example.model.PlayerColor;
import org.example.socket.socketcontroller.message.SocketMessage;

@Setter
@Getter
@NoArgsConstructor
public class BoardMessage extends SocketMessage {
    private String board;
    private long gameId;
    private PlayerColor currentPlayerColor;

    public BoardMessage( String board, long gameId, PlayerColor currentPlayerColor) {
        super(MessageType.BOARD);
        this.board = board;
        this.gameId = gameId;
        this.currentPlayerColor = currentPlayerColor;
    }
}
