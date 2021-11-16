package com.example.infologin.ui.cerrar_sesion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infologin.IniciarSesionActivity;
import com.example.infologin.InicioCompletado;
import com.example.infologin.MainActivity2;
import com.example.infologin.R;
import com.example.infologin.RegistrarseActivity;
import com.example.infologin.ui.cerrar_sesion.CerrarSesionViewModel;
import com.example.infologin.ui.home.HomeFragment;

public class CerrarsesionFragment extends Fragment{

    //private CerrarSesionViewModel cerrarSesionViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);

        Button btnLanzarActivity = (Button) view.findViewById(R.id.btn_cancelar);
        btnLanzarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
