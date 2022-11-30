package com.example.swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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

public class Lgin extends AppCompatActivity {
   EditText name ,pass;
   Button lgins;
   FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgin);


            name=findViewById(R.id.lginemail);
            pass=findViewById(R.id.lginpass);
            lgins= findViewById(R.id.lgin);
            firebaseAuth=FirebaseAuth.getInstance();
            lgins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String na=name.getText().toString();
                    String pas=pass.getText().toString();
                    if (na.isEmpty() && pas.isEmpty())
                    {
                        Toast.makeText(Lgin.this,"Enter values",Toast.LENGTH_LONG).show();
                        Intent i= new Intent(Lgin.this,Lgin.class);
                        startActivity(i);
                    }
                     firebaseAuth.signInWithEmailAndPassword(na,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {



                                if (task.isSuccessful())
                                {
                                    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                                    if (firebaseUser.isEmailVerified())
                                    {
                                        Toast.makeText(Lgin.this,"SUCCESFULLY lgin",Toast.LENGTH_SHORT).show();
                                        Intent i= new Intent(Lgin.this, mainntepage.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Lgin.this, "verify yur email", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Lgin.this, "ERRR", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }

                                }

                                else
                                {
                                    Toast.makeText(Lgin.this," Not successfully  lgin",Toast.LENGTH_SHORT).show();
                                    pass.setText("");
                                    name.setText("");
                                }

                        }
                    });
                }
            });
        }


    }




