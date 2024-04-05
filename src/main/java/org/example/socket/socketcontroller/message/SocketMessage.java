package org.example.socket.socketcontroller.message;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocketMessage {

    private MessageType type;

    public SocketMessage() {
    }

    public enum MessageType {

        GET_POSSIBLE_MOVES("getPossibleMoves"),
        BOARD("board"),
        MAKE_MOVE("makeMove");

        private final String type;

        MessageType(String type) {
            this.type = type;
        }

        @JsonValue
        public String getType() {
            return type;
        }

    }
}