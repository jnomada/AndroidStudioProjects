package myapp.jsealey.photoimportdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class UserFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        // Grab the layout
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        // Create a new ImageView
        ImageView imageView = new ImageView(getApplicationContext());

        // Set up sizing for the new ImageView
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Add an image to the ImageView
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.));
    }
}