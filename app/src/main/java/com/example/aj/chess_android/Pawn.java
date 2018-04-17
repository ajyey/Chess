package com.example.aj.chess_android;

/**
 * Subclass of Piece that represents the Pawn piece.
 */
public class Pawn extends Piece{
    /**
     * Constructor that initializes the color of the Pawn and sets the type to Pawn and the symbol depending on the input color
     * @param color desired color of the Pawn piece
     */
    public Pawn(String color){
        super(color,"Pawn");
        //set the symbol for use in the draw board method
        symbol = (color.equals("Black")) ? "bp":"wp";
    }

    /**
     * The Pawn's implementation of isValidMove that checks whether the desired move from its current location is valid.
     * @param sourceRank current row of the piece
     * @param sourceFile current column of the piece
     * @param destRank desired row for the piece
     * @param destFile desired column for the piece
     * @param board the state of the board currently
     * @return boolean value that will tell whether the move is valid or not.
     */
    public boolean isValidMove(int sourceRank, int sourceFile, int destRank, int destFile, Board board){
        boolean valid = false;
        //check if the source position is the same as the dest position
        if(sourceRank==destRank && sourceFile==destFile){
            return false;
        }
        //check if difference in rank is greater than 2
        if(Math.abs(sourceRank-destRank)>2||Math.abs(sourceFile-destFile)>1){
            return false;
        }
        //check for horizontal movement
        if(sourceRank==destRank){
            return false;
        }
        if(this.getColor().equals("White") && destRank>sourceRank) return false;
        if(this.getColor().equals("Black") && destRank<sourceRank) return false;
        if(Math.abs(sourceRank-destRank)==2 && sourceFile!=destFile) return false;
        if(this.getColor().equals("Black") && destRank== sourceRank+2 && destFile==sourceFile){
            if(board.getBoard()[sourceRank+1][destFile].getPiece()!=null){
                return false;
            }
        }
        if(this.getColor().equals("White") && destRank== sourceRank-2 && destFile==sourceFile){
            if(board.getBoard()[sourceRank-1][destFile].getPiece()!=null){
                return false;
            }
        }

        //check for normal move for black
        if(this.getColor().equals("Black") && destRank == sourceRank+1 && destFile == sourceFile){
            if(board.getBoard()[destRank][destFile].getPiece()!=null){
                return false;
            }
            return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
        }
        //check for normal move for white
        if(this.getColor().equals("White") && destRank == sourceRank-1 && destFile == sourceFile){
            if(board.getBoard()[destRank][destFile].getPiece()!=null){
                return false;
            }
            return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
        }
        //check for double move for black
        if(this.getColor().equals("Black") && destRank == sourceRank+2 && destFile == sourceFile){
            if(sourceRank!=1) return false;
            if(board.getBoard()[destRank][destFile].getPiece()!=null){
                return false;
            }
            return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
        }
        //check for double move for white
        if(this.getColor().equals("White") && destRank == sourceRank-2 && destFile == sourceFile){
            if(board.getBoard()[destRank][destFile].getPiece()!=null){
                return false;
            }
            if(sourceRank!=6) return false;
            return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
        }
        //check for en pessant
        if(board.getBoard()[destRank][destFile].getPiece()==null){
            //black is moving down and to the left
            if(destRank==(sourceRank+1) && destFile==(sourceFile-1)){
                Piece temp = board.getBoard()[sourceRank][sourceFile-1].getPiece();
                if(temp instanceof Pawn && !(temp.getColor().equals(this.getColor())) && temp.isEnpessantEligible() && temp==board.getLastPieceMoved()){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }
            }
            //black is moving down and to the right
            if(destRank==(sourceRank+1) && destFile==(sourceFile+1)){
                Piece temp = board.getBoard()[sourceRank][sourceFile+1].getPiece();
                if(temp instanceof Pawn && !(temp.getColor().equals(this.getColor())) && temp.isEnpessantEligible() && temp==board.getLastPieceMoved()){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }
            }
            //white is moving up and to the left
            if(destRank==(sourceRank-1) && destFile==(sourceFile-1)){
                Piece temp = board.getBoard()[sourceRank][sourceFile-1].getPiece();
                if(temp instanceof Pawn && !(temp.getColor().equals(this.getColor())) && temp.isEnpessantEligible() && temp==board.getLastPieceMoved()){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }
            }
            //white is moving up and to the right
            if(destRank==(sourceRank-1) && destFile==(sourceFile+1)){
                Piece temp = board.getBoard()[sourceRank][sourceFile+1].getPiece();
                if(temp instanceof Pawn && !(temp.getColor().equals(this.getColor())) && temp.isEnpessantEligible() && temp==board.getLastPieceMoved()){
                    return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
                }

            }
        }else{
            //pawn is taking a a piece normally
            //check if the piece being taken is the other player's
            Piece temp = board.getBoard()[destRank][destFile].getPiece();
            if(!temp.getColor().equals(this.getColor()) && temp instanceof King) {
                return true;
            }
            if(!temp.getColor().equals(this.getColor())){
                return this.kingStillInCheckAfterMove(sourceRank,sourceFile,destRank,destFile,board);
            }
        }
        return false;
    }


    @Override
    public void move(int sourceRank, int sourceFile, int destRank, int destFile, Board board){
        //set this piece to has moved
        this.setHasMoved(true);
        //check if we should set enpessantEligible to true
        if((sourceRank==1&&destRank==3)||(sourceRank==6&&destRank==4)){
            this.setEnpessantEligible(true);
        }
        //en pessant implementation
        //black is moving down and to the left
        if(destRank==(sourceRank-1) && destFile==(sourceFile-1) && board.getBoard()[destRank][destFile].getPiece()==null){
            board.getBoard()[sourceRank][sourceFile-1].setPiece(null);
        }
        //black is moving down and to the right
        else if(destRank==(sourceRank-1) && destFile==(sourceFile+1) && board.getBoard()[destRank][destFile].getPiece()==null){
            board.getBoard()[sourceRank][sourceFile+1].setPiece(null);
        }
        //white is moving up and to the left
        else if(destRank==(sourceRank+1) && destFile==(sourceFile-1) && board.getBoard()[destRank][destFile].getPiece()==null){
            board.getBoard()[sourceRank][sourceFile-1].setPiece(null);
        }
        //white is moving up and to the right
        else if(destRank==(sourceRank+1) && destFile==(sourceFile+1) && board.getBoard()[destRank][destFile].getPiece()==null){
            board.getBoard()[sourceRank][sourceFile+1].setPiece(null);
        }
        // ==  operator checks for equality by object reference so use this when checking if the last piece moved
        //is an enpessant eligible pawn

        board.getBoard()[destRank][destFile].setPiece(null);
        board.getBoard()[destRank][destFile].setPiece(board.getBoard()[sourceRank][sourceFile].getPiece());
        board.getBoard()[sourceRank][sourceFile].setPiece(null);
        board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());

        //check if we should promote the pawn after the move
//        String input = board.getUserInput();
//        String [] splitInput = input.split(" ");
//        int len = splitInput.length;
        if(destRank==0 && this.getColor().equals("White")){
            //the pawn is white and has reached the opposite side of the board
//            if(len==3 && !(splitInput[len-1].equals("draw?"))){
//                Piece promotedPiece=null;
//                //we know that the user input a promotion piece
//                char promotionSymbol = splitInput[2].charAt(0);
//                switch(promotionSymbol){
//                    case 'Q':
//                        promotedPiece = new Queen("White");
//                        break;
//                    case 'N':
//                        promotedPiece = new Knight("White");
//                        break;
//                    case 'B':
//                        promotedPiece = new Bishop("White");
//                        break;
//                    case 'R' :
//                        promotedPiece = new Rook("White");
//                        break;
//                }
                //set the promoted piece on the board
//                board.getBoard()[destRank][destFile].setPiece(null);
//                board.getBoard()[destRank][destFile].setPiece(promotedPiece);
//                board.getBoard()[destRank][destFile].setPiece(new Queen("White"));
                //set the last piece moved to be the newly promoted piece
//                board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
//            }else{
                //the user did not specify a promotion piece so we make it a queen
                Piece queen = new Queen("White");
                board.getBoard()[destRank][destFile].setPiece(null);
                board.getBoard()[destRank][destFile].setPiece(queen);
                board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
//            }

        }
        if(destRank==7 && this.getColor().equals("Black")){
            //the pawn is black and reached the opposite side of the board
//            if(len==3 && !(splitInput[len-1].equals("draw?"))){
//                Piece promotedPiece=null;
                //we know that the user input a promotion piece
//                char promotionSymbol = splitInput[2].charAt(0);
//                switch(promotionSymbol){
//                    case 'Q':
//                        promotedPiece = new Queen("Black");
//                        break;
//                    case 'N':
//                        promotedPiece = new Knight("Black");
//                        break;
//                    case 'B':
//                        promotedPiece = new Bishop("Black");
//                        break;
//                    case 'R' :
//                        promotedPiece = new Rook("Black");
//                        break;
//                }
                //set the promoted piece on the board
//                board.getBoard()[destRank][destFile].setPiece(null);
//                board.getBoard()[destRank][destFile].setPiece(promotedPiece);
//                set the last piece moved to be the newly promoted piece
//                board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
//            }else{
                //the user did not specify a promotion piece so we make it a queen
                Piece queen = new Queen("Black");
                board.getBoard()[destRank][destFile].setPiece(null);
                board.getBoard()[destRank][destFile].setPiece(queen);
                board.setLastPieceMoved(board.getBoard()[destRank][destFile].getPiece());
//            }
        }
        if(this.getColor().equals("White")) {
            board.setWhiteInCheck(false);
        }else {
            board.setBlackInCheck(false);
        }
    }
}

