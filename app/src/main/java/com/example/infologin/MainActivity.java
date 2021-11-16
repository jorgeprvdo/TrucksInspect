package com.example.infologin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infologin.ui.cerrar_sesion.CerrarsesionFragment;
import com.example.infologin.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irIniciar(View view){
        Intent i = new Intent(this, IniciarSesionActivity.class);
        startActivity(i);
    }

    public void irRegistrar(View view){
        Intent i = new Intent(this, RegistrarseActivity.class);
        startActivity(i);
    }

    public void clicAceptar(View view){
        Intent i = new Intent(this, IniciarSesionActivity.class);
        startActivity(i);
    }

    public void clicCancelar(View view){
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    }

}