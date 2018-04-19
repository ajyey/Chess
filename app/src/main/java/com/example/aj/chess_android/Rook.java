package com.example.aj.chess_android;


import java.io.Serializable;

public class Rook extends Piece implements Serializable{
    /**
     * Constructor that initializes the color of the Rook and sets the type to Rook and the symbol depending on the input color
     * @param color desired color of the Rook piece
     */
    public Rook(String color){
        super(color,"Rook");
        //set the symbol for use in the draw board method
        symbol = (color.equals("Black")) ? "bR":"wR";
    }

    /**
     * The Rook's implementation of isValidMove that checks whether the desired move from its current location is valid.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return boolean value that will tell whether the move is valid or not.
     */
    public boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile, Board board){
        //same position
        if(sourceRank==destRank && sourceFile == destFile){
            return false;
        }
        //horizontal movement
        if(sourceRank==destRank){
            //moving left
            if(sourceFile>destFile){
                for(int file = sourceFile-1;file>destFile;--file){
                    if(board.getBoard()[sourceRank][file].getPiece()!=null){
                        return false;
                    }
                }
                //check if the space is empty
                if(board.getBoard()[destRank][destFile].getPiece()==null){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }
                //returns false if the the piece in the destination is the same color
                if(board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
                    return false;
                }
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
            }
            //moving right
            if(sourceFile<destFile){
                for(int file = sourceFile+1;file<destFile;++file){
                    if(board.getBoard()[sourceRank][file].getPiece()!=null){
                        return false;
                    }
                }
                //check if the space is empty
                if(board.getBoard()[destRank][destFile].getPiece()==null){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }
                if(board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
                    return false;
                }
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
            }
        }else if(sourceFile==destFile){
            //moving upward
            if(sourceRank>destRank){
                for(int rank = sourceRank-1;rank>destRank;--rank){
                    if(board.getBoard()[rank][sourceFile].getPiece()!=null){
                        return false;
                    }
                }
                //check if the space is empty
                if(board.getBoard()[destRank][destFile].getPiece()==null){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }
                if(board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
                    return false;
                }
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
            }
            //moving downward
            if(sourceRank<destRank){
                for(int rank = sourceRank+1;rank<destRank;++rank){
                    if(board.getBoard()[rank][sourceFile].getPiece()!=null){
                        return false;
                    }
                }
                //check if the space is empty
                if(board.getBoard()[destRank][destFile].getPiece()==null){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);

                }
                if(board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
                    return false;
                }
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
            }
        }
        return false;
    }
}
