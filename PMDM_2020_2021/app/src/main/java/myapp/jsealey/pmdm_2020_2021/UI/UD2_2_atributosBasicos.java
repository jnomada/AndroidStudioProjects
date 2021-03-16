package myapp.jsealey.pmdm_2020_2021.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import myapp.jsealey.pmdm_2020_2021.R;

public class UD2_2_atributosBasicos extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_d2_2_atributos_basicos);

        final TextView miPrimerTextview = findViewById(R.id.miPrimerTexto);
        miPrimerTextview.append(getText(R.string.replacement_text));

        miPrimerTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miPrimerTextview.setText("Has clickeado este texto!");
            }
        });
    }
}