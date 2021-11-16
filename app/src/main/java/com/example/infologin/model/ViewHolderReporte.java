package com.example.infologin.model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.infologin.R;
import com.squareup.picasso.Picasso;


public class ViewHolderReporte extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderReporte.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position); //Presiona normal el item
        void onItemLongClick(View view, int position); // Mantiene presionado el item
    }

    public void setOnClickListener(ViewHolderReporte.ClickListener clicklistener){
        mClickListener = clicklistener;
    }

    public ViewHolderReporte(@NonNull View itemView){
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    public void seteoReportes(Context context, String unidad, String fecha, String imagen ){
        ImageView ImagenReporte;
        TextView NombreUnidad;
        TextView FechaReporte;

        //Conexion con el item
        ImagenReporte = mView.findViewById(R.id.ImagenReporte);
        NombreUnidad = mView.findViewById(R.id.Unidad);
        FechaReporte = mView.findViewById(R.id.FechaReporte);


        NombreUnidad.setText(unidad);
        FechaReporte.setText(fecha);

        //Controlar posibles errores
        try{
            Picasso.get().load(imagen).into(ImagenReporte);
        }catch(Exception e){
            Toast.makeText(context, "" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}
