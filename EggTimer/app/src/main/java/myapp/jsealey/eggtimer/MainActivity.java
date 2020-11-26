package myapp.jsealey.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int minutes = 0 ;
    int seconds = 60;
    int minutesDisplayed = minutes / 600000;
    String secondsDisplayed = "00";

    TextView timeTextView;
    TextView secondsTextView;
    ImageView startButton;
    SeekBar timeSeekBar;
    ImageView restartButton;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start button animation
        startButton = findViewById(R.id.startButton);
        startButton.setY(500f);
        startButton.animate().translationYBy(-500f).setDuration(1000);

        // Restart button
        restartButton = findViewById(R.id.restartButton);
        restartButton.setY(500f);

        //TextView
        timeTextView = findViewById(R.id.timeTextView);
        secondsTextView = findViewById(R.id.secondsTextView);

        //MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.atone);

        //SeekBar
        timeSeekBar = findViewById(R.id.timeSeekBar);
        timeSeekBar.setProgress(3000);
        timeSeekBar.setMax(91);
        timeSeekBar.setMin(1);

        minutesDisplayed = timeSeekBar.getProgress();
        timeTextView.setText(String.valueOf(minutesDisplayed));

        // Minutes seekBar
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                minutes = progress * 60000;
                minutesDisplayed = progress;
                timeTextView.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void restart(View view) {
        mediaPlayer.pause();
        countDownTimer.cancel();
        timeSeekBar.setEnabled(true);
        seconds = 60;
        minutes = timeSeekBar.getProgress() * 60000;
        startButton.animate().translationYBy(-500f).setDuration(1000);
        restartButton.animate().translationYBy(500f).setDuration(1000);
        secondsTextView.setText("00");
        timeTextView.setText(String.valueOf(timeSeekBar.getProgress()));


    }

    public void startCountDown(View view) {
            startButton.animate().translationYBy(500f).setDuration(1000);
            timeSeekBar.setEnabled(false);
            restartButton.animate().translationYBy(-500f).setDuration(1000);
            minutesDisplayed = minutes / 60000;
            minutesDisplayed--;
            countDownTimer = new CountDownTimer(minutes, 1000) {

                public void onTick(long millisecondsUntilDone) {
                    Log.i("Seconds left: ", String.valueOf(millisecondsUntilDone));

                    seconds--;

                    if (seconds == 0) {
                        minutesDisplayed--;
                        seconds = 59;
                    }

                    if (seconds >=1 && seconds <10) {
                        secondsDisplayed = String.valueOf("0" + seconds);
                        secondsTextView.setText(secondsDisplayed);
                        timeTextView.setText(String.valueOf(minutesDisplayed));
                    } else {
                        secondsDisplayed = String.valueOf(seconds);
                        secondsTextView.setText(secondsDisplayed);
                        timeTextView.setText(String.valueOf(minutesDisplayed));
                    }


                }

                public void onFinish() {
                    Log.i("Finished!", "Finished!");
                    secondsTextView.setText("00");
                    timeTextView.setText(String.valueOf("0"));
                    Toast.makeText(getApplicationContext(), "Food is cooked!!", Toast.LENGTH_SHORT).show();
                    mediaPlayer.seekTo(0);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }.start();
    }
}
