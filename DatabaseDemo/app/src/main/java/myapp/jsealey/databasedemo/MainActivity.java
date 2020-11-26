package myapp.jsealey.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Creates a new database or opens an existing one
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null); // We give it a name and choose the access level. PRIVATE is for this app only

            // Creates a new table if it doesn't already exist
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");

            // Insert a new registry in the table
            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('James', 34)");
            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Hayet', 30)");
            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Daniel', 4)");
            // myDatabase.execSQL("DELETE FROM users");

            // Creates a Cursor and adds the results of the query to it
            Cursor c = myDatabase.rawQuery("SELECT * FROM newUsers", null);

            // Gets the index for the 2 columns, we need this to later access the data
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            // We set our cursor to the starting position
            c.moveToFirst();

        /*// Log what the cursor is pointing at
        Log.i("Name", c.getString(nameIndex));
        Log.i("Age", c.getString(ageIndex));

        // Moves to the next line
        c.moveToNext();

        // Logs what is on the next line
        Log.i("Name", c.getString(nameIndex));
        Log.i("Name", c.getString(ageIndex));
*/
            // If we want to display everything in the cursor we can use a while loop
            while (!c.isAfterLast()) {
                Log.i("Name", c.getString(nameIndex));
                Log.i("Age", c.getString(ageIndex));
                Log.i("Id", c.getString(idIndex));
                c.moveToNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}