package com.example.tim.settingsun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends Activity implements Observer {

    private Game game;
    private Controller controller;
    private SunView sv;

    private Button b;

    private MainActivity myself=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();



        sv = (SunView) this.findViewById(R.id.sunview);
        sv.setGame(game);

        controller = new Controller(game, sv);
        controller.addObserver(this);
        sv.setOnTouchListener(controller);
        controller.start();

        b = (Button) this.findViewById(R.id.button);
        //b.setPadding(b.getLeft(),b.getTop(), b.getRight(),(int)sv.getMargin_vertical());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object o) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VICTORY");
        builder.setMessage("Moves used: "+game.moves);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        game.removePuzzle();
        sv.postInvalidate();
    }

    public void createResetDialog(View v) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder (this);
        resetAlert.setTitle("Reset puzzle?");
        final CharSequence[] options = {"Yes","No"};
        resetAlert.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item){
                if (item == 0) {
                    game = new Game();
                    sv.setGame(game);
                    controller = new Controller(game, sv);
                    controller.addObserver(myself);
                    sv.setOnTouchListener(controller);
                    controller.start();
                    sv.postInvalidate();
                }
                dialog.cancel();
            }
        });
        resetAlert.create().show();
    }
}
