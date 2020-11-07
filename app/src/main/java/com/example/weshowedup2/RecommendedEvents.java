package com.example.weshowedup2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecommendedEvents extends AppCompatActivity implements View.OnClickListener {
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomends);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setTitle("");
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getMyList());

        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.appbar:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(RecommendedEvents.this, EventInfoActivity.class));
    }

    private ArrayList<Model> getMyList() {
        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("Secretul lui Florin");
        m.setData("21/10.2020");
        m.setLocation("Acasa la Maria");
        m.setOrganiser("Florin insusi");
        m.setImg(R.drawable.ic_logout);
        models.add(m);

        Model m2 = new Model();
        m2.setTitle("Burcalul Scarlatescu");
        m2.setData("22/10.2020");
        m2.setLocation("Acasa la Diana");
        m2.setOrganiser("Sefu Scarlatescu");
        m2.setImg(R.drawable.ic_logout);
        models.add(m2);

        Model m3 = new Model();
        m3.setTitle("Secretul ratelor");
        m3.setData("23/10.2020");
        m3.setLocation("Acasa la Ruxi");
        m3.setOrganiser("Ruxi");
        m3.setImg(R.drawable.ic_logout);
        models.add(m3);

        Model m4 = new Model();
        m4.setTitle("Ratonii se intorc");
        m4.setData("25/10.2020");
        m4.setLocation("Unde vreti voi");
        m4.setOrganiser("Ruxi");
        m4.setImg(R.drawable.ic_logout);
        models.add(m4);

        return models;

    }
}
