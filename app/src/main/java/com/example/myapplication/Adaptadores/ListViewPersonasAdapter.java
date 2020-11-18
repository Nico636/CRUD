package com.example.myapplication.Adaptadores;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Tipos.Persona;

import java.util.ArrayList;

public class ListViewPersonasAdapter extends BaseAdapter {
    Context context;
    ArrayList<Persona> personaData;
    LayoutInflater layoutInflater;
    Persona ModeloPersona;

    public ListViewPersonasAdapter(Context context, ArrayList<Persona> personaData) {
        this.context = context;
        this.personaData = personaData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return personaData.size();
    }

    @Override
    public Object getItem(int position) {
        return personaData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_personas,
                    null,
                    true);
        }
        TextView rut = rowView.findViewById(R.id.rut);
        TextView nombre = rowView.findViewById(R.id.nombre);
        TextView apellido = rowView.findViewById(R.id.apellido);

        ModeloPersona = personaData.get(position);
        rut.setText(ModeloPersona.getRut());
        nombre.setText(ModeloPersona.getNombre());
        apellido.setText(ModeloPersona.getApellido());

        return rowView;
    }
}
