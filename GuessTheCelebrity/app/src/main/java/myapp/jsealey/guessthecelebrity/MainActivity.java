package myapp.jsealey.guessthecelebrity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //////////////////////////////////////////////
    // Attributes
    ImageView imageView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView resultMessage;

    ArrayList<Celebrity> celebrities = new ArrayList<Celebrity>();

    String chosenName;
    String chosenUrl;


    //////////////////////////////////////////////
    // Obtains a random celebrity from the data
    public void randomCeleb() {
        // Creates a random Integer
        Random random = new Random();
        int a = random.nextInt(celebrities.size());

        chosenName = celebrities.get(a).name;
        chosenUrl = celebrities.get(a).urlImage;


    }

    //////////////////////////////////////////////
    // Wrong random Celebrity generator
    public String wrongsCelebs() {
        // Creates a random Integer
        Random random = new Random();
        int b = random.nextInt(celebrities.size());
        String wrongName = celebrities.get(b).name;


        return wrongName;

    }

    //////////////////////////////////////////////
    // Populates ImageView and 1 button with random celebrity and other buttons with other random celebrities
    public void populateElements() {


        // ImageView population
        Bitmap celebImage;
        ImageDownloader getImage = new ImageDownloader();

        try {
            celebImage = getImage.execute(chosenUrl).get();
            imageView.setImageBitmap(celebImage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Button population
        Random random = new Random();
        int randomButton = random.nextInt(4);

        Log.i("Chosen name", chosenName);

        switch(randomButton) {
            case 1:
                button3.setText(chosenName);
                button2.setText(wrongsCelebs());
                button4.setText(wrongsCelebs());
                button1.setText(wrongsCelebs());
                break;
            case 2:
                button1.setText(chosenName);
                button2.setText(wrongsCelebs());
                button3.setText(wrongsCelebs());
                button4.setText(wrongsCelebs());
                break;
            case 3:
                button4.setText(chosenName);
                button3.setText(wrongsCelebs());
                button2.setText(wrongsCelebs());
                button1.setText(wrongsCelebs());
                break;
            default:
                button2.setText(chosenName);
                button3.setText(wrongsCelebs());
                button4.setText(wrongsCelebs());
                button1.setText(wrongsCelebs());
        }


    }

    //////////////////////////////////////////////
    // Loops through the Celebrities ArrayList
    public void showCelebs() {
        for(Celebrity i: celebrities) {
            Log.i("Name", i.name);
            Log.i("urlImage", i.urlImage);
        }
    }

    //////////////////////////////////////////////
    // Gets the HTML data
    public void getData() {
        DownloadHtmlData getData = new DownloadHtmlData();
        String result = null;

        try {
            // Obtain html data
            result = getData.execute("https://www.listchallenges.com/100-famous-people").get();
            String name = null;
            String url = null;


            //Pattern and matcher for name
            Pattern p1 = Pattern.compile("jpg\" alt=\"(.*?)\"");
            Matcher m1 = p1.matcher(result);

            // Pattern and matcher for image url
            Pattern p2 = Pattern.compile("<img onerror=\"ImageLoadError[(]this[)]\" src=\"(.*?)\"");
            Matcher m2 = p2.matcher(result);


            while(m1.find() && m2.find()) {
               name = m1.group(1);
               url = "https://www.listchallenges.com" + m2.group(1);

               celebrities.add(new Celebrity(name, url));
            }
            showCelebs();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //////////////////////////////////////////////
    // Action on button pressing
    public void buttonSelection(View view) {
        //String buttonPressed = view.getTag().toString();
        Button buttonPressed = (Button) view;
        String buttonText = buttonPressed.getText().toString();


        if(buttonText == chosenName) {
            Log.i("Answer", "Correct!");
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            randomCeleb();
            populateElements();

        } else {
            Log.i("Answer", "Wrong!!");
            Toast.makeText(this, "Wrong! The answer was " + chosenName, Toast.LENGTH_SHORT).show();
            randomCeleb();
            populateElements();
        }



    }

    //////////////////////////////////////////////
    // onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        resultMessage = findViewById(R.id.resultMessage);
        resultMessage.setAlpha(0);
        resultMessage.setY(500f);


        getData();
        randomCeleb();
        populateElements();
    }

    /////////////////////////////////
    // Class that allows downloading data in the background
    public class DownloadHtmlData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = null;
            URL url;
            HttpURLConnection connection;

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1) {
                    char current = (char) data;
                    result = result + current;
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

    public class Celebrity {
        /////////////////////////////////
        // Atributes
        String name;
        String urlImage;

        /////////////////////////////////
        // Constructor

        public Celebrity(String name, String urlImage) {
            this.name = name;
            this.urlImage = urlImage;
        }


    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        Bitmap myBitmap;
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return myBitmap;
        }
    }
}

