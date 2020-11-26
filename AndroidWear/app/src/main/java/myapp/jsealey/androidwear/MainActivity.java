package myapp.jsealey.androidwear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        if (getResources().getConfiguration().isScreenRound()) {
            Log.i("Screen", "Screen is round");
        } else {
            Log.i("Screen", "Screem is square");
        }

        // Enables Always-on
        setAmbientEnabled();
    }
}
