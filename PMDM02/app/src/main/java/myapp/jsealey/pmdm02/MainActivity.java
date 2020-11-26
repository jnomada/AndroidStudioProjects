package myapp.jsealey.pmdm02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ////////////////////////////////////////////////////
    // Atributos
    TextView tvPosNum;
    TextView tvResultado; // Estos dos variables son para guardar las vistas TextView cuando las 'localicemos' en la UI
    int posNum; // guardará el número que introduce el usuario

    ////////////////////////////////////////////////////
    // Método que se lanza al iniciar la aplicación
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ////////////////////////////////////////////////////
    // Método que se lanza al hacer onClick en el botón de calcular
    public void readPosNum(View view) {
        // Localizamos los dos TextView y asignamos sus valor a sus variables
        tvPosNum = findViewById(R.id.tvPosNum);
        tvResultado = findViewById(R.id.tvResultado);

        // Obtenemos el texto (número) que introduce el usuario y lo convertimos a String
        String posNumText = tvPosNum.getText().toString();

        // Validamos el campo posición asegurando que lo introducido sea menos o igual a 6 digitos y que el número sea mayor a 0.
        // También he limitado a 6 el número de digitos a través del atributo MaxLength en Android Studio
        if(posNumText.length() > 0 && posNumText.length() <= 6 && Integer.parseInt(posNumText ) > 0) {

            // Convirtimos el String en int
            posNum = Integer.parseInt(posNumText);

            // Un mensajito en forma de Toast para indicar que la app está trabajando
            Toast.makeText(this, "Calculando... por favor espere", Toast.LENGTH_SHORT).show();


            try {

                // Creamos un nuevo hilo donde correrán los cálculos de la clase Calculos
                Calculos calculos = new Calculos(posNum);
                calculos.start(); // Lo iniciamos
                calculos.join(); // El proceso padre esperará a que el hilo finalice

                // Cuando tengamos el resultado cambiamos el texto en la UI para que lo muestre.
                tvResultado.setText("El primo numero " +posNumText+  " es " +calculos.numeroPrimo);

                // Capturamos y manejamos posibles errores
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            // Forma parte de la validación de la entrada del número del usuario. Avisa de las condiciones que tiene que cumplir.
            Toast.makeText(this, "El número puede tener un máximo de 6 digitos y tiene que ser mayor que 0", Toast.LENGTH_SHORT).show();
        }

    }
}