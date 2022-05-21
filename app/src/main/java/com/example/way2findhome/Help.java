package com.example.way2findhome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    public TextView edtmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setTitle("Bantuan");

        edtmail = findViewById(R.id.edtmail);

        edtmail.setText("Email:" + '\n' + "fropertyapp@gmail.com");
    }
}