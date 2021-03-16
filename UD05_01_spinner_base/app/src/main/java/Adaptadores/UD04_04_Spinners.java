package Adaptadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import myapp.jsealey.ud05_01_spinner_base.R;

public class UD04_04_Spinners extends AppCompatActivity {

    private void cargarLista() {
        String[] datos = getResources().getStringArray(R.array.array_datos_spinner);
        int[] imagenes = new int[datos.length];
        for(int cont=0;cont<datos.length;cont++) {
            imagenes[cont]=R.drawable.ic_launcher_foreground;
        }

        Spinner lista = findViewById(R.id.spnLista_UD04_04_Spinner);

        Adaptador_UD04_Spinner miAdaptador = new Adaptador_UD04_Spinner(this, imagenes, datos);

        lista.setAdapter(miAdaptador);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_d04_04__spinners);


        cargarLista();

    }
}