package com.example.conversores;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TabHost tbhconversores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbhconversores = (TabHost) findViewById(R.id.tbhconversores);
        tbhconversores.setup();

        tbhconversores.addTab(tbhconversores.newTabSpec("M").setContent(R.id.monedas).setIndicator("Moneda"));
        tbhconversores.addTab(tbhconversores.newTabSpec("L").setContent(R.id.longitud).setIndicator("Longitud"));
        tbhconversores.addTab(tbhconversores.newTabSpec("P").setContent(R.id.Peso).setIndicator("peso"));

    }
    public void Calcular(View v) {
        try {
            TextView tempval = (TextView) findViewById(R.id.txtnum20);

            double cant = Double.parseDouble(tempval.getText().toString());
            Spinner spn;
            double valores[][] = {
                    new double[]{1, 1.11, 8.52, 27.39, 37.30, 9.71, 629.46},//Monedas
                    new double[]{1, 10, 0.01, 0.00001, 0.000006213, 0.0109361, 0.3936996},//Longitud
                    new double[]{1, 0.001, 1000, 0.00220462, 0.035274, 0.000001}//Peso
            };
            int de = 0, a = 0;
            double resp = 0;
            switch (tbhconversores.getCurrentTabTag()) {
                case "M":

                    spn = (Spinner) findViewById(R.id.optoperaciones1);

                    de = spn.getSelectedItemPosition();
                    spn = (Spinner) findViewById(R.id.optoperaciones2);
                    a = spn.getSelectedItemPosition();
                    resp = cant * valores[0][a] / valores[0][de];
                    break;

                case "L":
                    spn = (Spinner) findViewById(R.id.optoperaciones10);

                    de = spn.getSelectedItemPosition();
                    spn = (Spinner) findViewById(R.id.optoperaciones20);
                    a = spn.getSelectedItemPosition();
                    resp = cant * valores[1][a] / valores[1][de];
                    break;

                case "P":
                    spn = (Spinner) findViewById(R.id.optOperaciones100);

                    de = spn.getSelectedItemPosition();
                    spn = (Spinner) findViewById(R.id.optOperaciones200);
                    a = spn.getSelectedItemPosition();
                    resp = cant * valores[2][a] / valores[2][de];
                    break;

            }
            tempval = (TextView) findViewById(R.id.lblRespuesta);
            tempval.setText("respuesta: " + resp);


        }catch (Exception ex){
            ex.getMessage();
        }

    }



    }
