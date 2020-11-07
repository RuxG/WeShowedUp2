package com.example.weshowedup2;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InterestsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button muzica;
    private Button dans;
    private Button filme;
    private Button teatru;
    private Button arta;
    private Button muzee;
    private Button confirma;

    private boolean muzica_pressed;
    private boolean dans_pressed;
    private boolean filme_pressed;
    private boolean teatru_pressed;
    private boolean arta_pressed;
    private boolean muzee_pressed;

    private ArrayList<Tags> interests = new ArrayList<>();
    int color = 0x91B8F3;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("interests");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        muzica = findViewById(R.id.muzica);
        muzica.setOnClickListener(this);

        dans = findViewById(R.id.dans);
        dans.setOnClickListener(this);

        filme = findViewById(R.id.filme);
        filme.setOnClickListener(this);

        teatru = findViewById(R.id.teatru);
        teatru.setOnClickListener(this);

        arta = findViewById(R.id.arta);
        arta.setOnClickListener(this);

        muzee = findViewById(R.id.muzee);
        muzee.setOnClickListener(this);

        confirma = findViewById(R.id.confirm_button);
        confirma.setOnClickListener(this);
        /*FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("interests")
                .setValue();
         */


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.muzica:
                updateColor(muzica_pressed, muzica);
                muzica_pressed = !muzica_pressed;
                interests.add(Tags.MUZICA);
                updateInterests(Tags.MUZICA);
                break;
            case R.id.dans:
                updateColor(dans_pressed, dans);
                dans_pressed = !dans_pressed;
                interests.add(Tags.DANS);
                updateInterests(Tags.DANS);
                break;
            case R.id.filme:
                updateColor(filme_pressed, filme);
                filme_pressed = !filme_pressed;
                interests.add(Tags.FILME);
                updateInterests(Tags.FILME);
                break;
            case R.id.teatru:
                updateColor(teatru_pressed, teatru);
                teatru_pressed = !teatru_pressed;
                interests.add(Tags.TEATRU);
                updateInterests(Tags.TEATRU);
                break;
            case R.id.arta:
                updateColor(arta_pressed, arta);
                arta_pressed = !arta_pressed;
                interests.add(Tags.ARTA);
                updateInterests(Tags.ARTA);
                break;
            case R.id.muzee:
                updateColor(muzee_pressed, muzee);
                muzee_pressed = !muzee_pressed;
                interests.add(Tags.MUZEE);
                updateInterests(Tags.MUZEE);
                break;
            case R.id.confirm_button:
                Intent intent = new Intent(InterestsActivity.this, RecommendedEvents.class);
                startActivity(intent);
                break;
        }
    }

    private void updateColor(boolean pressed, Button btn) {
        if (pressed) {
            btn.setBackgroundColor(color);
        } else {
            btn.setBackgroundColor(Color.BLUE);

        }
    }

    public void updateInterests(Tags tag) {
        FirebaseDatabase.getInstance().getReference("Users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("interese").child(tag.toString()).setValue("true");
    }

}
