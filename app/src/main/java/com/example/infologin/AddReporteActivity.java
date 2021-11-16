package com.example.infologin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infologin.model.Persona;
import com.example.infologin.model.Reporte;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddReporteActivity extends AppCompatActivity {

    private List<Reporte> listReporte = new ArrayList<Reporte>();
    ArrayAdapter<Reporte> arrayAdapterReporte;

    EditText unidadP, operadorP,ultViajeP,fechaP,descripcionP ;

    TextView FechaReporte;
    EditText NombreReporte;
    ImageView ImagenAgregarReporte;
    Button PublicarReporte;

    String RutaDeAlmacenamiento = "Reporte_Subido/";
    String RutaDeBaseDeDatos = "Reportes";
    Uri RutaArchivoUri;

    StorageReference mStorageReference;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    int CODIGO_DE_SOLICITUD_IMAGEN = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reporte);

        FechaReporte = findViewById(R.id.FechaReporte);
        ImagenAgregarReporte = findViewById(R.id.ImagenAgregarReporte);
        PublicarReporte = findViewById(R.id.cardReporte);

        unidadP = findViewById(R.id.txt_nombreUnidad);
        operadorP = findViewById(R.id.txt_operador);
        ultViajeP = findViewById(R.id.txt_ultimoViaje);
        fechaP = findViewById(R.id.txt_fecha);
        descripcionP = findViewById(R.id.txt_descripcion);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(RutaDeBaseDeDatos);
        inicializarFirebase();

        progressDialog = new ProgressDialog(AddReporteActivity.this);

        //SI SE PRESIONA LA IMAGEN PARA AGREGAR UNA FOTO
        ImagenAgregarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seleccionar imagen"),CODIGO_DE_SOLICITUD_IMAGEN);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String ObtenerExtensionDelArchivo(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODIGO_DE_SOLICITUD_IMAGEN
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){

            RutaArchivoUri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),RutaArchivoUri);
                ImagenAgregarReporte.setImageBitmap(bitmap);
            }catch(Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void SubirImagen(){
        if(RutaArchivoUri != null){
            progressDialog.setTitle("Espere por favor..");
            progressDialog.setMessage("Subiendo imagen del reporte...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference storageReference2 = mStorageReference.child(RutaDeAlmacenamiento + System.currentTimeMillis() + "." + ObtenerExtensionDelArchivo(RutaArchivoUri));
            storageReference2.putFile(RutaArchivoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());

                            Uri downloadUri = uriTask.getResult();

                            String unidad = unidadP.getText().toString();
                            String operador = operadorP.getText().toString();
                            String ult_viaje = ultViajeP.getText().toString();
                            String fecha = fechaP.getText().toString();
                            String descripcion = descripcionP.getText().toString();
//                            String mNombre = NombreReporte.getText().toString();
//                            String mFecha = FechaReporte.getText().toString();

                            //Reporte reporte = new Reporte(unidad,operador,ult_viaje,fecha,descripcion,downloadUri.toString());


                            Reporte r = new Reporte();
                            r.setUid(UUID.randomUUID().toString());
                            r.setUnidad(unidad);
                            r.setOperador(operador);
                            r.setViaje(ult_viaje);
                            r.setFecha(fecha);
                            r.setDescripcion(descripcion);
                            r.setEstatus("Abierto");
                            r.setImagen(downloadUri.toString());
                            databaseReference.child("Reporte").child(r.getUid()).setValue(r);
//                           String ID_IMAGEN = databaseReference.push().getKey();
                            Toast.makeText(AddReporteActivity.this, "Agregado", Toast.LENGTH_LONG).show();

//                            databaseReference.child(ID_IMAGEN).setValue(r);
                            progressDialog.dismiss();
                            Toast.makeText(AddReporteActivity.this,"Subido con exito",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AddReporteActivity.this,AddReporteActivity.class));
                            limpiarCajas();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddReporteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressDialog.setTitle("Subiendo Reporte");
                    progressDialog.setCancelable(false);
                }
            });

        }
        else{
            Toast.makeText(this, "Debe asignar una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String unidad = unidadP.getText().toString();
        String operador = operadorP.getText().toString();
        String ult_viaje = ultViajeP.getText().toString();
        String fecha = fechaP.getText().toString();
        String descripcion = descripcionP.getText().toString();

        int id = item.getItemId();
        if (id==android.R.id.home){
            finish();
        }

        switch (item.getItemId()){
            case R.id.icon_add:{
                if (unidad.equals("")||operador.equals("")||ult_viaje.equals("")||fecha.equals("")){
                    validacion();
                }
                else {
                    Reporte r = new Reporte();
                    r.setUid(UUID.randomUUID().toString());
                    r.setUnidad(unidad);
                    r.setOperador(operador);
                    r.setViaje(ult_viaje);
                    r.setFecha(fecha);
                    r.setDescripcion(descripcion);
                    r.setEstatus("Abierto");
                    databaseReference.child("Reporte").child(r.getUid()).setValue(r);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:{
                Reporte r = new Reporte();
//                r.setUid(reporteSelected.getUid());
                r.setUnidad(unidadP.getText().toString().trim());
                r.setOperador(operadorP.getText().toString().trim());
                r.setViaje(ultViajeP.getText().toString().trim());
                r.setFecha(fechaP.getText().toString().trim());
                r.setDescripcion(descripcionP.getText().toString().trim());
                databaseReference.child("Reporte").child(r.getUid()).setValue(r);
                Toast.makeText(this,"Actualizado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{
                Reporte r = new Reporte();
//                r.setUid(reporteSelected.getUid());
                databaseReference.child("Reporte").child(r.getUid()).removeValue();
                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas() {
        unidadP.setText("");
        operadorP.setText("");
        ultViajeP.setText("");
        fechaP.setText("");
        descripcionP.setText("");
    }

    private void validacion() {
        String unidad = unidadP.getText().toString();
        String operador = operadorP.getText().toString();
        String ult_viaje = ultViajeP.getText().toString();
        String fecha = fechaP.getText().toString();
        String descripcion = descripcionP.getText().toString();
        if (unidad.equals("")){
            unidadP.setError("Required");
        }
        else if (operador.equals("")){
            operadorP.setError("Required");
        }
        else if (ult_viaje.equals("")){
            ultViajeP.setError("Required");
        }
        else if (fecha.equals("")){
            fechaP.setError("Required");
        }
        else if (descripcion.equals("")){
            descripcionP.setError("Required");
        }
    }

    public void Ingresar(View view){

//        String unidad = unidadP.getText().toString();
//        String operador = operadorP.getText().toString();
//        String ult_viaje = ultViajeP.getText().toString();
//        String fecha = fechaP.getText().toString();
//        String descripcion = descripcionP.getText().toString();
//
//
//        Reporte r = new Reporte();
//        r.setUid(UUID.randomUUID().toString());
//        r.setUnidad(unidad);
//        r.setOperador(operador);
//        r.setViaje(ult_viaje);
//        r.setFecha(fecha);
//        r.setDescripcion(descripcion);
//        r.setEstatus("Abierto");
//        databaseReference.child("Reporte").child(r.getUid()).setValue(r);
//        Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
//        limpiarCajas();
        SubirImagen();
    }
}