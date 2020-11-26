package myapp.jsealey.showhidedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView showHideText;
    Button showButton;
    Button hideButton;

    public void show(View view) {
        showHideText.setVisibility(View.VISIBLE);
    }

    public void hide(View view) {
        showHideText.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Select UI elements
        showHideText = findViewById(R.id.showHideText);
        showButton = findViewById(R.id.showButton);
        hideButton = findViewById(R.id.hideButton);


    }
}
