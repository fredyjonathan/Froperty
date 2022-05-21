package com.example.way2findhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.way2findhome.ui.Demodify;
import com.example.way2findhome.ui.modify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pemasaran extends AppCompatActivity {

    ImageView iv;
    Connection connect;
    String list, img, id, uid;
    TextView title, harga;
    GridView gridviewtanah, gridviewrumah, gridviewapart;
    ArrayList<String> arrayList;
    public Bitmap bitmapImageDB;
    public byte[] bytesImageDB;

    String sitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasaran);
        View angrid = getLayoutInflater().inflate(R.layout.grid, null);
        iv = angrid.findViewById(R.id.gimage);
        title = findViewById(R.id.gtitle);
        harga = findViewById(R.id.gharga);
        gridviewtanah = findViewById(R.id.gridviewtanah);
        gridviewrumah = findViewById(R.id.gridviewrumah);
        gridviewapart = findViewById(R.id.gridviewapart);

        geuid();
        getSupportActionBar().setTitle("Properti");

//        Bundle bundle = getIntent().getExtras();
//        if(bundle!=null){
//            list = bundle.getString("list");
//            img = bundle.getString("img");
//            id = bundle.getString("id");
//            sn = bundle.getString("n");
//        }
        gridviewtanah.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        gridviewrumah.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        gridviewapart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        griddata();

        griddata();
        gridviewtanah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                String getID = item.get("id");
                Intent intent2 = new Intent(Pemasaran.this, Demodify.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("ID", getID);

                    bundle2.putString("cate", "Tanah");
                    bundle2.putString("clause", "tanahID");
                bundle2.putString("uid", uid);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                finish();
            }
        });
        gridviewrumah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                String getID = item.get("id");
                Intent intent2 = new Intent(Pemasaran.this, Demodify.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("ID", getID);
                bundle2.putString("cate", "Rumah");
                bundle2.putString("clause", "rumahID");
                bundle2.putString("uid", uid);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                finish();
            }
        });
        gridviewapart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                String getID = item.get("id");
                Intent intent2 = new Intent(Pemasaran.this, Demodify.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("ID", getID);
                bundle2.putString("cate", "Apart");
                bundle2.putString("clause", "apartID");
                bundle2.putString("uid", uid);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                finish();
            }
        });
    }

    private void geuid() {
        Bundle bundle2 = getIntent().getExtras();
        if(bundle2!=null){
            uid = bundle2.getString("UID");
        }
        Bundle bundle3 = getIntent().getExtras();
        if(bundle3!=null){
            uid = bundle2.getString("UID");
        }
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Pemasaran.this.finish();
        }
        else {
            Pemasaran.this.finish();
            return;
        }
    }

    public void griddata() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            String query = "select tanahID, tanah, harga from listTanah where sellerID = '" + uid+"';";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();


            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while(rs.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();

                datanum.put("id", rs.getString(1));
                datanum.put("title", rs.getString(2));
                datanum.put("harga", rs.getString(3));
                data1.add(datanum);
            }

            String[] from = { "title", "harga", "id" };
            int[] views = { R.id.gtitle, R.id.gharga};
            final SimpleAdapter ADA = new SimpleAdapter(Pemasaran.this, data1, R.layout.grid, from, views);
            gridviewtanah.setAdapter(ADA);


            String query2 = "select rumahID, rumah, harga from listRumah where sellerID = '" + uid+"';";
            PreparedStatement stat2 = connect.prepareStatement(query2);
            ResultSet rs2 = stat2.executeQuery();


            List<Map<String, Object>> data2 = null;
            data2 = new ArrayList<Map<String, Object>>();

            while(rs2.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();

                datanum.put("id", rs2.getString(1));
                datanum.put("title", rs2.getString(2));
                datanum.put("harga", rs2.getString(3));
                data2.add(datanum);
            }

            String[] from2 = { "title", "harga", "id" };
            int[] views2 = { R.id.gtitle, R.id.gharga};
            final SimpleAdapter ADA2 = new SimpleAdapter(Pemasaran.this, data2, R.layout.grid, from2, views2);
            gridviewrumah.setAdapter(ADA2);


            String query3 = "select apartID, apart, harga from listApart where sellerID = '" + uid+"';";
            PreparedStatement stat3 = connect.prepareStatement(query3);
            ResultSet rs3 = stat3.executeQuery();


            List<Map<String, Object>> data3 = null;
            data3 = new ArrayList<Map<String, Object>>();

            while(rs3.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();

                datanum.put("id", rs3.getString(1));
                datanum.put("title", rs3.getString(2));
                datanum.put("harga", rs3.getString(3));
                data3.add(datanum);
            }

            String[] from3 = { "title", "harga", "id" };
            int[] views3 = { R.id.gtitle, R.id.gharga};
            final SimpleAdapter ADA3 = new SimpleAdapter(Pemasaran.this, data3, R.layout.grid, from3, views3);
            gridviewapart.setAdapter(ADA3);

        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }
}