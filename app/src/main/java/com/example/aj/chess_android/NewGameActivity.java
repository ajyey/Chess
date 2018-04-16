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

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Good Luck!");

        //handle the back button to make sure that the user wants to go back
        //pressing the back button will not save the currently running game


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        boolean isWhitesTurn = true;
        TextView currentTurn = (TextView) findViewById(R.id.currentTurn);
        currentTurn.setText("White");
        TableLayout table = (TableLayout) findViewById(R.id.boardLayout);
        //gets the first table row
        View view = table.getChildAt(0);
        if(view instanceof TableRow){
            TableRow row = (TableRow) view;
            ImageView img = (ImageView)row.getChildAt(0);
            System.out.println(img.getResources().getResourceName(img.getId()));

            //we can use the following line to update the board's square when we are moving pieces around
//            img.setImageResource(R.drawable.black_knight);

            //we can use the following line to empty a square in the UI
//            img.setImageDrawable(null);

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
