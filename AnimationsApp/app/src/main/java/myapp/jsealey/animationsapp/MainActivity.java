package myapp.jsealey.animationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


        public void onLoad() {
            ImageView bartImage = findViewById(R.id.bartImageView);
            ImageView homerImage = findViewById(R.id.homerImageView);

            bartImage.setX(-1100);
            bartImage.animate().translationXBy(1100).rotation(1080).setDuration(2000);
        }

    public void fade(View view) {
        Log.i("Message", "Tapped!!");
        ImageView bartImage = findViewById(R.id.bartImageView);
        ImageView homerImage = findViewById(R.id.homerImageView);


        bartImage.animate().translationXBy(1100).rotation(-1080).setDuration(2000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLoad();
    }
}
