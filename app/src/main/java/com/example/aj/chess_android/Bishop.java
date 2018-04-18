package com.example.aj.chess_android;

import java.io.Serializable;

public class Bishop extends Piece implements Serializable {
    public Bishop(String color){
        super(color,"Bishop");
        //set the symbol for use in the draw board method
        symbol = (color.equals("Black")) ? "bB":"wB";
    }

    /**
     * The Bishop's implementation of isValidMove that checks whether the desired move from its current location is valid.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return boolean value that will tell whether the move is valid or not.
     */
    public boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile, Board board) {
        //same position
        if(sourceRank==destRank && sourceFile == destFile){
            return false;
        }
        //checks if the move is diagonal
        if(Math.abs(destRank-sourceRank)!=Math.abs(destFile-sourceFile)){
            return false;
        }
        //check if there is anything in the path of the movement
        //4 cases
        //up and to the right
        if(destRank< sourceRank && destFile > sourceFile){
            int file = sourceFile+1;
            for(int rank = sourceRank-1;rank>destRank; --rank){
                if(board.getBoard()[rank][file].getPiece()!=null){
                    return false;
                }
                file+=1;
            }
        }
        //up and to the left
        if(destRank<sourceRank && destFile < sourceFile){
            int file = sourceFile-1;
            for(int rank = sourceRank-1;rank>destRank;--rank){
                if(board.getBoard()[rank][file].getPiece()!=null){
                    return false;
                }
                file-=1;
            }
        }
        //down and to the right
        if(destRank > sourceRank && destFile > sourceFile){
            int file = sourceFile+1;
            for(int rank = sourceRank+1;rank<destRank;++rank){
                if(board.getBoard()[rank][file].getPiece()!=null){
                    return false;
                }
                file+=1;
            }
        }
        //down and to the left
        if(destRank > sourceRank && destFile < sourceFile){
            int file = sourceFile-1;
            for(int rank = sourceRank+1;rank<destRank;++rank){
                if(board.getBoard()[rank][file].getPiece()!=null){
                    return false;
                }
                file-=1;
            }
        }
        if(board.getBoard()[destRank][destFile].getPiece()!=null && board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
            return false;
        }
        return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
//        if(board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
//            return false;
//        }
//        return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);

    }
}
