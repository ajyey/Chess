package com.example.aj.chess_android;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewGameActivity extends AppCompatActivity {

    private ImageView sourceClick = null;

    public ImageView getSourceClick() {
        return sourceClick;
    }

    public void setSourceClick(ImageView sourceClick) {
        this.sourceClick = sourceClick;
    }

    public ImageView getDestinationClick() {
        return destinationClick;
    }

    public void setDestinationClick(ImageView destinationClick) {
        this.destinationClick = destinationClick;
    }

    private ImageView destinationClick = null;

    private Board game;

    public int getSourceRow() {
        return sourceRow;
    }

    public void setSourceRow(int sourceRow) {
        this.sourceRow = sourceRow;
    }

    public int getSourceCol() {
        return sourceCol;
    }

    public void setSourceCol(int sourceCol) {
        this.sourceCol = sourceCol;
    }

    public int getDestRow() {
        return destRow;
    }

    public void setDestRow(int destRow) {
        this.destRow = destRow;
    }

    public int getDestCol() {
        return destCol;
    }

    public void setDestCol(int destCol) {
        this.destCol = destCol;
    }

    private int sourceRow;
    private int sourceCol;
    private int destRow;
    private int destCol;
    private Board previousBoard;
    private Piece previousPieceMoved;

    public Piece getPreviousPieceMoved() {
        return previousPieceMoved;
    }

    public void setPreviousPieceMoved(Piece previousPieceMoved) {
        this.previousPieceMoved = previousPieceMoved;
    }

    public Board getPreviousBoard() {
        return previousBoard;
    }

    public void setPreviousBoard(Board previousBoard) {
        this.previousBoard = previousBoard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Good Luck!");

        //handle the back button to make sure that the user wants to go back
        //pressing the back button will not save the currently running game

        //TODO;
            //create a representation of the board that we can use as a model for the game

        //create model for the game
        game = new Board();
        game.setWhitesTurn(true);
        //set the current turn text to reflect the current turn
        setTurn(game.isWhitesTurn());
        //set the listeners for each imageview in the chess board
        TableLayout table = (TableLayout) findViewById(R.id.boardLayout);
        setListenersForTable(table);

        //set the function listener for the undo button
        Button undoButton = (Button)findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean whiteTurn = game.isWhitesTurn();
                game = getPreviousBoard();
                game.setLastPieceMoved(getPreviousPieceMoved());
                game.setWhitesTurn(!whiteTurn);
                setTurn(game.isWhitesTurn());
                TableLayout table = (TableLayout) findViewById(R.id.boardLayout);
                redrawBoard(table,game);
            }
        });
        //set the function listener for the ai button
        Button aiButton = (Button)findViewById(R.id.aiButton);
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the current turn
                List<Move> validMoves = new ArrayList<>();
                for(int i = 0;i<8;i++){
                    for(int j = 0;j<8;j++){
                        if(game.getBoard()[i][j].getPiece()==null){
                            continue;
                        }
                        Piece temp = game.getBoard()[i][j].getPiece();
                        if(game.isWhitesTurn() && temp.getColor().equals("White")){
                            //we are on a white piece
                            //loop through the board and get all the valid moves for this piece
                            for(int a = 0;a<8;a++){
                                for(int b=0;b<8;b++){
                                    if(a==i && b==j){
                                        continue;
                                    }
                                    if(temp.isValidMove(i,j,a,b,game)){
                                        validMoves.add(new Move(i,j,a,b));
                                    }
                                }
                            }
                        }else if(!(game.isWhitesTurn()) && temp.getColor().equals("Black")){
                            for(int a = 0;a<8;a++){
                                for(int b=0;b<8;b++){
                                    if(a==i && b==j){
                                        continue;
                                    }
                                    if(temp.isValidMove(i,j,a,b,game)){
                                        validMoves.add(new Move(i,j,a,b));
                                    }
                                }
                            }
                        }
                    }
                }
                //pick a random move to execute
                Random rand = new Random();
                System.out.println("valid moves size");
                System.out.println(validMoves.size());
                int randomMoveIndex = rand.nextInt(validMoves.size());
                //execute the move
                Move move = validMoves.get(randomMoveIndex);
                Piece pieceToMove = game.getBoard()[move.getSourceRank()][move.getSourceFile()].getPiece();
                pieceToMove.move(move.getSourceRank(),move.getSourceFile(),move.getDestRank(),move.getDestFile(),game);
                game.setWhitesTurn(!(game.isWhitesTurn()));
                //redraw the board
                TableLayout table = (TableLayout) findViewById(R.id.boardLayout);
                redrawBoard(table, game);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Attaches listeners for a user's clicks for each Square on the chess board
     * @param layout Passed in TableLayout that refers to the chess board
     */
    public void setListenersForTable(TableLayout layout){
        for(int i = 0; i < 8; i++){
            TableRow row = (TableRow)layout.getChildAt(i);
            for(int j = 0;j < 8;j++){
                ImageView img = (ImageView)row.getChildAt(j);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //call the click handler
                        handleUserClick(v);
                    }
                });
            }
        }
    }

    /**
     * Create a copy of the current game board to keep as a previous configuration for the undo functionality
     * @param board The current game's board
     * @return A new temporary board of the previous move's configuration
     */
    public Board createCopyOfBoard(Board board){
        Board temp = new Board(true);
        //temp is now an empty board
        //copy the current game board into the temp board so we can perform the undo functionality
        for(int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
                temp.getBoard()[i][j].setPiece(board.getBoard()[i][j].getPiece());
            }
        }
        return temp;
    }
    public void redrawBoard(TableLayout layout, Board board){
        //loop through the game board and add the respective pieces that correlate to the board we
        //have modeled
        for(int i = 0; i<8;i++){
            TableRow row = (TableRow)layout.getChildAt(i);
            for(int j = 0;j<8;j++){
                ImageView img = (ImageView)row.getChildAt(j);
                //check if there is no piece there
                if(board.getBoard()[i][j].getPiece()==null){
                    img.setImageDrawable(null);
                    continue;
                }
                String symbol = board.getBoard()[i][j].getPiece().getSymbol();
                    //cases for symbols
                    switch(symbol){
                        //Pawn
                        case "wp":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.white_pawn);
                            break;
                        //Bishop
                        case "wB":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.white_bishop);
                            break;
                        case "wK":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.white_king);
                            break;
                        case "wQ":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.white_queen);
                            break;
                        case "wN":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.white_knight);
                            break;
                        case "wR":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.white_rook);
                            break;
                        case "bp":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.black_pawn);
                            break;
                        //Bishop
                        case "bB":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.black_bishop);
                            break;
                        case "bK":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.black_king);
                            break;
                        case "bQ":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.black_queen);
                            break;
                        case "bN":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.black_knight);
                            break;
                        case "bR":
                            img.setImageDrawable(null);
                            img.setImageResource(R.drawable.black_rook);
                            break;
                    }


            }
        }
    }
    //user click handler
    //TODO:
    public void handleUserClick(View v){
        //check whether the click is a source click or a destination click
        //get the spot that was clicked
        TableLayout table = (TableLayout) findViewById(R.id.boardLayout);
        //get the clicked row
        TableRow tableRow = (TableRow)v.getParent();
        ImageView img = (ImageView)tableRow.getChildAt(tableRow.indexOfChild(v));
        int row = table.indexOfChild(tableRow);
        int col = tableRow.indexOfChild(v);


        //the user is clicking what piece he wants to move
        if(getSourceClick()==null){
            //this is a source click
            //check if the user is trying to move a piece that isnt his

            //check if the user clicked an empty square
            if(game.getBoard()[row][col].getPiece()==null){
                //set both source and destination to null
                setSourceClick(null);
                setDestinationClick(null);
                return;
            }
            //check that that user is trying to move his own piece
            if(!(movingOwnPiece(row, col))){
                Toast.makeText(NewGameActivity.this, "That is not your piece!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            //set the source click imageview
            setSourceClick(img);
            setSourceRow(row);
            setSourceCol(col);
        }else{ //the user is clicking the destination
            //the click is a destination click
            //check if the user clicked an empty square
            if(game.getBoard()[row][col].getPiece()==null){
                //set both source and destination to null
                setSourceClick(null);
                setDestinationClick(null);
            }
            //check that the move is valid
            Piece piece = game.getBoard()[sourceRow][sourceCol].getPiece();
            if(piece.isValidMove(sourceRow,sourceCol,row,col,game)){
                //set the previous board for undo functionality
                setPreviousBoard(createCopyOfBoard(game));
                setPreviousPieceMoved(game.getLastPieceMoved());
                piece.move(sourceRow,sourceCol,row,col,game);
                redrawBoard(table,game);
                //set the opposite turn
                game.setWhitesTurn(!(game.isWhitesTurn()));
                //reflect the turn in the UI
                setTurn(game.isWhitesTurn());
                //check for check

                //TODO:
                //call handler for end of game
                checkHandler();
                checkmateHandler();

            }else{
                Toast.makeText(NewGameActivity.this, "Invalid move!",
                        Toast.LENGTH_SHORT).show();
            }
            setSourceClick(null);
            setDestinationClick(null);


        }
    }
    public void checkHandler(){
        TextView textView = (TextView) findViewById(R.id.gameResult);
        if(game.check("White", game.getWhiteKingRank(),game.getWhiteKingFile(),game.getBlackKingRank(),game.getBlackKingFile()) && game.isWhitesTurn()){
            game.setBlackInCheck(true);
            textView.setText("Black is in check");
        }else if(game.check("Black",game.getWhiteKingRank(),game.getWhiteKingFile(),game.getBlackKingRank(),game.getBlackKingFile()) && !game.isWhitesTurn()){
            game.setWhiteInCheck(true);
            textView.setText("White is in check");
        }else{
            textView.setText(null);
        }
    }
    //TODO:
        //implement the dialog to ask the user if they want save the game and provide a text input for a name for the game
    public void checkmateHandler(){
        TextView textView = (TextView) findViewById(R.id.gameResult);

        if(game.checkmate()){
            String winner = (game.isWhitesTurn()) ? "White wins!": "Black wins!";
            textView.setText(winner);
        }
    }

    /**
     * Changes the turn that appears to the user
     * @param isWhitesTurn Boolean representing if it is white's turn or not
     */
    public void setTurn(boolean isWhitesTurn){
        TextView currentTurn = (TextView) findViewById(R.id.currentTurn);
        if(!isWhitesTurn){
            currentTurn.setText("Black");
        }else{
            currentTurn.setText("White");
        }
    }
    public boolean movingOwnPiece(int row, int col){
        if(game.getBoard()[row][col].getPiece()==null){
            return false;
        }
        if(game.getBoard()[row][col].getPiece().getColor().equals("White")){
            if(game.isWhitesTurn()==false){
                return false;
            }
        }else{
            if(game.isWhitesTurn()){
                return false;
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                NewGameActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Leave?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the game? It won't be saved.");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
