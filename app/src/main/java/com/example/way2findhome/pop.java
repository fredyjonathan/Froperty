package com.example.way2findhome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class pop extends Activity {

    Spinner provspin;
    Spinner kotaspin;
    Spinner kecaspin;
    Connection connect;
    public String tempprov;
    public String tempkota;
    public String tempkeca;
    public String provdata;
    public String kotadata;
    public String kecadata;
    String  list, id, sn, src;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getActionBar().setTitle("Filter (Lokasi)");
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        provspin = (Spinner)findViewById(R.id.provspin);
        kotaspin = (Spinner)findViewById(R.id.kotaspin);
//        kecaspin = (Spinner)findViewById(R.id.kecaspin);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            src = bundle.getString("src");
            list = bundle.getString("list");
            id = bundle.getString("id");
            sn = bundle.getString("n");
       }
        getWindow().setLayout((int) (width*.8), (int) (height*.5));
        provspinner();

        Button closebtn = (Button)findViewById(R.id.btnclose);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(pop.this, list.class);
                Bundle bdl = new Bundle();
                bdl.putString("provdata", provdata);
                bdl.putString("kotadata", kotadata);
                bdl.putString("src", src);
                bdl.putString("list", list);
                bdl.putString("id", id);
                bdl.putString("n", sn);
                pass.putExtras(bdl);
                pass.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pass);
                finish();
            }
        });



    }

    public void provspinner(){

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from provinces";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String prov = rs.getString(2);
                data.add(prov);
            }

            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

            provspin.setAdapter(array);
            provspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tempprov = provspin.getSelectedItem().toString();
                    kotaspinner();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        catch (Exception ex){

        }
    }

    public void kotaspinner(){
        provdata = new String(tempprov);

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select nama, regname from provinces, regencies where nama="+"'"+provdata+"'"+"and regencies.province_id = provinces.id order by regname asc";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String kota = rs.getString(2);
                data.add(kota);
            }

            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

            kotaspin.setAdapter(array);

            kotaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tempkota = kotaspin.getSelectedItem().toString();
                    kotadata = new String(tempkota);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch (Exception ex){

        }
    }

    public void kecaspinner(){
        provdata = new String(tempprov);
        kotadata = new String(tempkota);

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select nama, regname, disname from provinces, regencies, districts where nama="+"'"+provdata+"'"+"AND regname="+"'"+kotadata+"'"+"and regencies.id = districts.regency_id and regencies.province_id = provinces.id order by regname asc, Disname asc";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String keca = rs.getString(3);
                data.add(keca);
            }

            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

            kecaspin.setAdapter(array);

            kecaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tempkeca = kecaspin.getSelectedItem().toString();
                    kecadata = new String(tempkeca);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch (Exception ex){

        }

    }

}
