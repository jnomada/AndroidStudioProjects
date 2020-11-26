package myapp.jsealey.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0: red, 1: yellow, 2: empty
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;


    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        Button playAgainBtn = (Button) findViewById(R.id.playAgainButton);
        playAgainBtn.setVisibility(View.INVISIBLE);

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.shield4);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.shield3);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(360).setDuration(1000);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    //Someone has one!!
                    String winner = "";
                    gameActive = false;
                    if (activePlayer == 1) {
                        winner = "Red";
                        playAgainBtn.setVisibility(1);
                    } else {
                        winner = "Yellow";
                        playAgainBtn.setVisibility(1);

                    }

                    TextView winnerTextView = (TextView) findViewById(R.id.winningText);
                    winnerTextView.setText(winner + " has won!!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, winner + " has won!!", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    public void playAgain(View view) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        activePlayer = 0;
        gameActive = true;

        TextView winnerTextView = (TextView) findViewById(R.id.winningText);
        winnerTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
