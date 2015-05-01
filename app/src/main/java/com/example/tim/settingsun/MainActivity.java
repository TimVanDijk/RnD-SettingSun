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
 *
 * @author Ward Theunisse, Tim van Dijk, Martijn Heitkönig, Luuk van Bitterswijk
 */
public class MainActivity extends Activity implements Observer {

    private Game game;
    private Controller controller;
    private SunView sv;

    private Button resetButton;

    private MainActivity myself = this;

    @Override
    /**
     * Creates a game, a SunView and a controller. Then connects these objects to allow them to interact with one another
     */
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
    /**
     * Handles action bar item clicks. The action bar will automatically handle click on the Home/Up botton,
     * as long as you specify a parent activity in AndroidManifest.xml
     *
     * @param item a single item in a menu
     *
     * @return When the menu item is successfully handled it returns true.
     *         If it isn't it calls the superclass implementation of onOptionsItemSelected() (whose default implementation returns false)
     */
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings ? true : super.onOptionsItemSelected(item));
    }

    @Override
    /**
     * This method is called only when the puzzle is solved. It creates a dialog telling the user that he has won and how many moves were used.
     * @param observable the observable that is notified
     * @param object ???
     */
    public void update (Observable observable, Object o) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VICTORY");
        builder.setMessage("Moves used: " + game.getMoves());
        builder.create().show();
    }

    @Override
    /**
     * This method is called when the back button is pressed.
     * It makes the user's previous move undone.
     */
    public void onBackPressed () {
        game.removePuzzle();
        sv.postInvalidate();
    }

    /**
     * Creates a dialog that asks the user if he really wants to reset the puzzle.
     * @param v the view on which the dialog is to be displayed
     */
    public void createResetDialog (View v) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder (this);
        resetAlert.setTitle("Reset puzzle?");
        final CharSequence[] options = {"Yes","No"};
        resetAlert.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            /**
             * This method is called when the user selects an option from the dialog.
             * If yes is selected, it closes the dialog and starts a new game. Otherwise, it only closes the dialog
             */
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
