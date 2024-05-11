package org.example.socket.socketcontroller.message.out;

import lombok.*;
import org.example.model.PlayerColor;
import org.example.socket.socketcontroller.message.SocketMessage;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardMessage that = (BoardMessage) o;

        if (gameId != that.gameId) return false;
        if (!Objects.equals(board, that.board)) return false;
        return currentPlayerColor == that.currentPlayerColor;
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = 31 * result + (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (currentPlayerColor != null ? currentPlayerColor.hashCode() : 0);
        return result;
    }
}
