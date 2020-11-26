package myapp.jsealey.videodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates the view for the video and then sets the resource to the actual video.
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.demovideo);

        //Creates a new MediaController and anchors it to the videoView we created above.
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        // We now allow it to control our videoView.
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}
