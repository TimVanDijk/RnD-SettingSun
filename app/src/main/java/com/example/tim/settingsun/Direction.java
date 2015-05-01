package com.example.tim.settingsun;

/**
 * This class is an enumeration of directions.
 * @author Maurice Knoop (re-used his code from  his "Snake" game)
 */
public enum Direction {

    UP(0,-1), RIGHT(1,0),DOWN(0,1),LEFT(-1,0);

    final int dx,dy;

    /**
     * Constructor for Direction
     * @param dx the distance that is moved on the horizontal axis
     * @param dy the distance that is moved on the vertical axis
     */
    private Direction(int dx, int dy)
    {
        this.dx=dx;
        this.dy=dy;
    }

    /**
     * Computes the general direction given the distance that is moved on the horizontal axis and on the vertical axis
     * @param f he distance that is moved on the horizontal axis
     * @param g the distance that is moved on the vertical axis
     * @return the general direction in which is moved
     */
    public static Direction between(float f, float g)
    {
        if(Math.abs(f) > Math.abs(g))
            if(f > 0)
                return RIGHT;
            else
                return LEFT;
        else
        if(g > 0)
            return DOWN;
        else
            return UP;
    }

}