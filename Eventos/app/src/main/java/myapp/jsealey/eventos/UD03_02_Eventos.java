package myapp.jsealey.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UD03_02_Eventos extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_d03_02__eventos);

        Button btn = findViewById(R.id.btnAceptar_UD03_02);
        btn.setOnClickListener(new GestionaEventos());
    }


   public class GestionaEventos implements View.OnClickListener {


       @Override
       public void onClick(View view) {
           Toast.makeText(view.getContext(), ((Button) view).getText(), Toast.LENGTH_SHORT).show();
       }
   }
}