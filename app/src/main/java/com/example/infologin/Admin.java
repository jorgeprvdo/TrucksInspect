package com.example.infologin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infologin.InterfaceRep.ReportsFragments;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin extends AppCompatActivity implements ReportsFragments {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    View vista;
    Activity actividad;
    CardView cardAbierto;
    CardView cardProgreso;
    CardView cardCerrado;
    CardView cardCerrarAdmin;
    ReportsFragments interfaceReportesFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        cardAbierto = findViewById(R.id.cardAbierto);
        cardProgreso = findViewById(R.id.cardProgreso);
        cardCerrado = findViewById(R.id.cardCerrado);
        cardCerrarAdmin = findViewById(R.id.cardLogout);

        cardAbierto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), ReportesAbiertosAdmin.class);
                startActivity(a);
            }
        });

        cardProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(getApplicationContext(), ReportesProgresoAdmin.class);
                startActivity(b);
            }
        });

        cardCerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getApplicationContext(), ReportesCerradosAdmin.class);
                startActivity(c);
            }
        });

        cardCerrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(getApplicationContext(), LogoutAdmin.class);
                startActivity(d);
            }
        });

        updateTextView();
    }

    public void updateTextView () {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.adminview);
        TextView TvMail = linearLayout.findViewById(R.id.nav_mail);

        TvMail.setText(currentUser.getEmail());
    }

    @Override
    public void RepAbierto() {
        Toast.makeText(getApplicationContext(), "Reportes abiertos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RepCerrado() {
        Toast.makeText(getApplicationContext(), "Reportes cerrados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RepEnProgreso() {
        Toast.makeText(getApplicationContext(), "Reportes en progreso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void BtnCerrarAdmin() {
        Toast.makeText(getApplicationContext(), "Cerrar Sesión", Toast.LENGTH_SHORT).show();
    }
}