package com.example.weshowedup2;

import android.app.SyncNotedAppOp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


public class RecommendedEvents extends AppCompatActivity implements View.OnClickListener {
    RecyclerView mRecyclerView;
     MyAdapter myAdapter;
    ArrayList<Model> models = new ArrayList<>();

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

        // if it is the first log in, add events corresponding to tags
        Bundle b = getIntent().getExtras();
        boolean value = false; // or other values
        if(b != null) {
            value = b.getBoolean("first_login");

            // interese intiale
            ArrayList<String> interese_initiale = b.getStringArrayList("interese_intiale");
        }
        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Model> models) {
                //Log.d("TAG", mo);
                myAdapter = new MyAdapter(RecommendedEvents.this, models);
                mRecyclerView.setAdapter(myAdapter);
            }
        });


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
            case R.id.event_profile:
                startActivity(new Intent(RecommendedEvents.this, ProfileActivity.class));
                // Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.create_event:
                startActivity(new Intent(RecommendedEvents.this, EventInfoActivity.class));
                //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(RecommendedEvents.this, EventInfoActivity.class));
    }

    private void getMyList() {


    }


    public interface MyCallback {
        void onCallback(ArrayList<Model> models);
    }

    public void readData(MyCallback myCallback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("evenimente_recomandate");



        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot uniqueUserSnapshot : snapshot.getChildren()) {

                    DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events")
                            .child(uniqueUserSnapshot.getKey());

                    eventsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot != null){
                                Log.e("val", "curiozitate");
                                String title = snapshot.child("titlu").getValue().toString();
                                String data = snapshot.child("data").getValue().toString();
                                String location = snapshot.child("locatie").getValue().toString();
                                String organiser = snapshot.child("organizator").getValue().toString();

                                int pic;
                                if(snapshot.child("img").getValue() == null) {
                                    pic  = R.drawable.ic_logout;
                                } else {
                                    pic  = Integer.parseInt(snapshot.child("img").getValue().toString());
                                }


                                Model m = new Model(title, data, location, organiser, pic);
                                m.setImg(pic);

                                models.add(m);
                                myCallback.onCallback(models);

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Val", error.getMessage());
            }


        });

    }
}
