package com.example.aj.chess_android;

public class King extends Piece{

    /**
     * Field that keeps track of whether the King has performed the castle move or not. (Initialized to false at the beginning)
     */
    private boolean hasCastled = false;

    /**
     * Constructor that initializes the color of the King and sets the type to King and the symbol depending on the input color
     * @param color desired color of the King piece
     */
    public King(String color){
        super(color, "King");
        //set the symbol for use in the draw board method
        symbol = (color.equals("Black")) ? "bK":"wK";
    }

    /**
     * The King's implementation of isValidMove that checks whether the desired move from its current location is valid.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return boolean value that will tell whether the move is valid or not.
     */

    public boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile,Board board){

        //white right side castling validity check
        if(sourceRank==7&& destRank==7 && this.getColor().equals("White") && sourceFile==4 && destFile==6) {
            // the white king is attempting castling on the right side
            //king cannot perform castling if it is currently in check
            if(board.isWhiteInCheck()){
                return false;
            }
            //get the piece that is supposed to be a rook if we are attempting castling
            Piece piece = board.getBoard()[7][7].getPiece();
            //return false if the other piece isnt a rook
            if(!(piece instanceof Rook)){
                return false;
            }
            //check if the pieces have moved before or if they are not of the same color
            if(this.hasMoved() || !piece.getColor().equals(this.getColor()) || piece.hasMoved()){
                return false;
            }
            //loop through all the spaces to see if all the squares are empty
            for(int file = sourceFile+1;file<=destFile;++file){
                if(board.getBoard()[sourceRank][file].getPiece()!=null){
                    return false;
                }
                if(board.check("Black",board.getWhiteKingRank(),board.getWhiteKingFile(),sourceRank,file)){
                    //move the king back since we know we cant perform castling
                    return false;
                }
            }
            return true;
        }
        //white king queen side castling validity check
        if(sourceRank==7 && destRank==7 && sourceFile==4 && destFile==2 && this.getColor().equals("White")){
            //king cannot perform castling if it is currently in check
            if((board.isWhiteInCheck())){
                return false;
            }
            //get the piece that is supposed to be a rook if we are attempting castling
            Piece piece = board.getBoard()[7][0].getPiece();
            //return false if the other piece isnt a rook
            if(!(piece instanceof Rook)){
                return false;
            }
            //check if the pieces have moved before or if they are not of the same color
            if(this.hasMoved() || !piece.getColor().equals(this.getColor()) || piece.hasMoved()){
                return false;
            }
            //loop through all the spaces to see if all the squares are empty
            for(int file = sourceFile-1;file>=destFile;--file){
                if(board.getBoard()[sourceRank][file].getPiece()!=null){
                    return false;
                }
                if(board.check("Black",board.getWhiteKingRank(),board.getWhiteKingFile(),sourceRank,file)){
                    return false;
                }
            }
            return true;
        }
        //black king right side castling validity check
        if(sourceRank==0 && destRank==0 && sourceFile==4 && destFile==6 && this.getColor().equals("Black")){
            //king cannot perform castling if it is currently in check
            if((board.isBlackInCheck())){
                return false;
            }
            //get the piece that is supposed to be a rook if we are attempting castling
            Piece piece = board.getBoard()[0][7].getPiece();
            //return false if the other piece isnt a rook
            if(!(piece instanceof Rook)){
                return false;
            }
            //check if the pieces have moved before or if they are not of the same color
            if(this.hasMoved() || !piece.getColor().equals(this.getColor()) || piece.hasMoved()){
                return false;
            }
            //loop through all the spaces to see if all the squares are empty
            for(int file = sourceFile+1;file<=destFile;++file){
                if(board.getBoard()[sourceRank][file].getPiece()!=null){
                    return false;
                }
                if(board.check("White",sourceRank,file,board.getBlackKingRank(),board.getBlackKingFile())){
                    return false;
                }
            }
            return true;
        }
        if(sourceRank==0 && destRank==0 && sourceFile==4 && destFile==2 && this.getColor().equals("Black")){
            //king cannot perform castling if it is currently in check
            if((board.isBlackInCheck())){
                return false;
            }
            //get the piece that is supposed to be a rook if we are attempting castling
            Piece piece = board.getBoard()[0][0].getPiece();
            //return false if the other piece isnt a rook
            if(!(piece instanceof Rook)){
                return false;
            }
            //check if the pieces have moved before or if they are not of the same color
            if(this.hasMoved() || !piece.getColor().equals(this.getColor()) || piece.hasMoved()){
                return false;
            }
            //loop through all the spaces to see if all the squares are empty
            for(int file = sourceFile-1;file>=destFile;--file){
                if(board.getBoard()[sourceRank][file].getPiece()!=null){
                    return false;
                }
                if(board.check("White",sourceRank,file,board.getBlackKingRank(),board.getBlackKingFile())){
                    return false;
                }
            }
            return true;
        }

        //check for normal king movement of 1 space
        //king is moving down
        if((destRank == sourceRank+1 && destFile == sourceFile)
                || (destRank == sourceRank-1 && destFile == sourceFile)
                || (destRank == sourceRank && destFile == sourceFile-1)
                || (destRank == sourceRank && destFile == sourceFile+1)
                || ((Math.abs(destRank-sourceRank)==Math.abs(destFile-sourceFile)) && Math.abs(destRank-sourceRank)==1)){
            //the space is empty
            if(board.getBoard()[destRank][destFile].getPiece()==null){
//                check if the movement into the space will result in a check
//                if(this.getColor().equals("White")){
//                    if(board.check("Black",destRank,destFile,board.getBlackKingRank(),board.getBlackKingFile())){
//                        return false;
//                    }
//                    return true;
//                }else{
//                    if(board.check("White",board.getWhiteKingRank(),board.getWhiteKingFile(),destRank,destFile)){
//                        return false;
//                    }
//                    return true;
//                }
                if(this.getColor().equals("White")){
                    //the king is currently in check
                    //move this piece and check if it is still in check
                    board.getBoard()[destRank][destFile].setPiece(this);
                    board.getBoard()[sourceRank][sourceFile].setPiece(null);
                    if(board.check("Black",destRank,destFile,destRank,destFile)){
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
                    if(board.check("White",destRank,destFile,destRank,destFile)){
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
                if(board.getBoard()[destRank][destFile].getPiece().getColor().equals(this.getColor())) return false;
                //the square isnt empty so we have to check if the capturing of the piece results in the king still being in check
                //hold onto the piece we are temporarily capturing
                Piece temp = board.getBoard()[destRank][destFile].getPiece();
                board.getBoard()[destRank][destFile].setPiece(this);
                board.getBoard()[sourceRank][sourceFile].setPiece(null);
                //check if the king is still in check
                if(this.getColor().equals("White")){
                    if(board.check("Black",destRank,destFile,board.getBlackKingRank(),board.getBlackKingFile())){
                        board.getBoard()[sourceRank][sourceFile].setPiece(this);
                        board.getBoard()[destRank][destFile].setPiece(temp);
                        return false;
                    }
                    board.getBoard()[sourceRank][sourceFile].setPiece(this);
                    board.getBoard()[destRank][destFile].setPiece(temp);
                    return true;
                }else{
                    if(board.check("White",board.getWhiteKingRank(),board.getWhiteKingFile(),destRank,destFile)){
                        board.getBoard()[sourceRank][sourceFile].setPiece(this);
                        board.getBoard()[destRank][destFile].setPiece(temp);
                        return false;
                    }
                    board.getBoard()[sourceRank][sourceFile].setPiece(this);
                    board.getBoard()[destRank][destFile].setPiece(temp);
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * This method is overridden from the Piece class since the King has the capability of doing different moves. (Specifically the Castle)
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     */
//    @Override
    public void move(int sourceRank, int sourceFile, int destRank, int destFile, Board board){
        //TODO: implement piece movements for castling if we are performing a castling move
        //white right side castling movement
        if(sourceRank==7 && destRank ==7 && sourceFile==4 && destFile==6){
            //move the king and rook into their new positions
            //move king
            board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
            board.getBoard()[sourceRank][sourceFile].setPiece(null);
            //set the king has moved boolean
            this.setHasMoved(true);
            //set new king position
            board.setWhiteKingRank(destRank);
            board.setWhiteKingFile(destFile);

            //move rook
            board.getBoard()[destRank][5].setPiece(board.getBoard()[destRank][7].getPiece());
            board.getBoard()[destRank][7].setPiece(null);

            //set the rook has moved boolean
            board.getBoard()[destRank][5].getPiece().setHasMoved(true);
            board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());


        }else if(sourceRank==7 && destRank==7 && sourceFile==4 && destFile==2){ //white queen side castling movement
            //move the king and rook into their new positions
            //move king
            board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
            board.getBoard()[sourceRank][sourceFile].setPiece(null);
            //set the king has moved boolean
            this.setHasMoved(true);
            //set new king position
            board.setWhiteKingRank(sourceRank);
            board.setWhiteKingFile(destFile);

            //move rook
            board.getBoard()[destRank][3].setPiece(board.getBoard()[destRank][0].getPiece());
            board.getBoard()[destRank][0].setPiece(null);

            //set the rook has moved boolean
            board.getBoard()[destRank][3].getPiece().setHasMoved(true);
            board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());

        }
        //black right side castling movement
        else if(sourceRank==0 && destRank==0 && sourceFile==4 && destFile == 6){
            //move the king and rook into their new positions
            //move king
            board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
            board.getBoard()[sourceRank][sourceFile].setPiece(null);
            //set the king has moved boolean
            this.setHasMoved(true);
            //set new king position
            board.setBlackKingRank(sourceRank);
            board.setBlackKingFile(destFile);

            //move rook
            board.getBoard()[destRank][5].setPiece(board.getBoard()[destRank][7].getPiece());
            board.getBoard()[destRank][7].setPiece(null);

            //set the rook has moved boolean
            board.getBoard()[destRank][5].getPiece().setHasMoved(true);
            board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
        }
        //white and black queen side castling movement
        else if(sourceRank==0 && destRank==0 && sourceFile==4 && destFile == 2){
            //move the king and rook into their new positions
            //move king
            board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
            board.getBoard()[sourceRank][sourceFile].setPiece(null);
            //set the king has moved boolean
            this.setHasMoved(true);
            //set new king position
            board.setBlackKingRank(sourceRank);
            board.setBlackKingFile(destFile);

            //move rook
            board.getBoard()[destRank][3].setPiece(board.getBoard()[destRank][0].getPiece());
            board.getBoard()[destRank][0].setPiece(null);

            //set the rook has moved boolean
            board.getBoard()[destRank][3].getPiece().setHasMoved(true);
            board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
        }else{
            //normal movement
            board.getBoard()[destRank][destFile].setPiece(null);
            board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
            board.getBoard()[sourceRank][sourceFile].setPiece(null);
            board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
            //set the kings new position and set in check to false again
            if(this.getColor().equals("White")){
                board.setWhiteKingRank(destRank);
                board.setWhiteKingFile(destFile);
                board.setWhiteInCheck(false);
            }else{
                board.setBlackKingRank(destRank);
                board.setBlackKingFile(destFile);
                board.setBlackInCheck(false);
            }
        }
    }

    /**
     * Getter that returns the boolean value that tells whether the King piece has performed the castle move or not.
     * @return boolean value that tells whether the king has castled
     */
    public boolean hasCastled() {
        return hasCastled;
    }

    /**
     * Setter that sets the boolean variable hasCastled to the desired value passed in
     * @param hasCastled desired boolean value to set hasCastled to
     */
    public void setHasCastled(boolean hasCastled) {
        this.hasCastled = hasCastled;
    }

}
