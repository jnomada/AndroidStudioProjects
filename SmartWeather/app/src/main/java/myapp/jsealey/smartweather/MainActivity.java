package myapp.jsealey.smartweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String result;
    TextView enterCityText;
    TextView enterCityField;
    Button checkCityButton;

    ConstraintLayout weatherStatusLayout;
    TextView weatherStateText;
    TextView currentTempText;
    TextView currentHumidityText;
    TextView currentWindText;
    ImageView currentWeatherIcon;

    JSONArray currentWeatherArray;
    JSONObject currentTempObject;
    JSONObject currentWindObject;

    boolean panelShowing = false;


    public void checkWeather(View view) {
        // Hides the weather panel
        //hideWeatherPanel();
        // Used to hide the keyboard after the button is pressed
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(enterCityText.getWindowToken(), 0);

        DataDownloader task = new DataDownloader();
        result = "";
        String city = enterCityField.getText().toString();
        try {
            result = task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=9e3da25f7ce5064a81f4172f8aa78740").get();

        }

        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No data found for this location, please enter a new one.", Toast.LENGTH_SHORT).show();
        }
    }

        public void displayInPanel() {

            if (!panelShowing) {
                showWeatherPanel();
                panelShowing = true;
            }
            // Now we can loop through it
            try {
                for(int i = 0; i < currentWeatherArray.length(); i++) {
                    // We now want to get the particular JSON object at each location in the array
                    JSONObject jsonPart = currentWeatherArray.getJSONObject(i);
                    String briefDescription = jsonPart.getString("main");
                    String fullDescription = jsonPart.getString("description");

                    weatherStateText.setText(briefDescription + ": " + fullDescription);
                    switch (briefDescription) {
                        case "Clouds":
                            currentWeatherIcon.setImageResource(R.drawable.cloudy);
                            break;
                        case "Clear":
                            currentWeatherIcon.setImageResource(R.drawable.sunny);
                            break;
                        case "Rain":
                            currentWeatherIcon.setImageResource(R.drawable.light_rain);
                            break;
                        case "Snow":
                            currentWeatherIcon.setImageResource(R.drawable.ice);
                            break;
                        case "Thunderstorm":
                            currentWeatherIcon.setImageResource(R.drawable.thunder_rain);
                            break;
                        case "Mist":
                            currentWeatherIcon.setImageResource(R.drawable.fog);
                            break;
                        default:
                            currentWeatherIcon.setImageResource(R.drawable.warning);
                    }
                }
                // Access the temp object we extracted from the result
                currentTempText.setText(currentTempObject.getString("temp") + " ºC");
                currentHumidityText.setText(currentTempObject.getString("humidity") + " % Humidity");
                double speedToKm = (double) Double.parseDouble(currentWindObject.getString("speed")) * 3.6;
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                currentWindText.setText("Speed: " + numberFormat.format(speedToKm) + " km/h");
        }


            catch (Exception e) {
                e.printStackTrace();
            }


        }




    public void hideWeatherPanel() {
        weatherStatusLayout.animate().alpha(0).translationYBy(1600);
        // weatherStatusLayout.setY(1600f);
    }

    public void showWeatherPanel() {
        weatherStatusLayout.animate().translationYBy(-1600f).setDuration(1000).alpha(1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterCityText = findViewById(R.id.enterCityText);
        enterCityField = findViewById(R.id.enterCityField);
        checkCityButton = findViewById(R.id.checkCityButton);

        weatherStatusLayout = findViewById(R.id.weatherStatusLayout);
        weatherStateText = findViewById(R.id.currentWeatherText);
        currentTempText = findViewById(R.id.currentTempText);
        currentHumidityText = findViewById(R.id.currentHumidityText);
        currentWindText = findViewById(R.id.currentWindText);
        currentWeatherIcon = findViewById(R.id.currentWeatherIcon);

        hideWeatherPanel();


    }



    public class DataDownloader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection connection;

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }
                Log.i("Result", result);
                return result;
            }

            catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // Create the JSON object and pass the string (s) as a parameter. This string is the result of the doInBackground method.
                JSONObject jsonObject = new JSONObject(s);
                // Create a String variable and assign the JSON data from the weather key.
                String weatherInfo = jsonObject.getString("weather");
                String tempInfo = jsonObject.getString("main");
                String windInfo = jsonObject.getString("wind");
                // Print out the weatherInfo contents
                Log.i("Weather Content", weatherInfo);
                Log.i("Temperature", tempInfo);
                Log.i("Wind", windInfo);
                // The weatherInfo variable actually contains an array which we need to convert to a JSONArray
                currentWeatherArray = new JSONArray(weatherInfo);
                currentTempObject = new JSONObject(tempInfo);
                currentWindObject = new JSONObject(windInfo);
                displayInPanel();
            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "No data found for this location, please enter a new one.", Toast.LENGTH_SHORT).show();
            }



        }
    }
}