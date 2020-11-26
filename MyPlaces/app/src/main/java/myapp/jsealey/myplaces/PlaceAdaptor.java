package myapp.jsealey.myplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdaptor extends ArrayAdapter<Place> {

    Context pContext;
    List<Place> placesList = new ArrayList<Place>();


    public PlaceAdaptor(@NonNull Context context, @LayoutRes ArrayList<Place> list) {
        super(context, 0, list);
        pContext = context;
        placesList = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(pContext).inflate(R.layout.list_item,parent,false);

        Place currentPlace = placesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentPlace.getName());

        return listItem;
    }


}
