package com.example.tim.settingsun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

/**
 * This is the activity that starts when the app is run.
 * It creates a game, controller, view and a reset button and makes sure that they can interact with each other
 * @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
 */
public class MainActivity extends Activity implements Observer {

    private Game game;
    private Controller controller;
    private SunView sv;

    private Button resetButton;

    private MainActivity myself = this;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        sv = (SunView) this.findViewById(R.id.sunview);
        sv.setGame(game);

        controller = new Controller(game, sv);
        controller.addObserver(this);
        sv.setOnTouchListener(controller);

        resetButton = (Button) this.findViewById(R.id.button);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
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
    public void update (Observable observable, Object o) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VICTORY");
        builder.setMessage("Moves used: " + game.getMoves());
        builder.create().show();
    }

    @Override
    public void onBackPressed () {
        game.removePuzzle();
        sv.postInvalidate();
    }

    public void createResetDialog (View v) {
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
                    sv.postInvalidate();
                }
                dialog.cancel();
            }
        });
        resetAlert.create().show();
    }
}
