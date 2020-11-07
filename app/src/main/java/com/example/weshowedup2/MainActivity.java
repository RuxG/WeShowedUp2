package com.example.weshowedup2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button registerButton;
    private Button loginButton;
    private ProgressBar progressBar;

    private CheckBox rememberMeCheckBox;
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

        progressBar = findViewById(R.id.progressBar);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkBox = preferences.getString("remember", "");
        if (checkBox.equals("true")) {
            startActivity(new Intent(MainActivity.this, RecommendedEvents.class));
        } else if (checkBox.equals("false")) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        rememberMeCheckBox = findViewById(R.id.rememberMe_checkBox);
        rememberMeCheckBox.setOnCheckedChangeListener(this);

        mAuth = FirebaseAuth.getInstance();

        keepUserLoggedIn();

    }

    private void keepUserLoggedIn() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                registerUser();
                break;
            case R.id.login_button:
                loginUser();
                break;
        }
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide valid email");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            emailEditText.setError("Pass is required");
            emailEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Min password length should be 6 chars");
            passwordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(email, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Register succes!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Failed register", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failed register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide valid email");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            emailEditText.setError("Pass is required");
            emailEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Min password length should be 6 chars");
            passwordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    //if (user.isEmailVerified()) {
                        // redirect to user preferences
                        startActivity(new Intent(MainActivity.this, InterestsActivity.class));
                        progressBar.setVisibility(View.GONE);
                   // } else {
                  //      user.sendEmailVerification();
                     //   Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                    //    progressBar.setVisibility(View.GONE);
                  //  }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login. Check your credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.isChecked()) {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "true");
            editor.apply();
            Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
        } else if (!compoundButton.isChecked()) {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();
            Toast.makeText(this, "UnChecked", Toast.LENGTH_SHORT).show();
        }
    }
}