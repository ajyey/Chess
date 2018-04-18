package com.example.aj.chess_android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Game implements Serializable {
    private String name;
    private ArrayList<Board> boardConfigurations = new ArrayList<>();
    private String dateSaved;

    public Game(String name, ArrayList<Board> boardConfigurations, String dateSaved) {
        this.name = name;
        this.boardConfigurations = boardConfigurations;
        this.dateSaved = dateSaved;
    }

    public ArrayList<Board> getBoardConfigurations() {
        return boardConfigurations;
    }

    public void setBoardConfigurations(ArrayList<Board> boardConfigurations) {
        this.boardConfigurations = boardConfigurations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(String dateSaved) {
        this.dateSaved = dateSaved;
    }


}
