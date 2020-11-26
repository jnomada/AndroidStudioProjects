package myapp.jsealey.myplaces;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlaceMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        final Intent intent = getIntent();

        double lattitude = intent.getDoubleExtra("currentLat", 0);
        double longitude = intent.getDoubleExtra("currentLong", 0);

        double selectedLat = intent.getDoubleExtra("selectedLat", 0);
        double selectedLong = intent.getDoubleExtra("selectedLong", 0);
        String address = intent.getStringExtra("address");

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        try {
            // If no location data comes through we use the last known location
            Location lastKnowLocation = MainActivity.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (intent.hasExtra("currentLat")) {
                /*while(lattitude == 0 || longitude == 0){
                    lattitude = lastKnowLocation.getLatitude();
                    longitude =  lastKnowLocation.getLongitude();
                }*/
                // Add a marker at current location and move the camera
                LatLng currentLocation = new LatLng(lattitude, longitude);
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
            } else {
                // Adds a marker at location selected from list
                LatLng selectedLoc = new LatLng(selectedLat, selectedLong);
                mMap.addMarker(new MarkerOptions().position(selectedLoc).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLoc, 17));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Implements on long click method for map
        mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.i("Long clicked", latLng.toString());

                double markerLat = latLng.latitude;
                double markerLong = latLng.longitude;

                try {
                    // Translates the coordinates to an address
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> listAddresses = geocoder.getFromLocation(markerLat, markerLong, 1);

                    String address = "";

                    // Checks for null values in address
                    if (listAddresses.get(0).getFeatureName() != null) {
                        address += listAddresses.get(0).getFeatureName() + ", ";
                    }

                    if (listAddresses.get(0).getThoroughfare() != null) {
                        address += listAddresses.get(0).getThoroughfare() + ", ";
                    }

                    if (listAddresses.get(0).getLocality() != null) {
                        address += listAddresses.get(0).getLocality() + ", ";
                    }

                    if (listAddresses.get(0).getAdminArea() != null) {
                        address += listAddresses.get(0).getAdminArea() + ", ";
                    }

                    if (listAddresses.get(0).getPostalCode() != null) {
                        address += listAddresses.get(0).getPostalCode();
                    }

                    if (address.equals("")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                        address += sdf.format(new Date());
                    }

                    // Adds a marker for the selected location
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

                    // Adds the new location to the myPlaces ArrayList and notifies the adaptor to update the list
                    MainActivity.myPlaces.add(new Place(address, markerLat, markerLong, address));
                    MainActivity.saveToSharedPreferences();
                    MainActivity.placeAdaptor.clear();
                    MainActivity.loadFromSharedPreferences();
                    MainActivity.placeAdaptor.notifyDataSetChanged();
                    MainActivity.checkMyPlacesArray();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}