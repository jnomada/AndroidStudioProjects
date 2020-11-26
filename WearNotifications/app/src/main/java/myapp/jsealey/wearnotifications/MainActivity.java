package myapp.jsealey.wearnotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        // We first create a notification manager
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel
        NotificationChannel notificationChannel = new NotificationChannel("default", "test", NotificationManager.IMPORTANCE_DEFAULT);

        // Create the channel within the NotificationManager
        manager.createNotificationChannel(notificationChannel);

        // Create the intent to go to when the notification is pressed
        Intent intent = new Intent(this, MainActivity.class);
        // Turn the intent into a pending intent. Allows us to work witht he notification
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build a notification
        Notification.Builder builder = new Notification.Builder(this, "default")
                .setContentTitle("Hello there")
                .setContentText("How are you doing?")
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentIntent(pIntent); // When the user presses on the notification it will trigger the pending Intent and take us to the activity

        // Get access to our Manager
        manager.notify(0, builder.build()); // Id is the request code of the pending intent, then we build the notification using the builder

        // Enables Always-on
        setAmbientEnabled();
    }
}
