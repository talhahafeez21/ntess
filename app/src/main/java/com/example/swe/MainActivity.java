package com.example.swe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText n, p;

    Button s;
    Spinner spinner;
    FirebaseAuth fireBaseAuth;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n = findViewById(R.id.name);
        p = findViewById(R.id.pass);
        s = findViewById(R.id.signup);
        spinner = findViewById(R.id.spin);




        Objects.requireNonNull(getSupportActionBar()).hide();

        fireBaseAuth = FirebaseAuth.getInstance();
        arrayList.add("Male");
        arrayList.add("Female");



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(arrayAdapter);




        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ns = n.getText().toString();
                String ps = p.getText().toString();
                fireBaseAuth.createUserWithEmailAndPassword(ns, ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "SUCCESFULLY", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, Lgin.class);
                            startActivity(i);
                        } else {
                            n.setText("");
                            p.setText("");
                            Toast.makeText(MainActivity.this, " not SUCCESFULLY", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }





    public void talha(View view) {
        Intent i =new Intent(MainActivity.this,Lgin.class);
        startActivity(i);
    }
}