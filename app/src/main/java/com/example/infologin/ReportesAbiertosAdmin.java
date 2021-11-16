package com.example.infologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.infologin.model.Reporte;
import com.example.infologin.model.ViewHolderReporte;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportesAbiertosAdmin extends AppCompatActivity {

    private List<Reporte> listReporte = new ArrayList<Reporte>();
    ArrayAdapter<Reporte> arrayAdapterReporte;

    ListView listV_reportes;

    RecyclerView recyclerViewReporte;

    FirebaseDatabase firebaseDatabase;
    Query databaseReference;

    FirebaseRecyclerAdapter<Reporte, ViewHolderReporte> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Reporte> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_abiertos_admin);

        recyclerViewReporte = findViewById(R.id.recyclerViewReporte);
        recyclerViewReporte.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Reporte").orderByChild("estatus").equalTo("Abierto");


        ListarImagenesReportes();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ListarImagenesReportes() {
        options = new FirebaseRecyclerOptions.Builder<Reporte>().setQuery(databaseReference,Reporte.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Reporte,ViewHolderReporte>(options){

            @Override
            protected void onBindViewHolder(@NonNull ViewHolderReporte viewHolderReporte,int i,@NonNull Reporte reporte){
                viewHolderReporte.seteoReportes(
                        getApplicationContext(),
                        reporte.getUnidad(),
                        reporte.getFecha(),
                        reporte.getImagen()
                );
            }

            @NonNull
            @Override
            public ViewHolderReporte onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reporte,parent,false);
                ViewHolderReporte viewHolderReporte = new ViewHolderReporte(itemView);

                viewHolderReporte.setOnClickListener(new ViewHolderReporte.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //OBTENER DATOS DE LA IMAGEN
                        String Imagen = getItem(position).getImagen();
                        String Unidad = getItem(position).getUnidad();
                        String Fecha = getItem(position).getFecha();
                        String Operador = getItem(position).getOperador();
                        String UltViaje = getItem(position).getViaje();
                        String Descripcion = getItem(position).getDescripcion();

                        //PASAMOS A LA ACTIVIDAD DE DETALLE
                        Intent intent = new Intent(getApplicationContext(),ReporteDetalle.class);

                        //DATOS A PASAR
                        intent.putExtra("Imagen",Imagen);
                        intent.putExtra("Unidad",Unidad);
                        intent.putExtra("Fecha",Fecha);
                        intent.putExtra("Operador",Operador);
                        intent.putExtra("Viaje",UltViaje);
                        intent.putExtra("Descripcion",Descripcion);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "ITEM LONG CLICK", Toast.LENGTH_SHORT).show();
                    }
                });
                return  viewHolderReporte;
            }
        };

        recyclerViewReporte.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        firebaseRecyclerAdapter.startListening();
        recyclerViewReporte.setAdapter(firebaseRecyclerAdapter);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}