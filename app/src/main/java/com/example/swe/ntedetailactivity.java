package com.example.swe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ntedetailactivity extends AppCompatActivity {
   TextView t,c;
   ImageView imageView;
    FloatingActionButton floatingActionButtonss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntedetailactivity);
        t=findViewById(R.id.titlefntedetail);
        imageView=findViewById(R.id.backpage);
        c=findViewById(R.id.descriptin);
        floatingActionButtonss=findViewById(R.id.edits);
         Objects.requireNonNull(getSupportActionBar()).hide();

        Intent data=getIntent();
        imageView.setOnClickListener(view -> {
            Intent i=new Intent(ntedetailactivity.this,mainntepage.class);
            startActivity(i);
        });
        floatingActionButtonss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),editnteactivity.class);

                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("cntent",data.getStringExtra("cntent"));
                intent.putExtra("nteid",data.getStringExtra("nteid"));
                view.getContext().startActivity(intent);

            }
        });
        t.setText(data.getStringExtra("title"));
        c.setText(data.getStringExtra("cntent"));


    }
}