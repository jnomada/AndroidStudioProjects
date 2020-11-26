package myapp.jsealey.multipleactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public void backToMain(View view) {
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // startActivity(intent);
        finish(); // Used to close the activity.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Gets the Intent that got us here
        Intent intent = getIntent();
        // We now state the extra that we want to get and its type, we do this by passing a name to the params. Assign it to a variable.
        String name = intent.getStringExtra("name"); // We also have to include a default in case no info came through
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();

    }
}