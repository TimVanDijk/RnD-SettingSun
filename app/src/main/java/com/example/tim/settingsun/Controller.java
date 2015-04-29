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

    Controller(Game game)
    {
        this.game = game;
        this.handler = new Handler();
        this.resetTouchLocation();
    }


    private void resetTouchLocation()
    {
        this.startx = -1;
        this.starty = -1;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("Controller","Touched");

        if(startx == -1 || starty == -1)
        {
            startx = event.getX();
            starty = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            game.playerMove(startx, starty, Direction.between(event.getX()-startx, event.getY()-starty));
            this.resetTouchLocation();
        }

        return true;
    }

    public void start(){
        //Do something useful
    }
}
