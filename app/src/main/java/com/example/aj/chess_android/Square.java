package com.example.aj.chess_android;


import java.io.Serializable;

public class Square implements Serializable {
    /**
     * Piece that is on the current square
     */
    private Piece piece;
    /**
     * Color of the square on the board
     */
    private String color;

    /**
     * Constructor that initiates a square objects color, the piece will be set later on.
     * @param color desired color of the square
     */
    public Square(String color){
        this.color = color;
    }

    /**
     * Getter that will return the piece if there is one currently on the square
     * @return piece that is on the square
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Setter that sets the piece on the square
     * @param piece desired piece to be stored in the piece variable
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Getter that returns the color of the square
     * @return the color of the square
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter that sets the color of the square
     * @param color desired color that color variable will be set to
     */
    public void setColor(String color) {
        this.color = color;
    }
}

