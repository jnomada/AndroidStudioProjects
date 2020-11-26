package myapp.jsealey.myplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ListView placeListView; // ListView where we are going to show the list
    static PlaceAdaptor placeAdaptor; // Custom adaptor for custom formatting
    static ArrayList<Place> myPlaces;

    static LocationManager locationManager;
    static LocationListener locationListener;

    double currentLat = 0;
    double currentLong = 0;
    double lastKnownLat;
    double getLastKnownLong;

    static SharedPreferences sharedPreferences;

    static ImageView mapIcon;
    static TextView addNewPlaceText;


    ////////////////////////////////////////////////////////
    // Obtains the current GPS location
    public void getCurrentLocation() {
        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();


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

            // Checks for permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, locationListener);
            }
        }
    }

    ////////////////////////////////////////////////////////
    // Sets up the ListView which displays the locations
    public void listViewSetup() {
        placeListView = findViewById(R.id.placeListView);

        // Create the ArrayList of place objects
        myPlaces = new ArrayList<Place>();

        // Create an instance of custom adaptor
        placeAdaptor = new PlaceAdaptor(this, myPlaces);
        // Set the ListView to use the custom adaptor
        placeListView.setAdapter(placeAdaptor);
    }


    ////////////////////////////////////////////////////////
    // Save and recover data from sharedPreferences
    public static void saveToSharedPreferences() {
        try {
            sharedPreferences.edit().putString("myPlaces", ObjectSerializer.serialize(myPlaces)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFromSharedPreferences() {
        try {
            myPlaces = (ArrayList<Place>) ObjectSerializer.deserialize(sharedPreferences.getString("myPlaces", ObjectSerializer.serialize(new ArrayList<Place>())));

            placeAdaptor.addAll(myPlaces);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////
    // Goes to maps activity and passes current lat and long
    public void addNewPlace(View view) {
        Intent newPlace = new Intent(getApplicationContext(), PlaceMap.class);
        newPlace.putExtra("currentLat", currentLat);
        newPlace.putExtra("currentLong", currentLong);
        startActivity(newPlace);
    }

    ////////////////////////////////////////////////////////
    // Checks if myPlaces ArrayList is populated or not and executes animations depending on the outcome
    public static void checkMyPlacesArray() {
        if (myPlaces == null || myPlaces.size() == 0) {
            showAddNew();
        } else {
            hideAddNew();
        }
    }

    public static void showAddNew() {
        mapIcon.animate().alpha(1).translationYBy(1500).setDuration(1500);
        addNewPlaceText.animate().alpha(1).translationYBy(1500).setDuration(1500);
    }

    public static void hideAddNew() {
        mapIcon.animate().alpha(0).translationYBy(-1500).setDuration(1500);
        addNewPlaceText.animate().alpha(0).translationYBy(-1500).setDuration(1500);
    }

    ////////////////////////////////////////////////////////
    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("myapp.jsealey.myplaces", Context.MODE_PRIVATE);
        // sharedPreferences.edit().clear().apply();

        mapIcon = findViewById(R.id.mapIcon);
        addNewPlaceText = findViewById(R.id.addNewPlaceText);

        mapIcon.setAlpha(0f);
        mapIcon.setY(-1500f);
        addNewPlaceText.setY(-1500f);
        addNewPlaceText.setAlpha(0f);


        // Gets current location
        getCurrentLocation();


        // Sets up the ListView, adaptor and ArrayList
        listViewSetup();
        loadFromSharedPreferences();

        // Checks if the myPlaces array is populated or not. If not then splash image is shown
        checkMyPlacesArray();

        // ListView onClick listeners
        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double selectedLat = myPlaces.get(position).lattitude;
                double selectedLong = myPlaces.get(position).longitude;
                String address = myPlaces.get(position).address;
                Intent savedPlace = new Intent(getApplicationContext(), PlaceMap.class);
                savedPlace.putExtra("selectedLat", selectedLat);
                savedPlace.putExtra("selectedLong", selectedLong);
                savedPlace.putExtra("address", address);
                startActivity(savedPlace);
            }
        });

        placeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myPlaces.remove(position);
                placeAdaptor.remove(placeAdaptor.getItem(position));
                saveToSharedPreferences();
                placeAdaptor.notifyDataSetChanged();
                checkMyPlacesArray();
                return false;
            }
        });
    }
}