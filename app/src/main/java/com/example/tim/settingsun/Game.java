package com.example.tim.settingsun;

import android.util.Log;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by tim on 28-4-15.
 */
public class Game extends Observable {
    private LinkedList<Puzzle> history;
    private Puzzle puzzle;
    public int moves=0;

    private final int DEFAULT_WIDTH = 4;
    private final int DEFAULT_HEIGHT = 5;

    private int width, height;

    public Game () {
        history = new LinkedList<>();
        puzzle = new Puzzle();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addPuzzle(Puzzle p) {
        history.add(p);
        moves++;
    }

    public void removePuzzle() {
        if (history.size()>0) {
            puzzle = history.removeLast();
            moves--;
        }
    }

    public boolean isGameWon(){
        return puzzle.isGameWon();
    }

}
