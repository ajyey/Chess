package com.example.aj.chess_android;

import java.io.Serializable;

/**
 * Created by aj on 4/17/18.
 */

public class Move implements Serializable {
    private int sourceRank;
    private int sourceFile;
    private int destRank;
    private int destFile;

    public Move(int sourceRank, int sourceFile, int destRank, int destFile) {
        this.sourceRank = sourceRank;
        this.sourceFile = sourceFile;
        this.destRank = destRank;
        this.destFile = destFile;
    }

    public int getSourceRank() {
        return sourceRank;
    }

    public void setSourceRank(int sourceRank) {
        this.sourceRank = sourceRank;
    }

    public int getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(int sourceFile) {
        this.sourceFile = sourceFile;
    }

    public int getDestRank() {
        return destRank;
    }

    public void setDestRank(int destRank) {
        this.destRank = destRank;
    }

    public int getDestFile() {
        return destFile;
    }

    public void setDestFile(int destFile) {
        this.destFile = destFile;
    }
}
