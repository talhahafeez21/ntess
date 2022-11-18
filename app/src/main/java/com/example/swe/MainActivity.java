package com.example.swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   EditText n,p;
   Button s;
   TextView als;
   FirebaseAuth fireBaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n=findViewById(R.id.name);
        p=findViewById(R.id.pass);

        s=findViewById(R.id.signup);

        fireBaseAuth=FirebaseAuth.getInstance();

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ns=n.getText().toString();
                String ps=p.getText().toString();
                fireBaseAuth.createUserWithEmailAndPassword(ns,ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"SUCCESFULLY",Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(MainActivity.this,Lgin.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this," not SUCCESFULLY",Toast.LENGTH_SHORT).show();
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