package com.example.way2findhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button filter = (Button) findViewById(R.id.btnfilter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passMain = new Intent(MainActivity.this, pop.class);
                startActivity(passMain);
            }
        });

        Intent provintent = getIntent();
        String provinsi = provintent.getStringExtra("provdata");
        String kota = provintent.getStringExtra("kotadata");
        String kecamatan = provintent.getStringExtra("kecadata");

        TextView prov = (TextView)findViewById(R.id.provtext);
        prov.setText(provinsi);
        TextView kot = (TextView)findViewById(R.id.kotatext);
        kot.setText(kota);
        TextView keca = (TextView)findViewById(R.id.kecatext);
        keca.setText(kecamatan);


    }



}



