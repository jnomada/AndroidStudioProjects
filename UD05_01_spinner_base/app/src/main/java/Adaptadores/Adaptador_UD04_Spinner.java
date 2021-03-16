package Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import myapp.jsealey.ud05_01_spinner_base.R;

public class Adaptador_UD04_Spinner extends ArrayAdapter {

    int[] imagenes;
    Context mContext;
    String[] textos;

    public Adaptador_UD04_Spinner(@NonNull Context context, int[] imagenes, String[] textos) {
        super(context, R.layout.activity_u_d04_05__spinners);

        this.imagenes = imagenes;
        this.textos = textos;
        this.mContext = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return textos.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_u_d04_05__spinners, parent, false);
        }
        ((ImageView)convertView.findViewById(R.id.imgVImaxen_UD04_04_Spinners)).setImageResource(imagenes[position]);
        ((TextView)convertView.findViewById(R.id.txtTexto_UD04_04_Spinners)).setText(textos[position]);


        return convertView;
    }
}
