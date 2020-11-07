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
    private Button confirma;

    private boolean muzica_pressed;
    private boolean dans_pressed;
    private boolean filme_pressed;
    private boolean teatru_pressed;

    private ArrayList<Tags> interests = new ArrayList<>();
    int color = 0xFF8A1515;
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
                if(muzica_pressed) {
                    muzica.setBackgroundColor(Color.RED);

                } else {
                    muzica.setBackgroundColor(Color.YELLOW);
                }
                muzica_pressed = !muzica_pressed;
                interests.add(Tags.MUZICA);
                updateInterests(Tags.MUZICA);
                break;
            case R.id.dans:
                if(dans_pressed) {
                    dans.setBackgroundColor(Color.RED);
                } else {
                    dans.setBackgroundColor(Color.YELLOW);

                }
                dans_pressed = !dans_pressed;
                interests.add(Tags.DANS);
                updateInterests(Tags.DANS);
                break;
            case R.id.filme:
                if(filme_pressed) {
                    filme.setBackgroundColor(Color.RED);
                } else {
                    filme.setBackgroundColor(Color.YELLOW);

                }
                filme_pressed = !filme_pressed;
                interests.add(Tags.FILME);
                updateInterests(Tags.FILME);
                break;
            case R.id.teatru:
                if(teatru_pressed) {
                    teatru.setBackgroundColor(Color.RED);
                } else {
                    teatru.setBackgroundColor(Color.YELLOW);

                }
                teatru_pressed = !teatru_pressed;
                interests.add(Tags.TEATRU);
                updateInterests(Tags.TEATRU);
                break;
            case R.id.confirm_button:
                Intent intent = new Intent(InterestsActivity.this, FeedActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void updateInterests(Tags tag) {
        FirebaseDatabase.getInstance().getReference("Users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("interese").child(tag.toString()).setValue("true");
        //System.out.println(E);
    }
}
