package myapp.jsealey.roundorsquare;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends WearableActivity {

    private Button button;

    public void check(View view) {
        if (getResources().getConfiguration().isScreenRound()) {
            Toast.makeText(this, "The screen is round.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The screen is square.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        // Enables Always-on
        setAmbientEnabled();
    }
}
