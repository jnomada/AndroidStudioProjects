package myapp.jsealey.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List out friends and show a little toast when one is tapped on.

        ListView myListView = findViewById(R.id.myListView);

        /*ArrayList<String> myFriends = new ArrayList<String>();

        myFriends.add("Bob");
        myFriends.add("Sally");
        myFriends.add("Michael");
        myFriends.add("Nick");*/

        final ArrayList<String> myFriends = new ArrayList<String>(asList("Bob", "Sally", "Michael", "Nick"));

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFriends);

        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, myFriends.get(position) + " was tapped on", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
