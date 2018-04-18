package com.example.aj.chess_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PlayGame extends AppCompatActivity {

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
    }

}
