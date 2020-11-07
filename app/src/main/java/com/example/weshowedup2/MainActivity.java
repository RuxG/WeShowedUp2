package com.example.weshowedup2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weshowedup2.data.RegisterUser;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        emailEditText = findViewById(R.id.email_textView);
        passwordEditText = findViewById(R.id.parola_textView);
        
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:

                break;
        }
    }
}