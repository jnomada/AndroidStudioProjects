package myapp.jsealey.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    TextView readyText;
    GridLayout gridLayout;
    GridLayout answerGrid;
    GridLayout infoGrid;
    CountDownTimer countDownTimer;
    TextView timerDisplay;
    TextView ecuationDisplay;
    TextView scoreDisplay;
    ConstraintLayout youLostScreen;
    ConstraintLayout winningScreen;
    TextView totalScoreText;
    TextView loosingTotalScore;
    Button playAgainButton;

    int timeOnClock = 30000;
    int score = 0;
    int questionNumber = 1;
    int randomNumber;
    int answer;
    int scoreToWin = 5;

    public void go(View view) {
        goPressedAnimations();
    }

    public void onCreateEntryAnimations() {

        // Entry animations
        infoGrid.setY(-300f);
        infoGrid.setAlpha(0);
        answerGrid.setY(500f);
        answerGrid.setAlpha(0);

        playAgainButton.setAlpha(0);
        //playAgainButton.setY(-500f);

        readyText.setY(-1000f);
        readyText.setAlpha(0);
        readyText.animate().translationYBy(1000f).setDuration(1000).alpha(1);
        goButton.setY(1000f);
        goButton.setAlpha(0);
        goButton.animate().translationYBy(-1000f).setDuration(1000).alpha(1);

    }

    public void goPressedAnimations() {

        // Exit animations
        readyText.animate().translationYBy(-1000f).setDuration(1000).alpha(0);
        goButton.animate().translationYBy(1000f).setDuration(1000).alpha(0);

        // Entry animations

        infoGrid.animate().translationYBy(300f).setDuration(1000).alpha(1);
        answerGrid.animate().translationYBy(-500f).setDuration(1000).alpha(1);

        gameCountDown();
        countDownTimer.start();
        equation();

        scoreDisplay.setText(score + " / " + questionNumber);

    }

    public void equation() {
        int num1;
        int num2;
        int randomOperator;
        String operator = "";

        // Generates a random operator 1-4
        randomOperator = (int) Math.floor(Math.random() * 4);

        // Depending on operator parameters change
        switch(randomOperator) {
            case 2:
                operator = "-";
                num1 = (int) Math.floor(Math.random() * 100);
                num2 = (int) Math.floor(Math.random() * 100);
                answer = num1 - num2;
                break;
            case 3:
                operator = "x";
                num1 = (int) Math.floor(Math.random() * 10);
                num2 = (int) Math.floor(Math.random() * 10);
                answer = num1 * num2;
                break;
            case 4:
                operator = "÷";
                num1 = (int) Math.floor(Math.random() * 10);
                num2 = (int) Math.floor(Math.random() * 10);
                answer = num1 / num2;
                break;
            default:
                operator = "+";
                num1 = (int) Math.floor(Math.random() * 100);
                num2 = (int) Math.floor(Math.random() * 100);
                answer = num1 + num2;
        }


        Log.i("Number1", String.valueOf(num1));
        Log.i("Number2", String.valueOf(num2));
        Log.i("Answer", String.valueOf(answer));
        Log.i("Answer", operator);

        ecuationDisplay.setText(num1+ " " +operator+ " " +num2);

        setNumberTiles(answer);

    }

    public void setNumberTiles(int answer) {
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        String color1 = "#E91E63";
        String color2 = "#8BC34A";
        String color3 = "#03A9F4";
        String color4 = "#FF9800";

        randomNumberGenerator();

        switch(randomNumber) {
            case 1:
                button1.setBackgroundColor(Color.parseColor(color1));
                button2.setBackgroundColor(Color.parseColor(color2));
                button3.setBackgroundColor(Color.parseColor(color3));
                button4.setBackgroundColor(Color.parseColor(color4));
                break;
            case 2:
                button1.setBackgroundColor(Color.parseColor(color2));
                button2.setBackgroundColor(Color.parseColor(color3));
                button3.setBackgroundColor(Color.parseColor(color4));
                button4.setBackgroundColor(Color.parseColor(color1));
                break;
            case 3:
                button1.setBackgroundColor(Color.parseColor(color3));
                button2.setBackgroundColor(Color.parseColor(color4));
                button3.setBackgroundColor(Color.parseColor(color1));
                button4.setBackgroundColor(Color.parseColor(color2));
                break;
            case 4:
                button1.setBackgroundColor(Color.parseColor(color4));
                button2.setBackgroundColor(Color.parseColor(color1));
                button3.setBackgroundColor(Color.parseColor(color2));
                button4.setBackgroundColor(Color.parseColor(color3));
                break;
        }

        randomNumberGenerator();

        switch(randomNumber) {
            case 2:
                button1.setText(String.valueOf(wrongAnswerGenerator()));
                button2.setText(String.valueOf(answer));
                button3.setText(String.valueOf(wrongAnswerGenerator()));
                button4.setText(String.valueOf(wrongAnswerGenerator()));
                break;
            case 3:
                button1.setText(String.valueOf(wrongAnswerGenerator()));
                button3.setText(String.valueOf(answer));
                button2.setText(String.valueOf(wrongAnswerGenerator()));
                button4.setText(String.valueOf(wrongAnswerGenerator()));
                break;
            case 4:
                button4.setText(String.valueOf(answer));
                button1.setText(String.valueOf(wrongAnswerGenerator()));
                button3.setText(String.valueOf(wrongAnswerGenerator()));
                button2.setText(String.valueOf(wrongAnswerGenerator()));
                break;
            default:
                button1.setText(String.valueOf(answer));
                button2.setText(String.valueOf(wrongAnswerGenerator()));
                button3.setText(String.valueOf(wrongAnswerGenerator()));
                button4.setText(String.valueOf(wrongAnswerGenerator()));
        }

    }

    public void randomNumberGenerator() {
        randomNumber = (int) Math.floor(Math.random() * 4);
    }

    public int wrongAnswerGenerator() {
        return answer + (int) Math.floor(Math.random() * 15);
    }

    public void answerClicked(View view) {
        Button buttonPressed = (Button) view;
        String buttonTag = buttonPressed.getTag().toString();

        int buttonAnswer = Integer.parseInt(buttonPressed.getText().toString());

        questionNumber++;

        if(buttonAnswer == answer) {
            score++;
        } else {
            score--;
        }

        if(score < 0) scoreDisplay.setTextColor(Color.parseColor("#ed002c"));
        else scoreDisplay.setTextColor(Color.parseColor("#FFFFFF"));

        scoreDisplay.setText(score + " / " + questionNumber);

        equation();
        setNumberTiles(answer);

    }

    public void winningScreen() {
        totalScoreText.setText(String.valueOf(score));
        infoGrid.animate().translationYBy(-300f).setDuration(1000).alpha(0);
        answerGrid.animate().translationYBy(500f).setDuration(1000).alpha(0);
        winningScreen.animate().alpha(1).setDuration(1500);

    }

    public void youLostScreen() {
        loosingTotalScore.setText(String.valueOf(score));
        infoGrid.animate().translationYBy(-300f).setDuration(1000).alpha(0);
        answerGrid.animate().translationYBy(500f).setDuration(1000).alpha(0);
        youLostScreen.animate().alpha(1).setDuration(1500);
    }

    public void playAgain(View view) {
        score = 0;
        timeOnClock = 30000;
        questionNumber = 1;

        winningScreen.setAlpha(0);
        youLostScreen.setAlpha(0);
        playAgainButton.setAlpha(0);

        goPressedAnimations();
        equation();
        setNumberTiles(answer);

    }

    public void gameCountDown() {
        countDownTimer = new CountDownTimer(timeOnClock,1000) {
            public void onTick(long millisecondsUntilDone) {
                Log.i("Seconds passed!", String.valueOf(millisecondsUntilDone / 1000));
                timerDisplay.setText(String.valueOf(millisecondsUntilDone / 1000 + "s"));
            }

            public void onFinish() {
                if(score > scoreToWin) {
                    winningScreen();
                } else {
                    youLostScreen();
                }

                playAgainButton.animate().alpha(1).setDuration(1500);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find UI elements

        winningScreen = findViewById(R.id.winningScreen);
        winningScreen.setAlpha(0);
        youLostScreen = findViewById(R.id.youLostScreen);
        youLostScreen.setAlpha(0);
        gridLayout = findViewById(R.id.infoGrid);
        goButton = findViewById(R.id.goButton);
        readyText = findViewById(R.id.readyText);
        totalScoreText = findViewById(R.id.totalScoreText);
        loosingTotalScore = findViewById(R.id.loosingTotalScore);
        playAgainButton = findViewById(R.id.playAgainButton);
        scoreDisplay = findViewById(R.id.scoreDisplay);

        infoGrid = findViewById(R.id.infoGrid);
        answerGrid = findViewById(R.id.answerGrid);

        ecuationDisplay = findViewById(R.id.ecuationDisplay);

        onCreateEntryAnimations();

        // Timer
        timerDisplay = findViewById(R.id.timerDisplay);




    }


}
