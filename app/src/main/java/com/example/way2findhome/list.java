package com.example.way2findhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class list extends AppCompatActivity {

    ImageView iv;
    Connection connect;
    String list, img, id, sn, src;
    TextView title, harga;
    GridView gridview;
    ArrayList<String> arrayList;
    public Bitmap bitmapImageDB;
    public byte[] bytesImageDB;
    EditText search;
    String sitem;
    Button filter;
    String provinsi, kota, kecamatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        View angrid = getLayoutInflater().inflate(R.layout.grid, null);
        iv = angrid.findViewById(R.id.gimage);
        title = findViewById(R.id.gtitle);
        harga = findViewById(R.id.gharga);
        gridview = findViewById(R.id.gridview);
        search = findViewById(R.id.searchEditText);
        filter = findViewById(R.id.btnfilter);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            list = bundle.getString("list");
            img = bundle.getString("img");
            id = bundle.getString("id");
            sn = bundle.getString("n");
        }
        Bundle bdl2 = getIntent().getExtras();
        if(bdl2 != null){
            provinsi = bdl2.getString("provdata");
            kota = bdl2.getString("kotadata");
            src = bdl2.getString("src");
            list = bundle.getString("list");
            id = bundle.getString("id");
            sn = bundle.getString("n");
            if(provinsi == (null)){
                provinsi = "";
            }
            if(kota == (null)){
                kota = "";
            }
            if(src == (null)){
                src = "";
            }
            griddata();
        }
        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if(search.getText().length() < 3){
                        search.setError("Minimal 3 Huruf");
                    }
                    else{
                        src = search.getText().toString();
                        provinsi = "";
                        kota = "";
                        griddata();
                    }
                }

                return false;
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passMain = new Intent(list.this, pop.class);
                Bundle save = new Bundle();
                save.putString("src", src);
                save.putString("list", list);
                save.putString("id", id);
                save.putString("n", sn);
                passMain.putExtras(save);
                startActivity(passMain);
            }
        });


        griddata();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                String getID = item.get("id");
                Intent intent1 = new Intent(list.this, detail.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", getID);
                if(sn.equals("tanah")){
                    bundle.putString("cate", "Tanah");
                    bundle.putString("clause", "tanahID");
                }
                if(sn.equals("apart")){
                    bundle.putString("cate", "Apart");
                    bundle.putString("clause", "apartID");
                }
                if(sn.equals("rumah")){
                    bundle.putString("cate", "Rumah");
                    bundle.putString("clause", "rumahID");
                }
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

    }


    public void griddata() {
//        Toast.makeText(com.example.way2findhome.list.this, "select "+ id + ", " + sn+ ", harga from " + list + " where " + sn + " like '%" +src + "%' and lokasi_provinsi like '%" +provinsi+"%' and lokasi_kota like '%" +kota+"%' order by "+ id + " asc", Toast.LENGTH_LONG).show();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
//            String query = "select lt."+ id + ", images, " + sn+ ", harga from " + img + " ig join " + list + " lt on ig."+id+" = lt."+id+" order by lt."+ id + " asc";
            String query = "select "+ id + ", " + sn+ ", harga from " + list + " where " + sn + " like '%" +src + "%' and lokasi_provinsi like '%" +provinsi+"%' and lokasi_kota like '%" +kota+"%' order by "+ id + " asc";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while(rs.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);
                datanum.put("id", rs.getString(1));
                datanum.put("title", rs.getString(2));
                datanum.put("harga", rs.getString(3));
                data1.add(datanum);
            }

            String[] from = { "title", "harga", "id" };
            int[] views = { R.id.gtitle, R.id.gharga};
            final SimpleAdapter ADA = new SimpleAdapter(list.this, data1, R.layout.grid, from, views);
            gridview.setAdapter(ADA);
            ArrayAdapter<Bitmap> bp = new ArrayAdapter(list.this, R.layout.grid, R.id.gimage, data0);


//            rs.next();
//            byte[] bytesImageDB = rs.getBytes(1);
//            Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//            iv.setImageBitmap(bitmapImageDB);



        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }


}