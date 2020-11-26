package myapp.jsealey.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void buttonClick(View view) {

        EditText userNameField = (EditText) findViewById(R.id.userNameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        String userName = userNameField.getText().toString();
        String password = passwordField.getText().toString();

        Log.i("Username: ", userName);
        Log.i("Password: ", password);

        Toast.makeText(this, "Hello there " +userName+ "!", Toast.LENGTH_SHORT).show();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
