package myapp.jsealey.hikerbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import java.text.Format;
import java.util.List;
import java.util.Locale;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;
import static android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE;

public class MainActivity extends AppCompatActivity {

    // Location tracking
    static LocationManager locationManager;
    static LocationListener locationListener;

    Double lattitude;
    Double longitude;
    Double altitude;
    String address;

    // UI Elements
    TextView lattitudeInfo;
    TextView longitudeInfo;
    TextView altitudeInfo;
    TextView addressInfo;
    Button mapButton;

    ///////////////////////////////////////////
    // Hide navbar and title
    public void fullScreen() {
        // Hides the nav bar
        //View window = getWindow().getDecorView();
        //window.setSystemUiVisibility(SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
    }

    ///////////////////////////////////////////
    // Get location data
    public void getLocationData() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lattitude = lastKnownLocation.getLatitude();
        longitude = lastKnownLocation.getLongitude();
        altitude = lastKnownLocation.getAltitude();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {

                    if (location != null) {
                        Log.i("Location", location.toString());
                        lattitude = location.getLatitude();
                        longitude = location.getLongitude();
                        altitude = location.getAltitude();


                        String lattitudeToString = String.format("%.6f", location.getLatitude());
                        String longitudeToString = String.format("%.6f", location.getLongitude());
                        String altitudeToString = String.format("%.2f", location.getAltitude());

                        lattitudeInfo.setText(lattitudeToString);
                        longitudeInfo.setText(longitudeToString);
                        altitudeInfo.setText(altitudeToString);
                        getAddress();
                        addressInfo.setText(address);

                        Log.i("Attributes", lattitudeToString + longitudeToString + altitudeToString);
                    }
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    ///////////////////////////////////////////
    // Get address
    public void getAddress() {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        address = "";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lattitude, longitude, 1);

            if (listAddresses != null && listAddresses.size() > 0) {

                if (listAddresses.get(0).getThoroughfare() != null) {
                    address += listAddresses.get(0).getThoroughfare() + " \n";
                }

                if (listAddresses.get(0).getLocality() != null) {
                    address += listAddresses.get(0).getLocality() + " \n";
                }

                if (listAddresses.get(0).getAdminArea() != null) {
                    address += listAddresses.get(0).getAdminArea() + " \n";
                }

                if (listAddresses.get(0).getPostalCode() != null) {
                    address += listAddresses.get(0).getPostalCode() + " \n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToMap(View view) {
        Intent mapScreen = new Intent(getApplicationContext(), CurrentLocation.class);

        mapScreen.putExtra("lattitude", lattitude);
        mapScreen.putExtra("longitude", longitude);

        startActivity(mapScreen);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        fullScreen();
        lattitudeInfo = findViewById(R.id.lattitudeInfo);
        longitudeInfo = findViewById(R.id.longitudeInfo);
        altitudeInfo = findViewById(R.id.altitudeInfo);
        addressInfo = findViewById(R.id.addressInfo);
        mapButton = findViewById(R.id.mapButton);
        getLocationData();

    }


}