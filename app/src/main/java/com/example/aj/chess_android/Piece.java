package com.example.aj.chess_android;

import java.io.Serializable;

/**
 * Abstract class that is the parent class of each of the different pieces of the chess board. Eases implementation of each piece to avoid redundant code.
 */
public abstract class Piece implements Serializable {
    /**
     * The color of the piece
     */
    private String color;
    /**
     * String that stores the type of the piece
     */
    private String type = null;
    /**
     * boolean that tells whether the piece has moved (set to false at the beginning)
     */
    private boolean hasMoved = false;
    /**
     * String that stores the symbol that the user will see at standard output
     */
    String symbol;

    /**
     * boolean that tells whether the piece is eligible for Enpessant (set to false at the beginning)
     */
    private boolean isEnpessantEligible = false;

    /**
     * Getter that returns a boolean varible to tell whether the piece is Enpessant Eligible
     * @return boolean variable that tells whether the piece is eligible
     */
    public boolean isEnpessantEligible() {
        return isEnpessantEligible;
    }

    /**
     * Setter that sets the value of the variable enpessantEligible to the desired value passed in
     * @param enpessantEligible boolean value that enpessantEligible will be set to
     */
    public void setEnpessantEligible(boolean enpessantEligible) {
        isEnpessantEligible = enpessantEligible;
    }

    /**
     * Constructor that initiates the Piece object's fields
     * @param color color of the piece
     * @param type type of the piece
     */
    public Piece(String color, String type){
        this.color = color;
        this.type = type;
    }

    /**
     * Getter that returns the color of the piece
     * @return String that tells what the color of the piece is
     */

    public String getColor() {
        return color;
    }

    /**
     * Setter that sets the String color to the desired color passed in
     * @param color desired color that will be set to the String variable color
     */

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Getter that returns the type of the piece
     * @return String that tells what the type of the piece is
     */
    public String getType() {
        return type;
    }

    /**
     * Setter that sets the type of the piece
     * @param type desired type that the String type will be set to
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter that returns the symbol of the Piece
     * @return String that is the symbol that the user will see in standard output on the board that represents the piece
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter that returns the boolean value that tells whether a piece has moved
     * @return boolean value that tells whether the piece has moved.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Setter that sets the boolean variable hasMoved to the desired value passed in
     * @param hasMoved boolean value that hasMoved will be set to
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Abstract method that each subclass piece will implement since each piece will have a different move, their isValidMove methods will be different and will return a boolean that will let you know whether the move the user wants to make is valid or not.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return a boolean value that tells whether the move is valid or not
     */
    public abstract boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile, Board board);

    /**
     * This method moves the piece from a source location to a desired destination on the board.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     */
    public void move(int sourceRank, int sourceFile, int destRank, int destFile, Board board){
        this.setHasMoved(true);
        board.getBoard()[destRank][destFile].setPiece(null);
        board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
        board.getBoard()[sourceRank][sourceFile].setPiece(null);
        board.setLastPieceMoved(this);
        if(this.getColor().equals("White")) {
            board.setWhiteInCheck(false);
        }else {
            board.setBlackInCheck(false);
        }

        //after the piece has moved check if the opposite king is in check
    }

    /**
     * This method checks to see whether the king is still in check after making a move. This is a helper for checkmate.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return a boolean value that tells you whether the king is still in check after making a move.
     */
    public boolean kingStillInCheckAfterMove(int sourceRank,int sourceFile, int destRank, int destFile, Board board){
        //spot is empty
        if(board.getBoard()[destRank][destFile].getPiece()==null){
            //check if our king is in check
            if(this.getColor().equals("White")){
                //the king is currently in check
                //move this piece and check if it is still in check
                board.getBoard()[destRank][destFile].setPiece(this);
                board.getBoard()[sourceRank][sourceFile].setPiece(null);
                if(board.check("Black",board.getWhiteKingRank(),board.getWhiteKingFile(),board.getBlackKingRank(),board.getBlackKingFile())){
                    //move the piece back
                    board.getBoard()[sourceRank][sourceFile].setPiece(this);
                    board.getBoard()[destRank][destFile].setPiece(null);
                    return false;
                }
                //move the piece back
                board.getBoard()[sourceRank][sourceFile].setPiece(this);
                board.getBoard()[destRank][destFile].setPiece(null);
                return true;
            }else if(this.getColor().equals("Black")){
                //the king is currently in check
                //move this piece and check if it is still in check
                board.getBoard()[destRank][destFile].setPiece(this);
                board.getBoard()[sourceRank][sourceFile].setPiece(null);
                if(board.check("White",board.getWhiteKingRank(),board.getWhiteKingFile(),board.getBlackKingRank(),board.getBlackKingFile())){
                    //move the piece back
                    board.getBoard()[sourceRank][sourceFile].setPiece(this);
                    board.getBoard()[destRank][destFile].setPiece(null);
                    return false;
                }
                //move the piece back
                board.getBoard()[sourceRank][sourceFile].setPiece(this);
                board.getBoard()[destRank][destFile].setPiece(null);
                return true;
            }
        }else{
            //the space is not empty
            Piece temp = board.getBoard()[destRank][destFile].getPiece();
            board.getBoard()[destRank][destFile].setPiece(this);
            board.getBoard()[sourceRank][sourceFile].setPiece(null);
            //check if the king is still in check
            if(this.getColor().equals("White")){
                if(board.check("Black",board.getWhiteKingRank(),board.getWhiteKingFile(),board.getBlackKingRank(),board.getBlackKingFile())){
                    board.getBoard()[sourceRank][sourceFile].setPiece(this);
                    board.getBoard()[destRank][destFile].setPiece(temp);
                    return false;
                }
                board.getBoard()[sourceRank][sourceFile].setPiece(this);
                board.getBoard()[destRank][destFile].setPiece(temp);
                return true;
            }else{
                if(board.check("White",board.getWhiteKingRank(),board.getWhiteKingFile(),board.getBlackKingRank(),board.getBlackKingFile())){
                    board.getBoard()[sourceRank][sourceFile].setPiece(this);
                    board.getBoard()[destRank][destFile].setPiece(temp);
                    return false;
                }
                board.getBoard()[sourceRank][sourceFile].setPiece(this);
                board.getBoard()[destRank][destFile].setPiece(temp);
                return true;
            }

        }
        return true;
    }
}
