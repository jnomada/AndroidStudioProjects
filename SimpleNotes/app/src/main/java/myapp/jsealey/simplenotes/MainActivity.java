package myapp.jsealey.simplenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView noteListView;
    static ArrayList<String> noteArrayList;
    static ArrayAdapter arrayAdapter;
    static SharedPreferences sharedPreferences;

    static ImageView mapIcon;
    static TextView addNewPlaceText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addNote:
                Intent editNote = new Intent(getApplicationContext(), Edit.class);
                startActivity(editNote);
                return true;
        }
        return true;
    }

    public static void saveToSP() {
        try {
            sharedPreferences.edit().putString("noteArrayList", ObjectSerializer.serialize(noteArrayList)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////
    // Checks if notes ArrayList is populated or not and executes animations depending on the outcome
    public static void checkNotesArray() {
        if (noteArrayList == null || noteArrayList.size() == 0) {
            showAddNew();
        } else {
            hideAddNew();
        }
    }

    public static void showAddNew() {
        mapIcon.animate().alpha(1).translationYBy(1500).setDuration(1500);
        addNewPlaceText.animate().alpha(1).translationYBy(1500).setDuration(1500);
    }

    public static void hideAddNew() {
        mapIcon.animate().alpha(0).translationYBy(-1500).setDuration(1500);
        addNewPlaceText.animate().alpha(0).translationYBy(-1500).setDuration(1500);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("myapp.jsealey.simplenotes", Context.MODE_PRIVATE);

        mapIcon = findViewById(R.id.mapIcon);
        addNewPlaceText = findViewById(R.id.addNewPlaceText);

        mapIcon.setAlpha(0f);
        mapIcon.setY(-1500f);
        addNewPlaceText.setY(-1500f);
        addNewPlaceText.setAlpha(0f);

        noteArrayList = new ArrayList<String>();
        noteListView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noteArrayList);
        noteListView.setAdapter(arrayAdapter);

        try {
            noteArrayList.addAll((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("noteArrayList", ObjectSerializer.serialize(new ArrayList<String>()))));

            // Checks if the myPlaces array is populated or not. If not then splash image is shown
            checkNotesArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemPressed = noteArrayList.get(position);

                Intent editNote = new Intent(getApplicationContext(), Edit.class);
                editNote.putExtra("noteContent", itemPressed);
                editNote.putExtra("notePosition", String.valueOf(position));
                startActivity(editNote);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete note")
                        .setMessage("Delete this note permanently?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noteArrayList.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                saveToSP();
                                Toast.makeText(getApplicationContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
                                MainActivity.checkNotesArray();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return false;
            }
        });


    }
}