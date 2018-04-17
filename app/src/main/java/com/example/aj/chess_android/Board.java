package com.example.aj.chess_android;

import java.util.Scanner;

public class Board {
    /**
     * 2DArray that holds the pieces of the board in their respective locations
     */
    private Square[][] board;
    /**
     * Piece object that stores the piece that was moved on the most recent move
     */
    Piece lastPieceMoved;
    /**
     * boolean that tells whether the match is over or not
     */
    private boolean done;
    /**
     * boolean that tells whether the white player is in check
     */
    private boolean whiteInCheck;
    /**
     * boolean that tells whether the black player is in check
     */
    private boolean blackInCheck;
    /**
     * boolean that tells whether it is the white player's turn or not
     */
    private boolean whitesTurn;
    /**
     * boolean that tells whether the white player has proposed a draw to the black player
     */
    private boolean whiteProposedDraw;
    /**
     * boolean that tells whether the black player has proposed a draw to the white player
     */
    private boolean blackProposedDraw;
    /**
     * integer value that corresponds to the row location of the white king (set to 7 at the beginning)
     */
    private int whiteKingRank = 7;
    /**
     * integer value that corresponds to the column location of the white king (set to 4 at the beginning)
     */
    private int whiteKingFile = 4;
    /**
     * integer value that corresponds to the row location of the black king (set to 0 at the beginning)
     */
    private int blackKingRank = 0;
    /**
     * integer value that corresponds to the column location of the black king (set to 4 at the beginning)
     */
    private int blackKingFile = 4;
    /**
     * String that stores the input sent into standard input by the user
     */
    private String userInput;

    /**
     * This method returns a boolean that says whether the white player is proposing a draw to the black player.
     * @return whether the white player wants to propose a draw
     */
    public boolean isWhiteProposedDraw() {
        return whiteProposedDraw;
    }

    /**
     * This method sets a boolean that is used to tell whether the white player has proposed a draw for when it is the black player's turn.
     * @param whiteProposedDraw boolean value that whiteProposedDraw will be set to
     */
    public void setWhiteProposedDraw(boolean whiteProposedDraw) {
        this.whiteProposedDraw = whiteProposedDraw;
    }

    /**
     * This method returns a boolean that says whether the black player is proposing a draw to the white player.
     * @return whether the black player wants to propose a draw
     */

    public boolean isBlackProposedDraw() {
        return blackProposedDraw;
    }

    /**
     * This method sets a boolean that is used to tell whether the black player has proposed a draw for when it is the white player's turn.
     * @param blackProposedDraw boolean value that blackProposedDraw will be set to
     */
    public void setBlackProposedDraw(boolean blackProposedDraw) {
        this.blackProposedDraw = blackProposedDraw;
    }

    /**
     * This is the constructor that creates the board and places each piece in their respective positions in a 2DArray of Square objects which hold piece objects for the start of the match.
     */
    public Board(){

        //create the board of squares and set their colors correctly
        board = new Square[8][8];
        for(int i = 0;i<8;i++){
            for(int j =0;j<8;j++){
                if((i%2==0&&j%2==0)||(i%2!=0&&j%2!=0)){
                    board[i][j]=new Square("White");
                }else{
                    board[i][j]= new Square("Black");
                }
            }
        }
        for(int i = 0;i<8;i++){
            for(int j = 0;j < 8; j++){
                if(i==0){
                    switch(j){
                        case 0:
                            board[i][j].setPiece(new Rook("Black"));
                            break;
                        case 1:
                            board[i][j].setPiece(new Knight("Black"));
                            break;
                        case 2:
                            board[i][j].setPiece(new Bishop("Black"));
                            break;
                        case 3:
                            board[i][j].setPiece(new Queen("Black"));
                            break;
                        case 4:
                            board[i][j].setPiece(new King("Black"));
                            break;
                        case 5:
                            board[i][j].setPiece(new Bishop("Black"));
                            break;
                        case 6:
                            board[i][j].setPiece(new Knight("Black"));
                            break;
                        case 7:
                            board[i][j].setPiece(new Rook("Black"));
                            break;
                    }
                }else if(i==1){
                    board[i][j].setPiece(new Pawn("Black"));
                }else if(i==6){
                    board[i][j].setPiece(new Pawn("White"));
                }else if(i==7){
                    switch(j){
                        case 0:
                            board[i][j].setPiece(new Rook("White"));
                            break;
                        case 1:
                            board[i][j].setPiece(new Knight("White"));
                            break;
                        case 2:
                            board[i][j].setPiece(new Bishop("White"));
                            break;
                        case 3:
                            board[i][j].setPiece(new Queen("White"));
                            break;
                        case 4:
                            board[i][j].setPiece(new King("White"));
                            break;
                        case 5:
                            board[i][j].setPiece(new Bishop("White"));
                            break;
                        case 6:
                            board[i][j].setPiece(new Knight("White"));
                            break;
                        case 7:
                            board[i][j].setPiece(new Rook("White"));
                            break;
                    }
                }
            }
        }
    }
    public Board(boolean empty){
        //create the board of squares and set their colors correctly
        board = new Square[8][8];
        for(int i = 0;i<8;i++){
            for(int j =0;j<8;j++){
                if((i%2==0&&j%2==0)||(i%2!=0&&j%2!=0)){
                    board[i][j]=new Square("White");
                }else{
                    board[i][j]= new Square("Black");
                }
            }
        }
    }

    /**
     * This method takes the 2DArray created in the Board constructor and prints the board to standard out for the user of the program to visually see the match. It also adds a column at the end with numbers for each row and a row at the bottom with letters for each of column.
     */
    public void drawBoard(){
        int count = 8;
        for(int i =0;i<8;i++){
            for(int j = 0;j<8;j++){
                //if board square has a piece get that piece's type and use that
                if(!(board[i][j].getPiece()==null)){
                    // the square has a piece in it
                    System.out.print(board[i][j].getPiece().getSymbol()+" ");
                }else{
                    //the square does not have a piece in it
                    String squareSymbol = (board[i][j].getColor().equals("White") ? "  ": "##");
                    System.out.print(squareSymbol+" ");
                }
                //if board does not have a piece then we handle that appropriately as well
            }
            System.out.print(" "+count);
            count--;
            System.out.println();
        }
        System.out.println(" a  b  c  d  e  f  g  h  ");
        System.out.println();
    }

    /**
     * This method gets the player's move whether white or black.
     * @param whitesTurn boolean that is used to tell which player's turn it is
     * @return A string to be parsed when moving the piece to the player's desired location
     */
    public String getInput(boolean whitesTurn){
        String prompt = (isWhitesTurn()) ? "White's move: " : "Black's move: ";
        System.out.print(prompt);
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * This method checks whether the player's input is valid format. Also checks whether the player would like to resign.
     * @param input The string that was read in by getInput method
     * @param whitesTurn boolean that is used to tell which player's turn it is
     * @return a boolean that tells whether the user's input is valid
     */

    public boolean isValidInput(String input, boolean whitesTurn){
        //check for resignation
        if(input.trim().equals("resign")){
            String winner = (whitesTurn) ? "Black wins": "White wins";
            System.out.println(winner);
            //exit the program immediately
            System.exit(0);
        }else if(input.trim().equals("draw") && (this.blackProposedDraw||this.whiteProposedDraw)){
            //exit the program immediately
            System.exit(0);
        }else if(input.matches("[abcdefgh][12345678] [abcdefgh][12345678]") || input.matches("[abcdefgh][12345678] [abcdefgh][12345678] [QNRB]")){
            char sourceFile = input.split(" ")[0].charAt(0), sourceRank = input.split(" ")[0].charAt(1);
            int sourceFileInt = (int)sourceFile-97;
            int sourceRankInt =  8 - Character.getNumericValue(sourceRank);
            //check if the user selects an empty square
            if(this.getBoard()[sourceRankInt][sourceFileInt].getPiece()==null){
                return false;
            }
            if(this.getBoard()[sourceRankInt][sourceFileInt].getPiece().getColor().equals("White") && !isWhitesTurn()){
                return false;
            }
            if(this.getBoard()[sourceRankInt][sourceFileInt].getPiece().getColor().equals("Black") && isWhitesTurn()){
                return false;
            }
            return true;
            // regex match the input to what we deem as valid input
        }else if(input.matches("[abcdefgh][12345678] [abcdefgh][12345678] draw[?]")){
            if(whitesTurn){
                this.setWhiteProposedDraw(true);
            }else{
                this.setBlackProposedDraw(true);
            }
            return true;
        }
        //check if the letters and numbers pertain to the actual board
        //check if the person whose turn it is is trying to move a piece that is his
        return false;
    }

    /**
     * This method tells whether the respective player is in checkmate or not.
     * @return a boolean that tells whether in checkmate
     */
    public boolean checkmate(){
        if(this.isBlackInCheck()){
            //loop through all pieces and check if each piece has any valid moves anywhere on the board
            for(int i = 0;i<8;i++){
                for(int j = 0;j<8;j++){
                    Piece piece = this.getBoard()[i][j].getPiece();
                    if(piece!=null && piece.getColor().equals("Black")) {
                        for(int k= 0;k<8;k++) {
                            for(int l = 0;l<8;l++){
                                if(piece.isValidMove(i,j,k,l,this)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }else if(this.isWhiteInCheck()){
            //loop through all pieces and check if each piece has any valid moves anywhere on the board
            for(int i = 0;i<8;i++){
                for(int j = 0;j<8;j++){
                    Piece piece = this.getBoard()[i][j].getPiece();
                    if(piece!=null && piece.getColor().equals("White")) {
                        for(int k= 0;k<8;k++) {
                            for(int l=0;l<8;l++){
                                if(piece.isValidMove(i,j,k,l,this)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * This method tells whether the match is in a stalemate and will enc in a draw. A stalemate occurs when the player whose turn it is to move is not in check but has no legal move.
     * @return a boolean value that tells whether the match is at a stalemate
     */
    public boolean stalemate(){
        //whites turn
        if(this.isWhitesTurn() && !this.isWhiteInCheck()){

            for(int i = 0;i<8;i++){
                for(int j = 0;j<8;j++){
                    Piece piece = this.getBoard()[i][j].getPiece();
                    if(piece!=null && piece.getColor().equals("White")) {
                        for(int k= 0;k<8;k++) {
                            for(int l = 0;l<8;l++){
                                if(piece.isValidMove(i,j,k,l,this)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        //blacks turn
        if(!this.isWhitesTurn() && !this.isBlackInCheck()){
            for(int i = 0;i<8;i++){
                for(int j = 0;j<8;j++){
                    Piece piece = this.getBoard()[i][j].getPiece();
                    if(piece!=null && piece.getColor().equals("Black")) {
                        for(int k= 0;k<8;k++) {
                            for(int l=0;l<8;l++){
                                if(piece instanceof King){
                                    if(piece.isValidMove(i,j,k,l,this)) {
                                        return false;
                                    }
                                }
                                if(piece.isValidMove(i,j,k,l,this)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * This method tells whether the respective player is in check.
     * @param turn String variable that holds the value of which player's turn it is
     * @param whiteKingRank current row of the White player's king
     * @param whiteKingFile current column of the White player's king
     * @param blackKingRank current row of the Black player's king
     * @param blackKingFile current column of the Black player's king
     * @return a boolean that tells whether in check
     */
    public boolean check(String turn, int whiteKingRank, int whiteKingFile ,int blackKingRank, int blackKingFile){
        if(turn.equals("White")){
            for(int i = 0;i<8;++i){
                for(int j = 0; j < 8; ++j){
                    Piece piece = this.getBoard()[i][j].getPiece();
                    if(piece!=null && piece.getColor().equals("White")){
                        if(piece.isValidMove(i,j,blackKingRank,blackKingFile,this)){
                            return true;
                        }
                    }
                }
            }
            //this is how we will know if the king is in check
        }else{
            for(int i = 0;i<8;++i){
                for(int j = 0; j < 8; ++j){
                    Piece piece = this.getBoard()[i][j].getPiece();
                    if(piece!=null && piece.getColor().equals("Black")){
                        if(piece.isValidMove(i,j,whiteKingRank,whiteKingFile,this)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    //getters and setters

    /**
     * Getter method that returns the 2DArray board
     * @return the updated board 2DArray
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * Setter that sets the values of the 2DArray board to that of the inputted 2DArray of Square objects
     * @param board 2D array of Squares that the board will be set to
     */
    public void setBoard(Square[][] board) {
        this.board = board;
    }

    /**
     * Getter that checks to see whether the match is over by checking the done boolean whether it was set to true or not.
     * @return the boolean done that will correspond to the status of the match
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Setter that sets the done variable for the user.
     * @param done boolean that is passed in that will correspond to the value that done will be set to.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Getter that returns a boolean that tells whether player white is in check.
     * @return boolean that tells whether white is in check
     */
    public boolean isWhiteInCheck() {
        return whiteInCheck;
    }

    /**
     * Setter that sets the boolean whiteInCheck to the desired value that is passed in
     * @param whiteInCheck boolean value that whiteInCheck will be set to
     */
    public void setWhiteInCheck(boolean whiteInCheck) {
        this.whiteInCheck = whiteInCheck;
    }

    /**
     * Getter that returns a boolean that tells whether player black is in check.
     * @return boolean that tells whether black is in check
     */
    public boolean isBlackInCheck() {
        return blackInCheck;
    }

    /**
     * Setter that sets the boolean blackInCheck to the desired value that is passed in
     * @param blackInCheck boolean value that blackInCheck will be set to
     */
    public void setBlackInCheck(boolean blackInCheck) {
        this.blackInCheck = blackInCheck;
    }

    /**
     * Getter that returns the boolean whitesTurn that will tell whether it is white's turn or not
     * @return a boolean that will tell whether it is white's turn or not
     */
    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    /**
     * Setter that will set the boolean whitesTurn to the desired value that is passed in.
     * @param whitesTurn the boolean value that whitesTurn will be set to
     */
    public void setWhitesTurn(boolean whitesTurn) {
        this.whitesTurn = whitesTurn;
    }

    /**
     * Getter that returns the last piece that was moved to a new location on the board.
     * @return a Piece object that was the last piece moved on the board.
     */
    public Piece getLastPieceMoved() {
        return lastPieceMoved;
    }

    /**
     * Setter that sets the value of the Piece variable lastPieceMoved to the last piece that was moved in the most recent turn.
     * @param lastPieceMoved Piece object that will be set to the variable lastPieceMoved.
     */
    public void setLastPieceMoved(Piece lastPieceMoved) {
        this.lastPieceMoved = lastPieceMoved;
    }

    /**
     * Getter that returns the row in which the white king is currently located.
     * @return the number that corresponds to the row the white king is located.
     */
    public int getWhiteKingRank() {
        return whiteKingRank;
    }

    /**
     * Setter that sets the rank which the white king is located at.
     * @param whiteKingRank the integer value that corresponds to the row the white king is located.
     */
    public void setWhiteKingRank(int whiteKingRank) {
        this.whiteKingRank = whiteKingRank;
    }

    /**
     * Getter that returns the column in which the white king is currently located.
     * @return the ascii value that corresponds to the letter of the column the white king is located.
     */
    public int getWhiteKingFile() {
        return whiteKingFile;
    }

    /**
     * Setter that sets the file which the white king is located at
     * @param whiteKingFile the ascii value that corresponds to the column letter which the white king is located at
     */
    public void setWhiteKingFile(int whiteKingFile) {
        this.whiteKingFile = whiteKingFile;
    }

    /**
     * Getter that returns the row in which the black king is currently located.
     * @return the number that corresponds to the row the black king is located.
     */
    public int getBlackKingRank() {
        return blackKingRank;
    }

    /**
     * Setter that sets the rank which the black king is located at.
     * @param blackKingRank the integer value that corresponds to the row the black king is located.
     */
    public void setBlackKingRank(int blackKingRank) {
        this.blackKingRank = blackKingRank;
    }

    /**
     * Getter that returns the column in which the black king is currently located.
     * @return the ascii value that corresponds to the letter of the column the black king is located.
     */
    public int getBlackKingFile() {
        return blackKingFile;
    }

    /**
     * Setter that sets the file which the black king is located at
     * @param blackKingFile the ascii value that corresponds to the column letter which the black king is located at
     */
    public void setBlackKingFile(int blackKingFile) {
        this.blackKingFile = blackKingFile;
    }

    /**
     * Getter that returns the String input by the user into standard input
     * @return the string that holds the input sent in by the user
     */
    public String getUserInput(){
        return userInput;
    }

    /**
     * Setter that sets the string input to whatever the user send into standard input
     * @param input the string value that the variable input will be set to
     */
    public void setUserInput(String input){
        this.userInput = input;
    }
}
