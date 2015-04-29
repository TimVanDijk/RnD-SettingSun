package com.example.tim.settingsun;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by tim on 28-4-15.
 */
public class Game extends Observable {
    private LinkedList<Puzzle> history;
    private Puzzle puzzle;

    private final int DEFAULT_WIDTH = 9;
    private final int DEFAULT_HEIGHT = 16;

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
}
