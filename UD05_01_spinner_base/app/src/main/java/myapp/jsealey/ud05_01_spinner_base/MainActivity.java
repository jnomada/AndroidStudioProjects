package myapp.jsealey.ud05_01_spinner_base;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> datos;
    private void cargarDatos() {
        datos = new ArrayList<>(Arrays.asList("Valor1", "Valor2", "Valor3"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.elemento_spinnerview,R.id.txvElemSpinner, datos);

        Spinner spinner = findViewById(R.id.spnSpinner);

        spinner.setAdapter(adapter);

    }

    private void xestionarEventos() {
        Spinner spinner = findViewById(R.id.spnSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = view.findViewById(R.id.txvElemSpinner);
                tv.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), datos.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        findViewById(R.id.edtEditElemLista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = findViewById(R.id.spnSpinner);
                if (spinner.getSelectedItem() == null) return;
// Nada seleccionado
                Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString() + " EN LA POSICION " + String.valueOf(spinner.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
                View layout_elem_spinner = spinner.getSelectedView();
// Podemos acceder al view seleccionado
                TextView tv = layout_elem_spinner.findViewById(R.id.txvElemSpinner);
                tv.setTextColor(Color.RED);
            }

        });


        findViewById(R.id.btnBaixa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = findViewById(R.id.spnSpinner);
                if (spinner.getSelectedItemPosition()==AdapterView.INVALID_POSITION) return;
                ArrayAdapter<String> adapter = (ArrayAdapter)spinner.getAdapter();
                datos.remove(spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.btnAlta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edt = findViewById(R.id.edtEditElemLista);
                if(edt.getText().toString().length()==0)return;

                ArrayAdapter adaptador = (ArrayAdapter) ((Spinner)findViewById(R.id.spnSpinner)).getAdapter();
                adaptador.add(edt.getText().toString());
            }
        });

        findViewById(R.id.btnModificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edt = findViewById(R.id.edtEditElemLista);
                Spinner spn = findViewById(R.id.spnSpinner);

                datos.set(spn.getSelectedItemPosition(), edt.getText().toString()); // Sustituimos item seleccionado por el segundo param
                ArrayAdapter adaptador = (ArrayAdapter) ((Spinner)findViewById(R.id.spnSpinner)).getAdapter();
                adaptador.notifyDataSetChanged();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarDatos();
        xestionarEventos();

    }
}