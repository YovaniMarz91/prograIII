package com.example.tiendacouchdb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class agregar_producto extends Activity {
    String resp, accion, id, rev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_producto);

        try {
            FloatingActionButton btnMostrarProductos = findViewById(R.id.btnMostrarProducto);
            btnMostrarProductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarProductos();
                }
            });
            Button btn=(Button)findViewById(R.id.btnAgregarProducto);
            btn .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView temp=(TextView) findViewById(R.id.txtcodigo);
                    String codigo=temp.getText().toString();

                    temp=(TextView)findViewById(R.id.txtproducto);
                    String producto=temp.getText().toString();

                    temp=(TextView)findViewById(R.id.txtmarca);
                    String marca=temp.getText().toString();

                    temp=(TextView)findViewById(R.id.txtpresentacion);
                    String presentacion=temp.getText().toString();

                    //crear jsonobject aqui
                    JSONObject miData=new JSONObject();

                    try{
                        if(accion.equals("modificar")){
                            miData.put("_id",id);
                            miData.put("_rev",rev);
                            miData.put("codigo",codigo);
                            miData.put("producto",producto);
                            miData.put("marca",marca);
                            miData.put("presenetacion",presentacion);

                            enviarDatosProducto objEnviar=new enviarDatosProducto();
                            objEnviar.execute(miData.toString());


                        }

                    } catch (Exception e) {
                        Toast.makeText(agregar_producto.this,"Error al guardar datos"+e.getMessage().toString(),Toast.LENGTH_LONG).show();

                    }


                }
            });
            mostrarDatosProducto();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al agregar producto: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void mostrarDatosProducto(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")){
                JSONObject dataProducto = new JSONObject(recibirParametros.getString("dataProducto")).getJSONObject("value");

                TextView tempVal = (TextView)findViewById(R.id.txtcodigo);
                tempVal.setText(dataProducto.getString("codigo"));

                tempVal = (TextView)findViewById(R.id.txtproducto);
                tempVal.setText(dataProducto.getString("nombre"));

                tempVal = (TextView)findViewById(R.id.txtmarca);
                tempVal.setText(dataProducto.getString("descripcion"));

                tempVal = (TextView)findViewById(R.id.txtpresentacion);
                tempVal.setText(dataProducto.getString("marca"));

                id = dataProducto.getString("_id");
                rev = dataProducto.getString("_rev");
            }
        }catch (Exception ex){
            ///
        }
    }
    private void mostrarProductos(){
        Intent mostrarProductos = new Intent(agregar_producto.this, MainActivity.class);
        startActivity(mostrarProductos);
    }
    private void guardarProducto(){
        TextView tempVal = findViewById(R.id.txtcodigo);
        String codigo = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtproducto);
        String nombre = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtmarca);
        String descripcion = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtpresentacion);
        String marca = tempVal.getText().toString();

        try {
            JSONObject datosProducto = new JSONObject();
            if (accion.equals("modificar")){
                datosProducto.put("_id",id);
                datosProducto.put("_rev",rev);
            }
            datosProducto.put("codigo", codigo);
            datosProducto.put("nombre", nombre);
            datosProducto.put("descripcion", descripcion);
            datosProducto.put("marca", marca);

            enviarDatosProducto objGuardarProducto = new enviarDatosProducto();
            objGuardarProducto.execute(datosProducto.toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private class enviarDatosProducto extends AsyncTask<String,String, String>{
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            String jsonDatos = parametros[0];
            BufferedReader reader;
            try {
                URL url = new URL("http://192.168.1.12:5984/db_productos/");
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept","application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(jsonDatos);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                if(inputStream==null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                resp = reader.toString();

                String inputLine;
                StringBuffer stringBuffer = new StringBuffer();
                while ((inputLine=reader.readLine())!= null){
                    stringBuffer.append(inputLine+"\n");
                }
                if(stringBuffer.length()==0){
                    return null;
                }
                jsonResponse = stringBuffer.toString();
                return jsonResponse;
            }catch (Exception ex){
                //
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.getBoolean("ok")){
                    Toast.makeText(getApplicationContext(), "Datos de producto guardado con exito", Toast.LENGTH_SHORT).show();
                    mostrarProductos();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al intentar guardar datos de producto", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error al guardar producto: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}




