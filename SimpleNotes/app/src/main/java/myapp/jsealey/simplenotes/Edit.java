package myapp.jsealey.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends AppCompatActivity {

    TextView textEntryField;
    String noteContent;
    String notePosition;

    @Override
    public void onBackPressed() {
        try {
            String textToSave = textEntryField.getText().toString();
            if (notePosition == null || noteContent == null) {
                MainActivity.noteArrayList.add(textToSave);
            } else {
                MainActivity.noteArrayList.remove(Integer.parseInt(notePosition));
                MainActivity.noteArrayList.add(Integer.parseInt(notePosition), textToSave);
            }
            MainActivity.arrayAdapter.notifyDataSetChanged();
            MainActivity.saveToSP();
            Toast.makeText(getApplicationContext(), "Note has been saved", Toast.LENGTH_SHORT).show();
            finish();
            MainActivity.checkNotesArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        textEntryField = findViewById(R.id.textEntryField);

        try {

            Intent intent = getIntent();
            noteContent = intent.getStringExtra("noteContent");
            notePosition = intent.getStringExtra("notePosition");

            textEntryField.setText(noteContent);


            Log.i("Content", noteContent);
            Log.i("Position", notePosition);


        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}