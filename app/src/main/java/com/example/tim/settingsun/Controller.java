package com.example.tim.settingsun;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.util.Observable;

/**
 * Created by tim on 28-4-15.
 */
public class Controller extends Observable implements OnTouchListener{

    private float startx, starty;

    private Game game;

    private Handler handler;

    private SunView view;

    Block a;

    Controller(Game game, SunView view)
    {
        Log.d("motion","controller created");
        this.game = game;
        this.handler = new Handler();
        this.resetTouchLocation();
        this.view = view;
    }


    private void resetTouchLocation()
    {
        this.startx = -1;
        this.starty = -1;
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.d("Controller","Touched");

        if(startx == -1 || starty == -1)
        {
            startx = event.getX();
            starty = event.getY();

            boolean found=false;
            for (Block b: game.getPuzzle().getBlocks()) {
                Log.d("blockcheck","checked");
                float left = view.getBlockCoords(b).x;
                float right = view.getBlockSize(b).x + left;
                float top = view.getBlockCoords(b).y;
                float bottom = view.getBlockSize(b).y + top;

                if (startx >= left && startx <= right && starty >= top && starty <= bottom) {
                    a = b;
                    found=true;
                }
            }
            if (!found) {
                a=null;
                Log.d("Monitor","Ongeldig blok proberen te verschuiven");
                this.resetTouchLocation();
            }

        }

        if (a!=null) {

        }

        if(event.getAction() == MotionEvent.ACTION_UP && a!=null)
        {
            Log.d("motionevent","motion detected ax="+a.x+" ay="+a.y);
            Direction d = Direction.between(event.getX()-startx, event.getY()-starty);
            //check if can move
            if (game.getPuzzle().canMove((int)a.x,(int)a.y,d)) {
                game.addPuzzle(new Puzzle(game.getPuzzle()));
                Log.d("check","check");
                game.getPuzzle().moveBlock((int)a.x,(int)a.y,d);
                Log.d("after","after");
                a=null;
                view.postInvalidate();
            }
            else {
                a=null;
            }
            //execute move
            this.resetTouchLocation();
        }

        return true;
    }

    public void start(){
        //Do something useful
    }
}
