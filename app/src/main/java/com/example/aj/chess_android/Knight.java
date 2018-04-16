package com.example.aj.chess_android;

/**
 * Subclass of Piece that represents the Knight piece.
 */
public class Knight extends Piece{
    /**
     * Constructor that initializes the color of the Knight and sets the type to Knight and the symbol depending on the input color
     * @param color desired color of the Knight piece
     */
    public Knight(String color){
        super(color,"Knight");
        //set the symbol for use in the draw board method
        symbol = (color.equals("Black")) ? "bN":"wN";
    }

    /**
     * The Knight's implementation of isValidMove that checks whether the desired move from its current location is valid.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return boolean value that will tell whether the move is valid or not.
     */
    public boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile, Board board){

        //check for same position
        if(sourceRank==destRank && sourceFile==destFile){
            return false;
        }
        //horizontal movement
        if(destFile==sourceFile+2||destFile==sourceFile-2){
            if(destRank==sourceRank+1||destRank==sourceRank-1) {
                //check if the piece at the destination square is empty
                if(board.getBoard()[destRank][destFile].getPiece()!=null && board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
                    return false;
                }
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);

            }
        }
        //vertical movement
        if(destRank == sourceRank+2 || destRank == sourceRank-2){
            if(destFile == sourceFile+1 || destFile == sourceFile-1){
                //check if the piece at the destination square is empty
                if(board.getBoard()[destRank][destFile].getPiece()!=null && board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
                    return false;
                }
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
            }
        }
        return false;
    }

}

