package com.example.tim.settingsun;

/**
 * This class stores a block's source coordinates and its type.
 * Combining the info in this class and that of BlockInfo we can figure out anything we need to know about the blocks in the puzzle
 * @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
 */
public class Block {
    public int type;
    public float x, y;

    /**
     * Constructor for Block
     * @param x the x coordinate of the block
     * @param y the y coordinate of the block
     * @param type the type of the block
     */
    public Block(int x, int y, int type){
        this.x = (float) x;
        this.y = (float) y;
        this.type = type;
    }
}
