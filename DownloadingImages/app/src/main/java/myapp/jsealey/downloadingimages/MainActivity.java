package myapp.jsealey.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    public void downloadImage(View view) {
        Log.i("Button tapped", "Tapped");
        // Create an instance of the ImageDownloader
        ImageDownloader task = new ImageDownloader();
        // Create a new Bitmap object to store the image
        Bitmap myImage;
        try {
            // We pass the url to the ImageDownloader and obtain the data and store it to the variable
            myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();
            // We set the imageView Bitmap to the one we just downloaded.
            imageView.setImageBitmap(myImage);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                // Get the first url
                URL url = new URL(urls[0]);
                // Create a connection to that url
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // Connect using the connection
                connection.connect();
                // Create a stream to receive the data
                InputStream in = connection.getInputStream();
                // This receives the stream of data, decodes it and stores it in a Bitmap object.
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                // Return the Bitmap
                return myBitmap;
            }

            catch (Exception e) {
                e.printStackTrace();
                // If somehtign goes wrong we return nothing
                return null;
            }

        }
    }
}