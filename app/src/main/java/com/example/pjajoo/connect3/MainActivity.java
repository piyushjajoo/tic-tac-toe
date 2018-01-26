package com.example.pjajoo.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0 = green and 1 = red
    private int activatePlayer = 0;

    // 2 means unplayed
    private int[] gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};

    // winning positions
    private final int[][] winninPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,4,8}, {2,4,6}, {2,5,8}, {1,4,7}, {0,3,6}};

    public void dropIn(final View view) {
        final ImageView counter = (ImageView) view;

        final int tappedCounter = Integer.parseInt(view.getTag().toString());

        if (gameState[tappedCounter] == 2) {

            gameState[tappedCounter] = this.activatePlayer;

            counter.setTranslationY(-1000f);

            if (this.activatePlayer == 0) {
                counter.setImageResource(R.drawable.green);
                this.activatePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                this.activatePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(200f).setDuration(100);

            for (int[] winningPosition: winninPositions) {
                if (
                        gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2
                    ) {

                    // some one won
                    final TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    if (gameState[winningPosition[0]] == 0) {
                        winnerMessage.setText("Green has won!");
                    } else {
                        winnerMessage.setText("Red has won!");
                    }
                    final LinearLayout myLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    myLayout.setVisibility(View.VISIBLE);
                }
            }

        } else {
            Toast.makeText(MainActivity.this, "Player " + gameState[tappedCounter] + " has already played this.", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAgain(final View view) {
        // make linear layout invisible
        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        myLayout.setVisibility(View.INVISIBLE);

        // reset the active player
        this.activatePlayer = 0;

        // reset the gamestate
        for (int i=0; i<gameState.length; i++) {
            gameState[i] = 2;
        }

        final GridLayout myGrid = (GridLayout) findViewById(R.id.myGrid);

        for (int i=0; i < myGrid.getChildCount(); i++) {
            ((ImageView) myGrid.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
