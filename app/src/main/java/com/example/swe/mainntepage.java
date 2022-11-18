package com.example.swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class mainntepage extends AppCompatActivity {
    FloatingActionButton mfloatingActionButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainntepage);
        mfloatingActionButton = findViewById(R.id.create);
        firebaseAuth = FirebaseAuth.getInstance();

        mfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainntepage.this, creatente.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

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
}
