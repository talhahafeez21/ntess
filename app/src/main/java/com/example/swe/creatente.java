package com.example.swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class creatente extends AppCompatActivity {
    private  static  final String CHANNEL_ID= "My Channel";
    private  static  final int  NTIFICATIN_ID= 100;
    EditText mcreatetitltefnte,mcreatedescriptinfnte;
    FloatingActionButton msavennte;
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseFirestore firebaseFirestore;
        Notification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatente);
        msavennte=findViewById(R.id.savente);
        mcreatetitltefnte=findViewById(R.id.createtitlefnte);
        mcreatedescriptinfnte=findViewById(R.id.createcntentfnte);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        msavennte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=mcreatetitltefnte.getText().toString();
                String cntent=mcreatedescriptinfnte.getText().toString();


                if (title.isEmpty() || cntent.isEmpty())
                {
                    Toast.makeText(creatente.this, "bth fields required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DocumentReference documentReference=firebaseFirestore.collection("ntes").document(firebaseUser.getUid()).collection("myntes").document();
                    Map<String,Object> nte=new HashMap<>();
                    nte.put("title",title);
                    nte.put("cntent",cntent);
                    documentReference.set(nte).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"nte created succesfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(creatente.this,mainntepage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed t0 create ",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notification= new Notification.Builder(creatente.this)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText("N0te created successfully")
                            .setSubText("nte")
                            .setChannelId(CHANNEL_ID)
                            .build();

                    notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"HELLI",NotificationManager.IMPORTANCE_HIGH));

                }
                else
                {
                    notification= new Notification.Builder(creatente.this)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText("talha")
                            .setSubText("alu")
                            .build();
                }
                notificationManager.notify(NTIFICATIN_ID,notification);

            }
        });
    }
}