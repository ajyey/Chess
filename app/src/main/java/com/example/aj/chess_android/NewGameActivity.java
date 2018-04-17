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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

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

        //gets the first table row
        View view = table.getChildAt(0);
        if(view instanceof TableRow){
            TableRow row = (TableRow) view;
            //gets the first square in the row
            ImageView img = (ImageView)row.getChildAt(0);
            System.out.println(img.getResources().getResourceName(img.getId()));

            //we can use the following line to update the board's square when we are moving pieces around
//            img.setImageResource(R.drawable.black_knight);

            //we can use the following line to empty a square in the UI
//            img.setImageDrawable(null);

        }

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
    //user click handler
    //TODO:
    public void handleUserClick(View v){
        //check whether the click is a source click or a destination click
        //get the spot that was clicked
        TableLayout table = (TableLayout) findViewById(R.id.boardLayout);
        //get the clicked row
        TableRow tableRow = (TableRow)v.getParent();
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
            }
            //check that that user is trying to move his own piece
            if(!(movingOwnPiece(row, col))){
                Toast.makeText(NewGameActivity.this, "That is not your piece!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else{ //the user is clicking the destination
            //the click is a destination click
            //check if the user clicked an empty square
            if(game.getBoard()[row][col].getPiece()==null){
                //set both source and destination to null
                setSourceClick(null);
                setDestinationClick(null);
            }
            //check that that user is trying to move his own piece
            if(!(movingOwnPiece(row, col))){
                Toast.makeText(NewGameActivity.this, "That is not your piece!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

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
