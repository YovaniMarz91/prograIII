package com.example.sales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Registraro extends AppCompatActivity {
    Registros miDB;
    String accion = "nuevo";
    String idUsuario = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraro);

        Button btnRegistrar = (Button) findViewById(R.id.btnRegistro);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempVal = (TextView) findViewById(R.id.txtNombre);
                String nombre = tempVal.getText().toString();

                tempVal = (TextView) findViewById(R.id.txtApellido);
                String apellido = tempVal.getText().toString();

                tempVal = (TextView) findViewById(R.id.txtEmail);
                String email = tempVal.getText().toString();

                tempVal = (TextView) findViewById(R.id.txtContraseña);
                String contraseña = tempVal.getText().toString();



                String[] data = {idUsuario, nombre, apellido, email, contraseña};

                miDB = new Registros(getApplicationContext(), "", null, 1);
                miDB.mantenimientoProducto(accion, data);
                Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();


            }
        });



    }
    public void regresar (View view){
        Intent regresar = new Intent(Registraro.this, MainActivity.class);
        startActivity(regresar);

    }
    public void principal(View view){
        Intent principal = new Intent(Registraro.this,MainActivity2.class);
        startActivity(principal);
    }
}