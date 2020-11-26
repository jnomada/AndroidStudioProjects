package myapp.jsealey.techfeedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        ArrayList<String> articleIdArray;
        ArrayList<String> articleTitleArray;
        ArrayList<String> articleUrlArray;
        ListView listView;
        ArrayAdapter<String> arrayAdapter;

    ///////////////////////////////////////////////////
    // Grabs JSON data from API
    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
                return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String result = null;

            try {
                JSONArray arr = new JSONArray(s);
                int numberItems = 20;

                if (arr.length() < 20) {
                    numberItems = arr.length();
                }

                for(int i = 0; i < numberItems; i++) {
                    articleIdArray.add(arr.get(i).toString());

                    // Get article details using the article id
                    DownloadTask getArticleDetails = new DownloadTask();
                    result = getArticleDetails.execute("https://hacker-news.firebaseio.com/v0/item/"+articleIdArray.get(i)+".json?print=pretty").get();
                    JSONObject obj = new JSONObject(result);

                    // Add article titles and urls to ArrayLists
                    if (obj != null) {
                        if (obj.has("title") && obj.has("url")) {
                            articleTitleArray.add(obj.getString("title"));
                            articleUrlArray.add(obj.getString("url"));
                        }
                    }
                }

                arrayAdapter.notifyDataSetChanged();

            } // try end

            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleIdArray = new ArrayList<String>();
        articleUrlArray = new ArrayList<String>();
        articleTitleArray = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articleTitleArray);
        listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Reader.class);
                intent.putExtra("url", articleUrlArray.get(position));
                startActivity(intent);
            }
        });


        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();

        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }
}