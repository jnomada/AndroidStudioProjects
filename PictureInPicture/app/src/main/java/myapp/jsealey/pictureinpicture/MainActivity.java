package myapp.jsealey.pictureinpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void goPip(View view) {
        enterPictureInPictureMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        TextView textView = findViewById(R.id.textView);
        Button pipButton = findViewById(R.id.pipButton);

        if (isInPictureInPictureMode) {
            // Going into PIP
            pipButton.setVisibility(View.INVISIBLE); // Hide the button
            getSupportActionBar().hide(); // Hide the bar
            textView.setText("$10,043.04"); // Change textView text

        } else {
            // Going out of PIP
            pipButton.setVisibility(View.VISIBLE); // show the button
            getSupportActionBar().show(); // show the bar
            textView.setText("$10,043.04"); // Change textView text
        }
    }
}