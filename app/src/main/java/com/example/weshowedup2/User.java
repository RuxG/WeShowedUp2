package com.example.weshowedup2;

import android.nfc.Tag;

import java.util.HashSet;

public class User {

    public String email, password;
    public HashSet<Tags> interests;
    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
