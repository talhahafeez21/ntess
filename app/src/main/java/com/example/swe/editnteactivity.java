package com.example.swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class editnteactivity extends AppCompatActivity {
  Intent data;
  EditText medittile,meditcntent;
  FloatingActionButton save;
  Button del;
  FirebaseAuth firebaseAuth;
  FirebaseUser firebaseUser;
  FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnteactivity);
        Objects.requireNonNull(getSupportActionBar()).hide();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        medittile=findViewById(R.id.edittitlefnte);
       meditcntent=findViewById(R.id.editcreatecntentfnte);
        save=findViewById(R.id.savente);

        data=getIntent();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newtitle=medittile.getText().toString();
                String newcntent=meditcntent.getText().toString();

                if (newtitle.isEmpty() || newcntent.isEmpty())
                {
                    Toast.makeText(editnteactivity.this, "smethimg is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DocumentReference documentReference=firebaseFirestore.collection("ntes").document(firebaseUser.getUid()).collection("myntes").document(data.getStringExtra("nteid"));
                    Map<String,Object> nte=new HashMap<>();
                    nte.put("title",newtitle);
                    nte.put("cntent",newcntent);
                    documentReference.set(nte).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(editnteactivity.this, "updated", Toast.LENGTH_SHORT).show();
                             Intent i =new Intent(editnteactivity.this,mainntepage.class);
                             startActivity(i);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(editnteactivity.this, "nt updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        String ntestitle=data.getStringExtra("title");
        String desc=data.getStringExtra("cntent");
        medittile.setText(ntestitle);
        meditcntent.setText(desc);

    }
}