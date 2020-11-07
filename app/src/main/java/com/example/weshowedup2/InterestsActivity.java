package com.example.weshowedup2;

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


        /*FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("interests")
                .setValue();
         */


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.muzica:
                interests.add(Tags.MUZICA);
                updateInterests(Tags.MUZICA);
                break;
            case R.id.dans:
                interests.add(Tags.DANS);
                updateInterests(Tags.DANS);
                break;
            case R.id.filme:
                interests.add(Tags.FILME);
                updateInterests(Tags.FILME);
                break;
            case R.id.teatru:
                interests.add(Tags.TEATRU);
                updateInterests(Tags.TEATRU);
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
