package org.example.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.exception.InvalidMoveException;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class Game {

    private Set<Long> playersIds = new HashSet<>();
    private Long id;
    private final Board board;

    public Game(Set<Long> playersIds) {
        this();
        this.playersIds = playersIds;
    }

    public Game() {
        this.board = new Board();
        this.board.reset();
    }

    public void makeMove(int from, int to) {
            board.makeMove(from, to);
    }

    public boolean isPlayersTurn(PlayerColor playerColor) {
        return board.isPlayersTurn(playerColor);
    }
}
