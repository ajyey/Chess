package com.example.aj.chess_android;

import java.io.Serializable;

/**
 * Subclass of Piece that represents the Queen piece.
 */
public class Queen extends Piece implements Serializable{

    /**
     * Constructor that initializes the color of the Queen and sets the type to Queen and the symbol depending on the input color
     * @param color desired color of the Queen piece
     */
    public Queen(String color){
        super(color, "Queen");
        //set the symbol for use in the draw board method
        symbol = (color.equals("Black")) ? "bQ":"wQ";
    }

    /**
     * The Queens's implementation of isValidMove that checks whether the desired move from its current location is valid.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return boolean value that will tell whether the move is valid or not.
     */
    public boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile,Board board){
        //same position
        if(sourceRank==destRank && sourceFile == destFile){
            return false;
        }
        if(sourceRank!=destRank && sourceFile!=destFile && Math.abs(sourceRank-destRank)!=Math.abs(sourceFile-destFile)){
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
            }
            //moving right
            if(sourceFile<destFile){
                for(int file = sourceFile+1;file<destFile;++file){
                    if(board.getBoard()[sourceRank][file].getPiece()!=null){
                        return false;
                    }
                }
            }
        }

        //vertical movement
        if(sourceFile==destFile){
            //moving upward
            if(sourceRank>destRank){
                for(int rank = sourceRank-1;rank>destRank;--rank){
                    if(board.getBoard()[rank][sourceFile].getPiece()!=null){
                        return false;
                    }
                }
            }
            //moving downward
            if(sourceRank<destRank){
                for(int rank = sourceRank+1;rank<destRank;++rank){
                    if(board.getBoard()[rank][sourceFile].getPiece()!=null){
                        return false;
                    }
                }
            }
        }
        //diagonal movement
        if(Math.abs(destRank-sourceRank)==Math.abs(destFile-sourceFile)){
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
        }
        //if the spot is empty return true
        if(board.getBoard()[destRank][destFile].getPiece()!=null && board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())){
            return false;
        }
        return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
    }

}
