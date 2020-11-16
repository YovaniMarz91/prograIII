package com.example.sales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void registrar (View view){
        Intent registrar = new Intent(MainActivity.this,Registraro.class);
        startActivity(registrar);

    }
    public void login(View view){
        Intent Login = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(Login);
    }
}