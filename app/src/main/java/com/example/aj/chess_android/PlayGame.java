package com.example.aj.chess_android;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class PlayGame extends AppCompatActivity {
    private int gameIndex = 0;

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Game gameToPlay = (Game)intent.getSerializableExtra("gameObject");
        System.out.println(gameToPlay.getName());
        setTitle("Play game: "+gameToPlay.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ArrayList<Board> currentGameBoards = gameToPlay.getBoardConfigurations();
        //set the board UI
        final TableLayout currentBoardLayout = (TableLayout)findViewById(R.id.playGameBoardLayout);
        //should redraw the board with the start configuration
        redrawBoard(currentBoardLayout,currentGameBoards.get(getGameIndex()));



        //attach listeners for forward buttons
        ImageButton forward = (ImageButton)findViewById(R.id.playGameForward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getGameIndex() == currentGameBoards.size()-1){
                    return;
                }
                //increment the game index
                setGameIndex(getGameIndex()+1);
                redrawBoard(currentBoardLayout,currentGameBoards.get(getGameIndex()));

                //handle check and checkmate
            }
        });

        //attach listener for backward button
        ImageButton backward = (ImageButton)findViewById(R.id.playGameBackward);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the game index is 0 then return since we cant go back any further than the first configuration
                if(getGameIndex()==0){
                    return;
                }
                //decrement the game index
                setGameIndex(getGameIndex()-1);
                redrawBoard(currentBoardLayout,currentGameBoards.get(getGameIndex()));
                //handle check and checkmate
            }
        });

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

}
