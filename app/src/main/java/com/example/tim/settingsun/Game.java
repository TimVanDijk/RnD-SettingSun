package com.example.tim.settingsun;

import java.util.LinkedList;
import java.util.Observable;

/**
 * This game class is the model in our game.
 * It contains the puzzle and its history and it provides some methods to manipulate the current puzzle
 *
 * @author Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
 */
public class Game extends Observable {
    private LinkedList<Puzzle> history;
    private Puzzle puzzle;

    private final int DEFAULT_WIDTH = 4;
    private final int DEFAULT_HEIGHT = 5;

    private int width, height;
    private int moves = 0;

    /**
     * Constructor for game
     */
    public Game() {
        history = new LinkedList<>();
        puzzle = new Puzzle();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    /**
     * Adds a new puzzle to the history of puzzle states
     * @param p the puzzle that is to be added to the history
     */
    public void addPuzzle (Puzzle p) {
        history.add(p);
        moves++;
    }

    /**
     * Removes the newest puzzle in the history of puzzle states
     */
    public void removePuzzle () {
        if (history.size() > 0) {
            puzzle = history.removeLast();
            moves--;
        }
    }

    /**
     * Checks if the puzzle has been solved
     * @return True if the puzzle is solved. False otherwise.
     */
    public boolean isGameWon (){
        return puzzle.isGameWon();
    }

    /**
     * Getter for puzzle
     * @return the current state of the puzzle that the game is using
     */
    public Puzzle getPuzzle () {
        return puzzle;
    }

    /**
     * Getter for width
     * @return the width of the puzzle (in blocks)
     */
    public int getWidth () {
        return width;
    }

    /**
     * Getter for height
     * @return the height of the puzzle (in blocks)
     */
    public int getHeight () {
        return height;
    }

    /**
     * Getter for moves
     * @return the amount of moves that have been made so far
     */
    public int getMoves () {
        return moves;
    }

    /**
     * Sets the amount of moves to param_moves
     * @param moves the amount of moves that moves needs to be set to
     */
    public void setMoves (int moves) {
        this.moves = moves;
    }
}

