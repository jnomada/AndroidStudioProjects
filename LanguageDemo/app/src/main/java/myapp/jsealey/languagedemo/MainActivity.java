package myapp.jsealey.languagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String chosenLang = "";
    TextView welcomeMessage;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.languaje_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.english:
                chosenLang = "english";
                savePreferences();
                loadLanguageContent();
                return true;
            case R.id.spanish:
                chosenLang = "spanish";
                savePreferences();
                loadLanguageContent();
                return true;
            default:
                return false;
        }
    }

    public void savePreferences() {
        sharedPreferences.edit().putString("chosenLang", chosenLang).apply();
    }

    public void loadPreferences() {
        try {
            chosenLang = sharedPreferences.getString("chosenLang", "not found");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadLanguageContent() {
        if (chosenLang.equals("english")) {
            Toast.makeText(getApplicationContext(), "English is selected", Toast.LENGTH_SHORT).show();
            welcomeMessage.setText("Welcome");

        } else if (chosenLang.equals("spanish")) {
            Toast.makeText(getApplicationContext(), "Spanish is selected", Toast.LENGTH_SHORT).show();
            welcomeMessage.setText("Bienvenido");
        } else {
            // Alert dialog
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Language selection")
                    .setMessage("Please choose a language")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chosenLang = "english";
                            savePreferences();
                            loadLanguageContent();
                        }
                    })
                    .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chosenLang = "spanish";
                            savePreferences();
                            loadLanguageContent();
                        }
                    }).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("myapp.jsealey.languagedemo", Context.MODE_PRIVATE);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        loadPreferences();


        loadLanguageContent();
    }
}