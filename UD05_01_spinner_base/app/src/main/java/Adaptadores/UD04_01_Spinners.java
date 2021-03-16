package Adaptadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import myapp.jsealey.ud05_01_spinner_base.R;

public class UD04_01_Spinners extends AppCompatActivity {

    private void cargarLista() {
        Spinner lista = findViewById(R.id.spnLista_UD02_02_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(getResources().getStringArray(R.array.array_datos_spinner));
        lista.setAdapter(adapter);


        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String texto = ((TextView)view).getText().toString();
                Toast.makeText(getApplicationContext(), "Elemento seleccionado: " + texto + " en la posición " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_d04_01__spinners);

        cargarLista();
    }
}