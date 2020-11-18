package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adaptadores.ListViewPersonasAdapter;
import com.example.myapplication.Tipos.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declarar variables
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText ex, ex2, ex3;
    Button btn1, btn2,btn3,btn4;
    private ArrayList<Persona> listaPersonas= new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    ListViewPersonasAdapter listViewPersonasAdapter;
    ListView listViewPersonas;
    Persona personaSeleccionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ex = (EditText)findViewById(R.id.editTextRUT);
        ex2 = (EditText)findViewById(R.id.editTextNOMBRE);
        ex3 = (EditText)findViewById(R.id.editTextAPELLIDO);

        btn1 = (Button)findViewById(R.id.BTNGuardar);
        btn1.setOnClickListener(this);
        btn2 = (Button)findViewById(R.id.BTNModificar);
        btn2.setOnClickListener(this);
        btn3 = (Button)findViewById(R.id.BTNBuscar);
        btn3.setOnClickListener(this);
        btn4 = (Button)findViewById(R.id.BTNEliminar);
        btn4.setOnClickListener(this);

        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);
        //Funcionamiento de la lista
        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSeleccionada = (Persona) parent.getItemAtPosition(position);
                ex.setText(personaSeleccionada.getRut());
                ex2.setText(personaSeleccionada.getNombre());
                ex3.setText(personaSeleccionada.getApellido());
                //Toast.makeText(getApplicationContext(), personaSeleccionada.getIdPersona(), Toast.LENGTH_SHORT).show();

            }
        });
        //Iniciar conexion a BD
        inicializarFirebase();




    }
    public void GuardarPersona(){
        //Crear obj temporal
        Persona P = new Persona();
        P.setIdPersona(UUID.randomUUID().toString());
        P.setRut(ex.getText().toString());
        P.setNombre(ex2.getText().toString());
        P.setApellido(ex3.getText().toString());
        //Almacenar en la BD
        databaseReference.child("Personas").child(P.getIdPersona()).setValue(P);
        //Finalizar guardado
        Toast.makeText(this,"Guardado Correctamente",Toast.LENGTH_SHORT).show();
        LimpiarCampos();
    }
    public void ModificarPersona(){
        //Validar que se selecciono una persona
        if (personaSeleccionada != null){
            //Crear obj temporal
            Persona p = new Persona();
            p.setIdPersona(personaSeleccionada.getIdPersona());
            p.setRut(ex.getText().toString());
            p.setNombre(ex2.getText().toString());
            p.setApellido(ex3.getText().toString());
            //Actualizar persona
            databaseReference.child("Personas").child(p.getIdPersona()).setValue(p);
            Toast.makeText(this, "Actualizado Correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Seleccione una persona", Toast.LENGTH_SHORT).show();
        }
    }

    public void EliminarPersona(){
        //Validar que se selecciono una persona
        if (personaSeleccionada != null){
            //Crear obj temporal
            Persona p = new Persona();
            //Asignar el id del obj que se eliminara
            p.setIdPersona(personaSeleccionada.getIdPersona());
            //Eliminar el obj
            databaseReference.child("Personas").child(p.getIdPersona()).removeValue();
            //Finalizar eliminado
            Toast.makeText(this, "Persona Eliminada", Toast.LENGTH_SHORT).show();
            personaSeleccionada = null;
            LimpiarCampos();
        }else{
            Toast.makeText(this, "Seleccione una persona", Toast.LENGTH_SHORT).show();
        }
    }

    private void LimpiarCampos() {
        ex.setText("");
        ex2.setText("");
        ex3.setText("");
    }

    private void inicializarFirebase (){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BTNGuardar:{
                GuardarPersona();}
                break;
            case R.id.BTNBuscar:{
                listarPersonas();}
                break;
            case R.id.BTNModificar:{
                ModificarPersona();}
                break;
            case R.id.BTNEliminar:{
                EliminarPersona();}
                break;
        }
    }

    private void listarPersonas() {
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPersonas.clear();
                for(DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listaPersonas.add(p);
                }
                arrayAdapterPersona = new ArrayAdapter<Persona>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        listaPersonas
                );
                listViewPersonas.setAdapter(arrayAdapterPersona);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}