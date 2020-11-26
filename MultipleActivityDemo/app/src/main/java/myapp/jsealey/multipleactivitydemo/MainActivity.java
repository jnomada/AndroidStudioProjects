package myapp.jsealey.multipleactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    public void goToNext(View view) {
        // This creates the task which describes what we want to do.
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class); // The second parameter is the class that we want to move to (activity)
        // Passes information on to the next activity.
        intent.putExtra("age", 34);
        // This actually launches that task.
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        final ArrayList<String> myFriends = new ArrayList<String>();
        myFriends.add("Bob");myFriends.add("Sally");myFriends.add("Michelle");myFriends.add("John");myFriends.add("Sandra");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myFriends);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemPressed = myFriends.get(position).toString();

                Intent secondActivity;
                switch (itemPressed) {
                    case "Bob":
                        Intent bob = new Intent(getApplicationContext(), Bob.class);
                        bob.putExtra("name", myFriends.get(position));
                        startActivity(bob);
                        break;
                    case "Sally":
                        Intent sally = new Intent(getApplicationContext(), Sally.class);
                        sally.putExtra("name", myFriends.get(position));
                        startActivity(sally);
                        break;
                    case "Michelle":
                        Log.i("Name", "Michelle");
                        Intent michelle = new Intent(getApplicationContext(), Michelle.class);
                        michelle.putExtra("name", myFriends.get(position));
                        startActivity(michelle);
                        break;
                    case "John":
                        secondActivity = new Intent(getApplicationContext(), SecondActivity.class);
                        secondActivity.putExtra("name", myFriends.get(position));
                        startActivity(secondActivity);
                        break;
                    case "Sandra":
                        secondActivity = new Intent(getApplicationContext(), SecondActivity.class);
                        secondActivity.putExtra("name", myFriends.get(position));
                        startActivity(secondActivity);
                        break;
                }

            }
        });

    }
}