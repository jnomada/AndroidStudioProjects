package myapp.jsealey.interactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void buttonClicked(View view) {

        EditText editNameText = (EditText) findViewById(R.id.nameEditText);
        String textContent = editNameText.getText().toString();

        Log.i("Info", textContent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
