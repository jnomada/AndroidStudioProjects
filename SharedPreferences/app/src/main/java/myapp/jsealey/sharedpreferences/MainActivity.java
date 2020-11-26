package myapp.jsealey.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // First part is looking for the name of our app, the second param is how we want this to be saved. In our case PRIVATE means that it is only for THIS app.
        SharedPreferences sharedPreferences = this.getSharedPreferences("myapp.jsealey.sharedpreferences", Context.MODE_PRIVATE);
        // Add a String value to the SharedPrefernces
        //sharedPreferences.edit().putString("username", "james").apply();
        //sharedPreferences.edit().putString("username1", "bob").apply();
        // Get the information from the SharedPreferences. Set a default value if nothing is found.
        /*String username = sharedPreferences.getString("username", "Not found");
        String username1 = sharedPreferences.getString("username1", "Not found");

        Log.i("Result", username);
        Log.i("Result", username1);*/

        // Saved an array list to sharedPreferences
     /*   ArrayList<String> friends = new ArrayList<String>();
        friends.add("Sarah");friends.add("Bob");friends.add("Laura");

        try {
           // We serialize the friends ArrayList and place it in the sharedPreferences object as a String
            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();

            Log.i("Friends", ObjectSerializer.serialize(friends));
        }

        catch (Exception e) {
            e.printStackTrace();
        }*/

        // Create a new ArrayList where we save the recovered data from the sharedPrefernces
        ArrayList<String> newFriends = new ArrayList<String>();
        // Assign the deserialized data obtained from sharedPreferences to the newFriends ArrayList. We also need to cast the data to the ArrayList data type.
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));
            Log.i("newFriends", newFriends.toString());
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}