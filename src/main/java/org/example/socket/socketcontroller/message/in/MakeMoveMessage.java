package org.example.socket.socketcontroller.message.in;


import lombok.Getter;
import lombok.Setter;
import org.example.socket.socketcontroller.message.SocketMessage;

@Setter
@Getter
public class MakeMoveMessage extends SocketMessage {

    private int from;
    private int to;
    public MakeMoveMessage(int from, int to){
        super(MessageType.MAKE_MOVE);
        this.from = from;
        this.to = to;
    }

    public MakeMoveMessage() {
    }

}
