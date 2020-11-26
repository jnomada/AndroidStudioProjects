package myapp.jsealey.myapplication;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends WearableActivity implements DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{  // Implement the methods

    private TextView mTextView;

    // We need a google Api client, this is what allows us to communicate between the 2 devices
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Text that we want to send to the phone
        String friend = "Bob";

        // We send this across to the phone part of the app using a PutDataMapRequest
        // We create a directory when we can store this information. We can have as many directories as
        // we want for text, images, etc
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/data");
        // We now add a string to this dataMap
        putDataMapRequest.getDataMap().putString("friend", friend); // Give it a name and a value
        // The following is what is requested by the Android documentation, not sure why...
        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest(); // Converts from DataMapRequest to DataRequest
        // We now want this information to come forward as a PendingResult
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataRequest); // Pass in the Google Api client and putDataRequest



        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }
}
