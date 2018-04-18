package com.example.aj.chess_android;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private String name;
    private ArrayList<Move> moves;
    private String dateSaved;

    public Game(String name, ArrayList<Move> moves, String dateSaved) {
        this.name = name;
        this.moves = moves;
        this.dateSaved = dateSaved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public String getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(String dateSaved) {
        this.dateSaved = dateSaved;
    }
}
