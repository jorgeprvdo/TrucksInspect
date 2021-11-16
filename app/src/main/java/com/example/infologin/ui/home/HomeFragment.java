package com.example.infologin.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infologin.AddReporteActivity;
import com.example.infologin.InicioCompletado;
import com.example.infologin.InterfacesC.IFragments;
import com.example.infologin.MainActivity;
import com.example.infologin.MainActivity2;
import com.example.infologin.R;
import com.example.infologin.model.ReportesA;
import com.example.infologin.ui.home.HomeFragment;

public class HomeFragment extends Fragment implements IFragments {

    View vista;
    Activity actividad;
    CardView cardReporte;
    CardView cardAyuda;
    IFragments interfaceComunicaFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_home, container, false);

        cardReporte=vista.findViewById(R.id.cardReporte);
        cardAyuda = vista.findViewById(R.id.cardAyuda);

        cardAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InicioCompletado.class);
                startActivity(i);
            }
        });

        cardReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AddReporteActivity.class);
                startActivity(in);
            }
        });

        return vista;
    }


    @Override
    public void CrearReporte() {
        Toast.makeText(getActivity(), "Crear Reporte", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AyudaScreen() {
        Toast.makeText(getActivity(), "Pantalla de Ayuda", Toast.LENGTH_SHORT).show();
    }
}