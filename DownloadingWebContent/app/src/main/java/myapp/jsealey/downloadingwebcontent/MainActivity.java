package myapp.jsealey.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";

            // Actual URL object that exists in Java. We convert a string to a URL object.
            URL url;

            // Like a browser, it's a way of grabbing the html from a site.
            HttpURLConnection urlConnection = null;

            try {
                // Here we take the first url from the arguments and convert it into a URL object.
                url = new URL(urls[0]);
                // Now we open a connection to the url and save it to the urlConnection variable.
                urlConnection = (HttpURLConnection) url.openConnection();
                // Create an InputStream to gather the data as it comes through
                InputStream in = urlConnection.getInputStream();
                // Create an InputStreamReader that can actually read the data the is coming through.
                InputStreamReader reader = new InputStreamReader(in);
                // Use the reader to read the data. This will be read as an Integer, meaning that the data is read character by character. This get the very first peice of data that comes through.
                int data = reader.read();

                // The next while loop reads the data from teh reader until -1 is sent back meaning that there is no data left to read.
                while(data != -1) {
                    // We turn the data into a character and cast it to a char.
                    char current = (char) data;
                    // We add to the result string character by character whatever comes in off the reader.
                    result += current;
                    // This will move on to the next character.
                    data = reader.read();
                }
                return result;
            }

            catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
             result = task.execute("http://www.zappycode.com").get();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}