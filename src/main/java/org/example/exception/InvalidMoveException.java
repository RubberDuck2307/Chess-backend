package org.example.exception;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(int gameId, int from, int to, String state, boolean whiteToMove) {
        super("Invalid move from " + from + " to " + to + " in game " + gameId + " with state " + state + " and white to move " + whiteToMove);
    }

    public InvalidMoveException() {
    }
}
