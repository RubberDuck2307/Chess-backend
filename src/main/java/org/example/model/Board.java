package org.example.model;

import org.example.exception.InvalidMoveException;
import org.example.logic.piece.MoveValidator;

import java.util.ArrayList;

import static org.example.logic.piece.MoveValidator.*;

public class Board {

    private String state = "";
    private boolean whiteToMove = true;

    public Board() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            builder.append("-".repeat(8));
            builder.append(";");
        }
        state = builder.toString();
    }

    public void reset() {
        state = "SHBQCBHS;PPPPPPPP;--------;--------;--------;--------;pppppppp;shbqcbhs";
        whiteToMove = true;
    }

    public boolean isPlayersTurn(PlayerColor playerColor) {
        return isWhiteToMove() && playerColor == PlayerColor.WHITE || !isWhiteToMove() && playerColor == PlayerColor.BLACK;
    }

    public boolean isValidMove(int from, int to) {
        return MoveValidator.isValidMove(from, to, state, whiteToMove);
    }

    public void makeMove(int from, int to) {
        if (!isValidMove(from, to)) {
            throw new InvalidMoveException();
        }
        updateCurrentState(from, to);
        whiteToMove = !whiteToMove;
        if (!canCurrentPlayerMove()) {
            if (isCurrentPlayerInCheck()) {
                System.out.println(whiteToMove ? "Black wins" : "White wins");
            } else {
                System.out.println("Stalemate");
            }
        }
        printOutBoard();
    }

    public ArrayList<Integer> getPossibleMoves(int from) {
        return MoveValidator.getPossibleMoves(from, state, whiteToMove);
    }

    private void updateCurrentState(int from, int to) {
        state = updateState(from, to, state);
    }

    private boolean isCurrentPlayerInCheck() {
        return isPlayerInCheck(state, whiteToMove);
    }

    private boolean canCurrentPlayerMove() {
        return canPlayerMove(state, whiteToMove);
    }

    public void printOutBoard() {
        String[] lines = state.split(";");
        for (int i = lines.length - 1; i >= 0; i--) {
            System.out.println(lines[i]);
        }
        System.out.println(whiteToMove ? "White to move" : "Black to move");
    }

    public String getState() {
        return state;
    }

    public boolean isWhiteToMove() {
        return whiteToMove;
    }
}



