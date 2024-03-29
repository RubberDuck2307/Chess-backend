package org.example.logic.piece;

import java.util.ArrayList;

import static org.example.logic.piece.Piece.haveDifferentColor;
import static org.example.logic.piece.Piece.isWhite;

public class MoveValidator {

    public static boolean isValidMove(int from, int to, String state, boolean whiteToMove) {

        if (from < 0 || from > 63 + 8 || to < 0 || to > 63 + 8 || from == to) return false;
        char piece = state.charAt(from);
        char target = state.charAt(to);
        //Invalid pieces
        if (target == ';' || piece == ';' || piece == '-') return false;
        //If capturing pieces have different colours
        if (target != '-' && !haveDifferentColor(piece, target)) return false;
        //who is playing check
        if ((whiteToMove && !isWhite(piece)) || (!whiteToMove && isWhite(piece))) return false;
        //piece logic check
        boolean valid = false;
        for (Piece p : Piece.getPieces()) {
            if (p.getSymbol() == Character.toUpperCase(piece)) {
                valid = p.isValidMove(state, whiteToMove, from, to);
                break;
            }
        }
        if (!valid) {
            return false;
        }

        return !isPlayerInCheck(updateState(from, to, state), whiteToMove);
    }

    public static boolean isPlayerInCheck(String state, boolean whiteToMove) {
        String king = whiteToMove ? "K" : "k";
        int kingIndex = state.indexOf(king);
        if (kingIndex == -1) {
            king = whiteToMove ? "C" : "c";
            kingIndex = state.indexOf(king);
        }
        char kingP = state.charAt(kingIndex);
        for (int i = 0; i < state.length(); i++) {
            char piece = state.charAt(i);
            if (piece != ';' && piece != '-' && haveDifferentColor(kingP, piece)) {
                for (Piece p : Piece.getPieces()) {
                    if (p.getSymbol() == Character.toUpperCase(piece)) {
                        if (p.isValidMove(state, whiteToMove, i, kingIndex)) {
                            System.out.println(p.getSymbol() + " is attacking the king at " + kingIndex + " from " + i);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String updateState(int from, int to, String state) {
        StringBuilder newState = new StringBuilder(state);
        newState.replace(from, from + 1, "-");
        newState.replace(to, to + 1, String.valueOf(state.charAt(from)));
        char movedPiece = newState.charAt(to);
        if (Character.toUpperCase(movedPiece) == 'S') {
            newState.replace(to, to + 1, Character.isUpperCase(movedPiece) ? "R" : "r");
        }
        if (Character.toUpperCase(movedPiece) == 'C') {
            newState.replace(to, to + 1, Character.isUpperCase(movedPiece) ? "K" : "k");
        }
        if (Character.toUpperCase(movedPiece) == 'P' && Math.abs(from - to) == 19) {
            newState.replace(to, to + 1, Character.isUpperCase(movedPiece) ? "A" : "a");
        }
        String newStateString = newState.toString();

        newStateString = newStateString.replace('A', 'P');
        newStateString = newStateString.replace("a", "p");

        return newStateString;
    }

    public static ArrayList<Integer> getPossibleMoves(int from, String state, boolean whiteToMove) {
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for (int i = 0; i < state.length(); i++) {
            if (isValidMove(from, i, state, whiteToMove)) {
                possibleMoves.add(i);
            }
        }
        return possibleMoves;
    }


    public static boolean canPlayerMove(String state, boolean whiteToMove) {
        for (int i = 0; i < state.length(); i++) {
            char piece = state.charAt(i);
            if (piece != ';' && piece != '-' && isWhite(piece) == whiteToMove) {
                for (int j = 0; j < state.length(); j++) {
                    if (isValidMove(i, j, state, whiteToMove)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
