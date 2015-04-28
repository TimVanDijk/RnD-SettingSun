package com.example.tim.settingsun;

/**
 * Created by tim on 28-4-15.
 */
public class Block {
    public int type;
    public float x, y;

    public Block(int x, int y, int type){
        this.x = (float) x;
        this.y = (float) y;
        this.type = type;
    }
}
