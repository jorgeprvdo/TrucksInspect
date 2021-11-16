package com.example.infologin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    private EditText Usuario;
    private EditText Correo;
    private EditText password;
    private EditText ConfirmPassword;
    boolean valid = true;
    Button resgiterbtn;
    CheckBox isOperadorBox, isSoporteBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Usuario = findViewById(R.id.register_username);
        Correo = findViewById(R.id.register_correo);
        password = findViewById(R.id.register_password);
        ConfirmPassword = findViewById(R.id.register_ConfirmPassword);

        isOperadorBox = findViewById(R.id.checkBox);
        isSoporteBox = findViewById(R.id.checkBox2);

        isOperadorBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isSoporteBox.setChecked(false);
                }
            }
        });

        isSoporteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isOperadorBox.setChecked(false);
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void registrarUsuario (View view){

        if (!(isOperadorBox.isChecked() || isSoporteBox.isChecked())) {
            Toast.makeText(this,"Elige el tipo de cuenta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().toString().equals(ConfirmPassword.getText().toString())) {

            mAuth.createUserWithEmailAndPassword(Correo.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Usuario creado.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String,Object> userInfo = new HashMap<>();
                                userInfo.put("Usuario",Usuario.getText().toString());
                                userInfo.put("Correo", Correo.getText().toString());

                                //Especifica si el usuario es admin
                                if(isOperadorBox.isChecked()){
                                    userInfo.put("isUser", "1");
                                }
                                if(isSoporteBox.isChecked()){
                                    userInfo.put("isAdmin", "1");
                                }

                                df.set(userInfo);

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user
                                Toast.makeText(getApplicationContext(), "Fallo la autenticacion.", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }


    }
}