package com.example.infologin.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infologin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.security.AccessController.getContext;

public class ReportesA extends AppCompatActivity {

    RecyclerView recyclerViewReporte;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Reporte,ViewHolderReporte> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Reporte> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        recyclerViewReporte = findViewById(R.id.recyclerViewReporte);
        recyclerViewReporte.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Reporte");

        ListarImagenesReportes();
    }

    private void ListarImagenesReportes() {

        options = new FirebaseRecyclerOptions.Builder<Reporte>().setQuery(mRef,Reporte.class).build();
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
                        Toast.makeText(ReportesA.this, "ITEM CLICK", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(ReportesA.this, "ITEM LONG CLICK", Toast.LENGTH_SHORT).show();
                    }
                });
                return  viewHolderReporte;
            }
        };

        recyclerViewReporte.setLayoutManager(new GridLayoutManager(ReportesA.this, 2));
        firebaseRecyclerAdapter.startListening();
        recyclerViewReporte.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        mRef = mFirebaseDatabase.getReference();
    }
}