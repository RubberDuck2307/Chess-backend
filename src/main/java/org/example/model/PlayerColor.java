package org.example.model;

public enum PlayerColor {

    BLACK("black"),
    WHITE("white");

    private String color;

    PlayerColor(String color) {
        this.color = color;
    }

    public static PlayerColor getEnum(String color) {
        if (color == null)
            return null;
        if (color.equals("black"))
            return BLACK;
        else if (color.equals("white"))
            return WHITE;
        else
            return null;
    }


}
