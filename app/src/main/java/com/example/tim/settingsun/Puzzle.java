package com.example.tim.settingsun;

/**
* The puzzle class stores a puzzle and provides some methods to extract information from that puzzle.
* @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
*/
public class Puzzle {
    public int[][] puzzle;

    /**
     * Constructor for puzzle.
     */
    public Puzzle() {
        initializePuzzle();
    }

    /**
     * Copy constructor for puzzle p
     * @param p the puzzle whose data we copy into this puzzle
     */
    public Puzzle(Puzzle p){
        puzzle = new int[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                puzzle[i][j] = p.puzzle[i][j];
            }
        }
    }

    /**
     * Adds a block to the puzzle
     * @param x the x coordinate of the top-left corner of the block
     * @param y the y coordinate of the top-left corner of the block
     * @param type the type of the block
     * @return true if the block has successfully been added. False otherwise.
     */
    private boolean addBlock(int x, int y, int type) {

        boolean valid = true;

        for (int i = 0; i < BlockInfo.getDimensions(type).x; i++) {
            for (int j = 0; j < BlockInfo.getDimensions(type).y; j++) {
                if (puzzle[x + i][y + j] != 0)
                    valid = false;
            }
        }

        if (valid) {
            puzzle[x][y] = type;
            for (int i = 0; i < BlockInfo.getDimensions(type).x; i++) {
                for (int j = 0; j < BlockInfo.getDimensions(type).y; j++) {
                    if (puzzle[x + i][y + j] == 0) {
                        puzzle[x + i][y + j] = -1;
                    }

                }
            }
        }
        return valid;
    }


    /**
     * Sets the puzzle's dimensions and fills it with blocks to create the setting sun problem
     */
    public void initializePuzzle () {
        puzzle = new int[4][5];

        addBlock(0,0,2);
        addBlock(1,0,4);
        addBlock(3,0,2);
        addBlock(1,2,3);
        addBlock(0,3,2);
        addBlock(3,3,2);
        addBlock(1,3,1);
        addBlock(2,3,1);
        addBlock(1,4,1);
        addBlock(2,4,1);
    }

    /**
     * Adds all blocks in the puzzle to an array. Then returns that array.
     * @return the array of all blocks in the puzzle.
     */
    public Block[] getBlocks () {
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

    /**
     * Moves the block whose top-left coordinates are (x, y) in direction d
     * @param x the x coordinate of the top-left corner of the block
     * @param y the y coordinate of the top-left corner of the block
     * @param d the direction in which the block is to be moved
     */
    public void moveBlock(int x, int y, Direction d) {
            int type = puzzle[x][y];
            int width = BlockInfo.getDimensions(puzzle[x][y]).x;
            int height = BlockInfo.getDimensions(puzzle[x][y]).y;

            puzzle[x][y] = 0;
            puzzle[x + width - 1][y] = 0;
            puzzle[x][y + height - 1] = 0;
            puzzle[x + width - 1][y + height - 1] = 0;

            addBlock(x + d.dx, y + d.dy, type);

    }

    /**
     * Checks if the specified block can move in direction d
     * @param x the x coordinate of the top-left corner of the block
     * @param y the y coordinate of the top-left corner of the block
     * @param d the the direction in which the block is to be moved
     * @return true if we can move the block in that direction and the game has not been won already. False otherwise.
     */
    public boolean canMove (int x, int y, Direction d) {
        int width = BlockInfo.getDimensions(puzzle[x][y]).x;
        int height = BlockInfo.getDimensions(puzzle[x][y]).y;

        if (isGameWon())
            return false;

        //check for collision with puzzle boundaries
        if (x + d.dx < 0)
            return false;
        if (x + width + d.dx - 1 >= 4)
            return false;
        if (y + d.dy < 0)
            return false;
        if (y + height + d.dy - 1 >= 5)
            return false;

        //Check for collision which another block
        if (d.dx > 0) {
            if (puzzle[x + width + d.dx - 1][y] != 0)
                return false;
            if (puzzle[x + width + d.dx - 1][y + height - 1] != 0)
                return false;
        }

        if (d.dx < 0) {
            if (puzzle[x + d.dx][y] != 0)
                return false;
            if (puzzle[x + d.dx][y + height - 1] != 0)
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

    /**
     * Checks whether the puzzle is solved. It checks this by looking if the sun is in the right position.
     * @return true if the puzzle is solved. False otherwise.
     */
    public boolean isGameWon () {
        return puzzle[1][3] == 4;
    }
}