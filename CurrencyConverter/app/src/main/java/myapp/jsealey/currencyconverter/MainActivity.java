package myapp.jsealey.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void buttonClick(View view) {

        try {
            EditText currencyField = (EditText) findViewById(R.id.currencyTextField);

            String amount = currencyField.getText().toString();

            convert(amount);
        }

        catch (NumberFormatException e) {

            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();

        }

    }

    public void convert(String amount) {

        double amountDouble = Double.parseDouble(amount);
        double rate = 1.12;
        double calculated = amountDouble*rate;
        String conversion = String.format("%.2f", calculated);
        Toast.makeText(this, "£"+amount+" is "+conversion+ " €", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
