package myapp.jsealey.timestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView numberListView;
    ArrayList<Integer> numberArrayList;
    ArrayAdapter<Integer> arrayAdapter;
    TextView timesNumberText;

    int startingPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(startingPosition);

        timesNumberText = (TextView) findViewById(R.id.timesNumberText);
        timesNumberText.setText("X " + startingPosition);

        numberListView = findViewById(R.id.numberListView);
        numberArrayList =  new ArrayList<Integer>();

        for(int i = startingPosition; i < 200; i++) {
            numberArrayList.add(i * seekBar.getProgress());
        }

        arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numberArrayList);
        numberListView.setAdapter(arrayAdapter);




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberArrayList.clear();

                if(seekBar.getProgress() < 1) {
                    seekBar.setProgress(1);
                } else {
                    for(int i = startingPosition; i < 200; i++) {
                        numberArrayList.add(i * progress);
                    }

                    timesNumberText.setText("X " + Integer.toString(progress));

                    arrayAdapter.notifyDataSetChanged();

                   Log.i("Value", Integer.toString(progress));

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}
