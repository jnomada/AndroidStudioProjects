package myapp.jsealey.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.EventListener;

public class UD03_01_xesteventos_base extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button btn;

    private void gestionarEventosTextView() {

        textView = findViewById(R.id.txvTextoTextView);
        btn = findViewById(R.id.btnBoton);
        textView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                TextView tv = (TextView) view;
                Toast.makeText(getApplicationContext(), "Pulsaste en el texto: " +
                        tv.getText().toString(), Toast.LENGTH_SHORT).show();
                tv.setText("Pulsaste en el TextView");
                tv.setTextColor(Color.BLUE);
                tv.setTextSize(14);
            }
        });

    }


    private void gestionarEventosEditText() {

        EditText editText = findViewById(R.id.etvTextoEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_SEND) {
                    String textoEscrito = textView.getText().toString();

                    Toast.makeText(getApplicationContext(), textoEscrito, Toast.LENGTH_SHORT).show();
                }


                return handled;
            }
        });
    }

    private void gestionarEventosFab() {

        FloatingActionButton fab = findViewById(R.id.fabFloatActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = findViewById(R.id.txvTextoTextView);
                tv.setText("Pulsado el FAB!");
            }
        });

    }

    public void gestionarEventosToggleSwitch() {

        Switch swbSwitchButton = findViewById(R.id.swbSwitchButton);
        swbSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView tv = findViewById(R.id.txvTextoTextView);
                tv.setText("El estado del switch es: " + b);
            }

        });

        ToggleButton tbtnToogleBoton = findViewById(R.id.tbtnToogleBoton);
        tbtnToogleBoton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView tv = findViewById(R.id.txvTextoTextView);
                tv.setText("El estado del toggle es: " + b);
            }

        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_d03_01_xesteventos_base);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);        // Para que no aparezca el teclado virtual
        gestionarEventosTextView();
        gestionarEventosEditText();
        gestionarEventosFab();
        gestionarEventosToggleSwitch();

    btn.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btn.getId()) {
            Toast.makeText(getApplicationContext(), "Botón pulsado " + btn.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}