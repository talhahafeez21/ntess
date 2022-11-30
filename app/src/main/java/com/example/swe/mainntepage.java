package com.example.swe;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class mainntepage extends AppCompatActivity {
    FloatingActionButton mfloatingActionButton;
    FirebaseAuth firebaseAuth;
    RecyclerView mrecyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseUser firebaseUser;
  ImageView imageView;
    FirebaseFirestore firebaseFirestore;
    Intent data=getIntent();
    Button bt;
    FirestoreRecyclerAdapter<firebasemdel,NoteviewHolder> nteadaptr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainntepage);
        mfloatingActionButton = findViewById(R.id.create);
        imageView=findViewById(R.id.img);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
         Objects.requireNonNull(getSupportActionBar()).hide();
        imageView.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");


            intent.putExtra(Intent.EXTRA_TEXT,"Dwnlad the app fr0m \n\n https://play.google.com/store/apps/details?id=com.socialnmobile.dictapps.notepad.color.note");

            startActivity(Intent.createChooser(intent,"Ch00se ne"));
        });

        mfloatingActionButton.setOnClickListener(view -> startActivity(new Intent(mainntepage.this, creatente.class)));
        Query query=firebaseFirestore.collection("ntes").document(firebaseUser.getUid()).collection("myntes").orderBy("title",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<firebasemdel> allfirestorerecycler= new FirestoreRecyclerOptions.Builder<firebasemdel>().setQuery(query,firebasemdel.class).build();
        nteadaptr=new FirestoreRecyclerAdapter<firebasemdel, NoteviewHolder>(allfirestorerecycler) {
            @Override
            protected void onBindViewHolder(@NonNull NoteviewHolder noteviewHolder, int position, @NonNull firebasemdel firebasemdel) {
               Button bt=noteviewHolder.itemView.findViewById(R.id.favbtns);

               bt.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Toast.makeText(mainntepage.this, "addedt favurte", Toast.LENGTH_SHORT).show();
                       Intent i=new Intent(mainntepage.this,favrite.class);
                       startActivity(i);


                   }
               });
                ImageView pp=noteviewHolder.itemView.findViewById(R.id.menuppbuttn);
                noteviewHolder.ntetitle.setText(firebasemdel.getTitle());
                noteviewHolder.ntecntent.setText(firebasemdel.getCntent());
                String did=nteadaptr.getSnapshots().getSnapshot(position).getId();
                noteviewHolder.itemView.setOnClickListener(view -> {
                    Intent intent= new Intent(view.getContext(),ntedetailactivity.class);
                    intent.putExtra("title",firebasemdel.getTitle());
                    intent.putExtra("cntent",firebasemdel.getCntent());
                    intent.putExtra("nteid",did);

                    view.getContext().startActivity(intent);

                });
                   pp.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                           popupMenu.setGravity(Gravity.END);
                           popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                               @Override
                               public boolean onMenuItemClick(MenuItem menuItem) {
                                   Intent intent=new Intent(view.getContext(),editnteactivity.class);
                                   intent.putExtra("title",firebasemdel.getTitle());
                                   intent.putExtra("cntent",firebasemdel.getCntent());
                                   intent.putExtra("nteid",did);
                                   view.getContext().startActivity(intent);
                                   return false;
                               }
                           });
                           popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                               @Override
                               public boolean onMenuItemClick(MenuItem menuItem) {
                                   DocumentReference documentReference=firebaseFirestore.collection("ntes").document(firebaseUser.getUid()).collection("myntes").document(did);
                                   documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void unused) {
                                           Toast.makeText(mainntepage.this, "Suceesfully deleted", Toast.LENGTH_SHORT).show();
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(mainntepage.this, "FAILED", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                                   return false;
                               }

                           });
                           popupMenu.getMenu().add("Share").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                               @Override
                               public boolean onMenuItemClick(MenuItem menuItem) {
                                   Intent share=new Intent();
                                   share.setAction(Intent.ACTION_SEND);
                                   share.putExtra(Intent.EXTRA_TEXT,firebasemdel.getCntent().toString());
                                   share.setType("text/plain");
                                   share=Intent.createChooser(share,"Share via");
                                   startActivity(share);

                                   return false;
                               }
                           });
                           popupMenu.show();
                       }
                   });
            }

            @NonNull
            @Override
            public NoteviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ntes_layut,parent,false);
                return new NoteviewHolder(view);
            }
        };


        mrecyclerView=findViewById(R.id.recyclerview);
        mrecyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(linearLayoutManager);
        mrecyclerView.setAdapter(nteadaptr);

    }




    public  class  NoteviewHolder extends RecyclerView.ViewHolder
    {
        private final TextView ntetitle;
        private final TextView ntecntent;
        LinearLayout mnte;

        public NoteviewHolder(@NonNull View itemView) {
            super(itemView);
            ntetitle=itemView.findViewById(R.id.ntetitle);
            ntecntent=itemView.findViewById(R.id.ntecntent);
            mnte=findViewById(R.id.nte);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

      return  true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lgut:

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(mainntepage.this, Lgin.class));
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected  void onStart()
    {
        super.onStart();
        nteadaptr.startListening();
    }
    @Override
    protected  void onStop()
    {
        super.onStop();
        if (nteadaptr != null)
        {
            nteadaptr.stopListening();
        }
    }


}


