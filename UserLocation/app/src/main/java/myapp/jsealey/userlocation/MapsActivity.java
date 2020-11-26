package myapp.jsealey.userlocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LocationManager locationManager;
    LocationListener locationListener;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    double latitude;
    double longitude;

    public void userLocation() {
        // Get location
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    if (latitude != 0 && longitude != 0) {
                        // Cleares the map to remove any existing markers.
                        mMap.clear();
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        LatLng userLocation = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(userLocation).title("You are here"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));


                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());  // The local is used to get back the address information. Default locale will detect whcih country the phone is in and get the addresses for that country.
                        try {
                            // We tell the geocoder to get the info for the loat and long with a maximum results of just 1.
                            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);

                            if (listAddresses != null && listAddresses.size() > 0) {
                                Log.i("PlaceInfo", listAddresses.get(0).toString());
                                String address = "";

                                if(listAddresses.get(0).getThoroughfare() != null) {
                                    address += listAddresses.get(0).getThoroughfare() + " ";
                                }

                                if(listAddresses.get(0).getLocality() != null) {
                                    address += listAddresses.get(0).getLocality() + " ";
                                }

                                if(listAddresses.get(0).getAdminArea() != null) {
                                    address += listAddresses.get(0).getAdminArea() + " ";
                                }

                                if(listAddresses.get(0).getPostalCode() != null) {
                                    address += listAddresses.get(0).getPostalCode() + " ";
                                }

                                Toast.makeText(MapsActivity.this, address, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000,0,locationListener);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        userLocation();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add marker to location

    }
}