package com.example.tim.settingsun;

import java.util.LinkedList;
import java.util.Observable;

/**
 * This game class is the model in our game.
 * It contains the puzzle and its history and it provides some methods to manipulate the current puzzle
 * @author Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
 */
public class Game extends Observable {
    private LinkedList<Puzzle> history;
    private Puzzle puzzle;

    private final int DEFAULT_WIDTH = 4;
    private final int DEFAULT_HEIGHT = 5;

    private int width, height;
    private int moves = 0;

    public Game() {
        history = new LinkedList<>();
        puzzle = new Puzzle();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public void addPuzzle (Puzzle p) {
        history.add(p);
        moves++;
    }

    public void removePuzzle () {
        if (history.size() > 0) {
            puzzle = history.removeLast();
            moves--;
        }
    }

    public boolean isGameWon (){
        return puzzle.isGameWon();
    }

    public Puzzle getPuzzle () {
        return puzzle;
    }

    public int getWidth () {
        return width;
    }

    public int getHeight () {
        return height;
    }

    public int getMoves () {
        return moves;
    }

    public void setMoves (int moves) {
        this.moves = moves;
    }
}

