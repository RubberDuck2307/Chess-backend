package org.example.logic.piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public enum Piece {

    ROOK('R') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            String move = Piece.extractMove(state, from, to);
            //Horizontal move
            if (move.length() <= 8) {
                if (move.contains(";"))
                    return false;
                return move.chars().filter(ch -> ch == '-').count() == move.length() -1; //No pieces in the way
            }
            //Vertical move
            else {
                if (move.length() % 9 != 1) return false;
                for (int i = 1; i < move.length() / 9; i++) {
                    if (move.charAt(i * 9) != '-') //Piece in the way
                        return false;
                }
                return true;
            }
        }
    },

    BISHOP('B') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            String move = Piece.extractMove(state, from, to);
            if (move.length() < 9) return false;
            if (move.length() % 10 == 1) {
                for (int i = 1; i < move.length() / 10; i++) {
                    if (move.charAt(i * 10) != '-') //Piece in the way
                        return false;
                }
            } else if (move.length() % 8 == 1) {
                for (int i = 1; i < move.length() / 8; i++) {
                    if (move.charAt(i * 8) != '-') //Piece in the way
                        return false;
                }
            } else return false;
            return true;
        }
    },


    QUEEN('Q') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            return BISHOP.isValidMove(state, whiteToMove, from, to) || ROOK.isValidMove(state, whiteToMove, from, to);
        }
    },

    PAWN('P') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            //No backtracking
            if ((whiteToMove && to < from) || (!whiteToMove && from < to)) return false;

            boolean firstPawnMove = (whiteToMove && from < 18) || (!whiteToMove && from > 53);
            String move = Piece.extractMove(state, from, to);

            char end = whiteToMove ? move.charAt(move.length() - 1) : move.charAt(0);

            //move forward and there is a figure
            if ((move.length() == 10 || move.length() == 19) && end != '-') {
                return false;
            }
            //move two squares and the path is occupied
            if (move.length() == 19 && move.charAt(9) != '-') return false;

            Set<Integer> validLengths = getValidLengths(whiteToMove, move, firstPawnMove);
            if (!validLengths.contains(move.length())) {
                return false;
            }

            //En passant
            if (move.length() == 9 && end == '-' && Character.toLowerCase(state.charAt(from - 1)) != 'a') {
                return false;
            }

            if (move.length() == 11 && end == '-' && Character.toLowerCase(state.charAt(from + 1)) != 'a') return false;

            return true;
        }

        private static Set<Integer> getValidLengths(boolean whiteToMove, String move, boolean firstPawnMove) {
//            String part = whiteToMove ? move.substring(0, move.indexOf(";")) : move.substring(move.indexOf(";") + 1);

            Set<Integer> validLengths = Set.of(9, 10, 11);
//            //edgePawns probably already checked by target != ";"
//            if (part.length() == 8)
//                validLengths = Set.of(10, 11);
//            if (part.length() == 1)
//                validLengths = Set.of(9, 10);
            if (firstPawnMove) {
                validLengths = new HashSet<>(validLengths);
                validLengths.add(19);
            }
            return validLengths;
        }
    },

    KING('K') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            String move = extractMove(state, from, to);
            List<Integer> validLengths = List.of(2, 9, 10, 11);
            return validLengths.contains(move.length());
        }
    },

    KNIGHT('H') {
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            String move = extractMove(state, from, to);
            List<Integer> validLengths = List.of(12, 8, 20, 18);
            if (!validLengths.contains(move.length()))
                return false;
            if (move.length() == 8 && !move.contains(";"))
                return false;
            return true;
        }
    },

    //Casting rules
    ROOK_UNMOVED('S') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            return ROOK.isValidMove(state, whiteToMove, from, to);
        }
    },

    KING_UNMOVED('C') {
        @Override
        public boolean isValidMove(String state, boolean whiteToMove, int from, int to) {
            return KING.isValidMove(state, whiteToMove, from, to) || isValidCastle(state, whiteToMove, from, to);
        }

        public static boolean isValidCastle(String state, boolean whiteToMove, int from, int to) {
            List<Integer> validLengths = List.of(3, 4);
            String move = Piece.extractMove(state, from, to);

            boolean isLongCastle = move.length() == 4;

            if (!validLengths.contains(move.length())) {
                return false;
            }
            if (isLongCastle && Character.toUpperCase(state.charAt(to) - 1) != 'S')
                return false;

            if (isLongCastle && Character.toUpperCase(state.charAt(to) + 1) != 'S')
                return false;
            for (int i = 0; i < state.length(); i++) {
                char piece = state.charAt(i);
                AtomicBoolean valid = new AtomicBoolean(true);
                //are opponents pieces protecting the path
                if (piece != ';' && piece != '-' && !haveDifferentColor(state.charAt(from), piece)) {
                    getPieces().forEach(p -> {
                        if (p.getSymbol() == piece) {
                            for (int j = 0; j < (isLongCastle ? 4 : 3); j++) {
                                if (p.isValidMove(state, whiteToMove, from, (isLongCastle ? from - j : from + j))) {
                                    valid.set(false);
                                    return;
                                }
                            }
                        }
                    });
                }
                if (!valid.get())
                    return valid.get();
            }
            return true;
        }
    };

    private final char symbol;


    public abstract boolean isValidMove(String state, boolean whiteToMove, int from, int to);


    private static String extractMove(String state, int from, int to) {
        return from < to ? state.substring(from, to + 1) : state.substring(to, from + 1);
    }

    Piece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Set<Piece> getPieces() {
        return Set.of(ROOK, ROOK_UNMOVED, KNIGHT, BISHOP, KING, KING_UNMOVED, QUEEN, PAWN);
    }

    public static boolean isWhite(char piece) {
        return Character.isUpperCase(piece);
    }

    public static boolean haveDifferentColor(char piece, char piece2) {
        return isWhite(piece) != isWhite(piece2);
    }

}
