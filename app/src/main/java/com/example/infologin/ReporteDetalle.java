package com.example.infologin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ReporteDetalle extends AppCompatActivity {

    ImageView ImagenDetalleReporte;
    TextView NombreImagenDetalle;
    TextView VistaDetalle;
    TextView NombreOperador;
    TextView NombreUltimoViaje;
    TextView NombreDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_detalle);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Detalle del Reporte");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImagenDetalleReporte = findViewById(R.id.ImagenDetalleReporte);
        NombreImagenDetalle = findViewById(R.id.NombreImagenDetalle);
        VistaDetalle = findViewById(R.id.VistaDetalle);
        NombreOperador = findViewById(R.id.NombreOperador);
        NombreUltimoViaje = findViewById(R.id.NombreUltimoViaje);
        NombreDescripcion = findViewById(R.id.NombreDescripcion);


        String imagen = getIntent().getStringExtra("Imagen");
        String unidad = getIntent().getStringExtra("Unidad");
        String fecha = getIntent().getStringExtra("Fecha");
        String operador = getIntent().getStringExtra("Operador");
        String viaje = getIntent().getStringExtra("Viaje");
        String descripcion = getIntent().getStringExtra("Descripcion");

        try {
            Picasso.get().load(imagen).placeholder(R.drawable.detalle_imagen).into(ImagenDetalleReporte);
        }catch(Exception e){
            Picasso.get().load(R.drawable.detalle_imagen).into(ImagenDetalleReporte);
        }

        NombreImagenDetalle.setText(unidad);
        VistaDetalle.setText(fecha);
        NombreOperador.setText(operador);
        NombreUltimoViaje.setText(viaje);
        NombreDescripcion.setText(descripcion);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}