package org.example.socket.socketcontroller.message.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.socket.socketcontroller.message.SocketMessage;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class GetPossibleMovesResponseMessage extends SocketMessage {
    private List<Integer> possibleMovesList;
    private long gameId;

    public GetPossibleMovesResponseMessage(List<Integer> possibleMovesList, long gameId) {
        super(MessageType.GET_POSSIBLE_MOVES);
        this.possibleMovesList = possibleMovesList;
        this.gameId = gameId;
    }
}
