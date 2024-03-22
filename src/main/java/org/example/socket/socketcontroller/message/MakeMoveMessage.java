package org.example.socket.socketcontroller.message;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeMoveMessage {

    private Long gameId;
    private int from;
    private int to;

    public MakeMoveMessage() {
    }

}
