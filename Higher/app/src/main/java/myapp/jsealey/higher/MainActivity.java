package myapp.jsealey.higher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int randomNumber;

    public void generateRandomNumber(){
        Random rand = new Random();
        randomNumber = rand.nextInt(20) + 1;
    }

    public void clearText() {
        EditText numField = (EditText) findViewById(R.id.numField);
        numField.setText("");

    }

    public void buttonClick(View view) {
        try {

            Log.i("Message", "Button working!");
            EditText numField = (EditText) findViewById(R.id.numField);
            int number = Integer.parseInt(numField.getText().toString());

            String message = "";

            if(number == randomNumber) {
                message = "Correct!! You guessed it!";
                generateRandomNumber();
                clearText();
            }
            else if(number < randomNumber) {
                message = "Higher";
                clearText();
            }
            else if(number > randomNumber) {
                message = "Lower";
                clearText();
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateRandomNumber();
    }
}
