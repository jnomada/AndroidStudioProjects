package myapp.jsealey.alertdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert) // Adds an alert icon
                .setTitle("Are you sure??") // Adds a title to the alert
                .setMessage("Do you definitely want to do this") // Adds a message
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code that is run if someone clicks on the Yes button
                        Toast.makeText(getApplicationContext(), "YES was clicked", Toast.LENGTH_SHORT).show();
                        Log.i("Button pressed", "YES");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {  // We can also use null for the second param so it doesn't do anything and jsut closes
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code that is run if someone clicks on the NO button
                        Toast.makeText(getApplicationContext(), "NO was clicked", Toast.LENGTH_SHORT).show();
                        Log.i("Button pressed", "NO");
                    }
                }).show();  // Remember to add .show() on the end
    }
}