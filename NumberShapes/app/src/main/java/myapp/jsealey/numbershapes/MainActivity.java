package myapp.jsealey.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void getResult(View view) {

        boolean isTriangular = false;
        boolean isSquare = false;

        try {
            EditText editText = (EditText) findViewById(R.id.editText);

            Number chosenNumber = new Number();

            chosenNumber.num = Integer.parseInt(editText.getText().toString());

            isTriangular = chosenNumber.triangularCheck();
            isSquare = chosenNumber.squareCheck();

            if(isTriangular) Toast.makeText(this, "It is a TRIANGULAR number", Toast.LENGTH_SHORT).show();
            else if(isSquare) Toast.makeText(this, "It is a SQUARE number", Toast.LENGTH_SHORT).show();
            else if(isSquare && isTriangular) Toast.makeText(this, "It is both a TRIANGULAR and SQUARE number", Toast.LENGTH_SHORT).show();
            else if(!(isSquare && isTriangular)) Toast.makeText(this, "It is neither a TRIANGULAR or SQUARE number", Toast.LENGTH_SHORT).show();

        }

        catch (NumberFormatException e) {
            Log.i("","Wrong number format.");
        }


    }

    class Number {

        int num;

        public boolean triangularCheck(){

            int x = 1;

            int triangular = 1;
            boolean result = false;

            while(x <= num) {

                System.out.println(triangular);

                x++;

                triangular = triangular + x;

                if(num == triangular) {
                    result = true;
                }

            }
                return result;


        }

        public boolean squareCheck(){
            boolean result = false;
            try {

                double squareRoot = Math.sqrt(num);
                    if (squareRoot == Math.floor(squareRoot)) result = true;
            }

            catch (ArithmeticException e) {
                   Log.i("", "Cannot divide by 0");
            }
            return result;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}




// Traingular number and Square Numbers
// User puts in a number and the app will tell them whether it's a triangular number, square number, both or neither.