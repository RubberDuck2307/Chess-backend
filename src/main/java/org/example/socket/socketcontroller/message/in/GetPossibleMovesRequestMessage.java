package org.example.socket.socketcontroller.message.in;

import lombok.Getter;
import lombok.Setter;
import org.example.socket.socketcontroller.message.SocketMessage;

@Setter
@Getter
public class GetPossibleMovesRequestMessage extends SocketMessage {

    private int from;

    public GetPossibleMovesRequestMessage() {
        super(MessageType.GET_POSSIBLE_MOVES);
    }

}
