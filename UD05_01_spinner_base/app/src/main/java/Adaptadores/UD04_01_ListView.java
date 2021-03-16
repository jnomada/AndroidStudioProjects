package Adaptadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import myapp.jsealey.ud05_01_spinner_base.R;

public class UD04_01_ListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_d04_01__list_view);

        // Localizamos ListView
        ListView lvFrutas = (ListView) findViewById(R.id.lv_frutas);

        // Creamos array con los datos a mostrar
        String[] frutas = new String[] {"Pera", "Manzana", "Plátano"};

        // Creamos el adaptador, escogemos el layout a mostrar e indicamos la fuente de datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, frutas);

        // Definimos el adaptador que se utilizará para el listview
        lvFrutas.setAdapter(adaptador);

        // Creamos el OnItemClickListener
        lvFrutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "Has seleccionado: " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
