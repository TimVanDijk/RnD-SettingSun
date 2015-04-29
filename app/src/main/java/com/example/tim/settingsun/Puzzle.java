package com.example.tim.settingsun;

/**
 * Created by tim on 28-4-15.
 */
public class Puzzle {
    public int[][] puzzle;

    public Puzzle() {
        initializePuzzle();
    }

    public Puzzle(Puzzle p){
        puzzle = new int[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                puzzle[i][j] = p.puzzle[i][j];
            }
        }
    }

    private boolean addBlock(int x, int y, int type) {

        boolean test = true;

        for (int i = 0; i < BlockInfo.getDimensions(type).x - 1; i++) {
            for (int j = 0; j < BlockInfo.getDimensions(type).y - 1; j++) {
                if (puzzle[x + i][y + j] != 0)
                    test = false;
            }
        }

        if (test) {
            puzzle[x][y] = type;
            for (int i = 0; i < BlockInfo.getDimensions(type).x - 1; i++) {
                for (int j = 0; j < BlockInfo.getDimensions(type).y - 1; j++) {
                    puzzle[x + i][y + j] = -1;
                }
            }
        }

        return test;
    }

    public void initializePuzzle() {
        puzzle = new int[4][5];

        addBlock(0,0,2);
        /*addBlock(3,0,2);
        addBlock(0,3,2);
        addBlock(3,3,2);
        addBlock(1,2,3);
        addBlock(1,0,4);
        addBlock(2,3,1);
        addBlock(2,3,1);
        addBlock(3,3,1);
        addBlock(3,3,1);*/
    }

    public Block[] getBlocks() {
        Block[] blocks = new Block[10];
        int index = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (puzzle[i][j] > 0) {
                    blocks[index] = new Block(i, j, puzzle[i][j]);
                    index++;
                }
            }
        }
        return blocks;
    }

    public void moveBlock(int x, int y, Direction d) {
        if (canMove(x, y, d)) {
            int type = puzzle[x][y];
            int width = BlockInfo.getDimensions(puzzle[x][y]).x;
            int height = BlockInfo.getDimensions(puzzle[x][y]).y;

            puzzle[x][y] = 0;
            puzzle[x + width - 1][y] = 0;
            puzzle[x][y + height - 1] = 0;
            puzzle[x + width - 1][y + height - 1] = 0;

            addBlock(x + d.dx, y + d.dy, type);
        }

    }

    private boolean canMove(int x, int y, Direction d){
        int width = BlockInfo.getDimensions(puzzle[x][y]).x;
        int height = BlockInfo.getDimensions(puzzle[x][y]).y;

        //check of binnen scherm
        //check hoeft niet aan beide kanten, want als waar voor 1 kant, ook waar voor ander
        if (x + d.dx < 0)
            return false;
        if (x + width + d.dx - 1 > 4)
            return false;
        if (y + d.dy < 0)
            return false;
        if (y + height + d.dy - 1 > 5)
            return false;

        //check of botsing
        if (d.dx > 0) {
            if (puzzle[x + width + d.dx - 1][y] != 0)
                return false;
            if (puzzle[x + width + d.dx - 1][y + height - 1] != 0)
                return false;
        }

        if (d.dx < 0) {
            if (puzzle[x + d.dx][y] != 0)
                return false;
            if (puzzle[x + d.dx][y + height] != 0)
                return false;
        }

        if (d.dy > 0) {
            if (puzzle[x][y + height + d.dy - 1] != 0)
                return false;
            if (puzzle[x + width - 1][y + height + d.dy - 1] != 0)
                return false;
        }

        if (d.dy < 0) {
            if (puzzle[x][y + d.dy] != 0)
                return false;
            if (puzzle[x + width - 1][y + d.dy] != 0)
                return false;
        }

        return true;
    }
}