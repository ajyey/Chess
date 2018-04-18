package com.example.aj.chess_android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListSavedGames extends AppCompatActivity implements Serializable {
    public static final String storeDir = "app/src/main/dat";
    public static final String storeFile = "games.dat";
    public static ArrayList<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_saved_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Your Saved Games");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //read from the serialized games
        try {
            games = readApp(this);
            System.out.println(games);
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public static void writeApp(ArrayList<Game> gamesToSave, Context context) throws IOException{
            FileOutputStream fileOutputStream = context.openFileOutput(storeFile, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gamesToSave);
            objectOutputStream.close();
            fileOutputStream.close();

    }

    public static ArrayList<Game> readApp(Context context) throws IOException, ClassNotFoundException{
        System.out.println(context.getFilesDir());
        ArrayList<Game> readGames = new ArrayList<>();
        FileInputStream fileInputStream = context.openFileInput(storeFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        readGames = (ArrayList<Game>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return readGames;

    }

}
