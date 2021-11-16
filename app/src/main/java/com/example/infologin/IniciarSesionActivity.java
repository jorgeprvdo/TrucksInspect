package com.example.infologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class IniciarSesionActivity extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private EditText Correo;
    private EditText password;
    private FirebaseAuth mAuth;
    Button Loginbtn;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Correo = findViewById(R.id.Username);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
       }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void iniciarSesion (View view){
        mAuth.signInWithEmailAndPassword(Correo.getText().toString(),password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(), "Se ha iniciado la sesión", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //Extraer datos del documento
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSucces" + documentSnapshot.getData());
                // Identificar los accesos del usuario
                if(documentSnapshot.getString("isAdmin") != null) {
                    //Usuario es admin

                    startActivity(new Intent(getApplicationContext(),Admin.class));
                    finish();
                }
                    //Usuario no es admin
                if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();
                }
            }
        });
    }


//    public void iniciarSesion (View view){
//        mAuth.signInWithEmailAndPassword(Correo.getText().toString(), password.getText().toString())
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
//                            startActivity(i);
//                            Toast.makeText(getApplicationContext(), "Se ha iniciado la sesion.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(getApplicationContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//                    }
//                });
//    }
}