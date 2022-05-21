package com.example.way2findhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class detail extends AppCompatActivity {

    public String contact;
    String cate, id, clause;
    Connection connect;
    ImageView ivimage;
    TextView tvtitle, tvharga, tvseller, tvalamat, tvprov, tvkota, tvkeca, tvopsi, tvpanjang,
            tvlebar, tvluastanah, tvluasbangunan, tvlantai, tvbedroom, tvbathroom, tvinterior,
            tvhalaman, tvparkir, tvlistrik, tvair, tvsertifikat, tvketerangan;
    Button btkontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //assign
        ivimage = findViewById(R.id.dtimg);
        //
        tvtitle = findViewById(R.id.dttitle);
        tvharga = findViewById(R.id.dtharga);
        tvseller = findViewById(R.id.dtseller);
        tvalamat = findViewById(R.id.dtalamat);
        tvprov = findViewById(R.id.dtprov);
        tvkota = findViewById(R.id.dtkota);
        tvkeca = findViewById(R.id.dtkeca);
        tvopsi = findViewById(R.id.dtopsi);
        tvpanjang = findViewById(R.id.dtpanjang);
        tvlebar = findViewById(R.id.dtlebar);
        tvluastanah = findViewById(R.id.dtluastanah);
        tvluasbangunan = findViewById(R.id.dtluasbangunan);
        tvlantai = findViewById(R.id.dtlantai);
        tvbedroom = findViewById(R.id.dtbedroom);
        tvbathroom = findViewById(R.id.dtbathroom);
        tvinterior = findViewById(R.id.dtinterior);
        tvhalaman = findViewById(R.id.dthalaman);
        tvparkir = findViewById(R.id.dtparkir);
        tvlistrik = findViewById(R.id.dtlistrik);
        tvair = findViewById(R.id.dtair);
        tvsertifikat = findViewById(R.id.dtsertifikat);
        tvketerangan = findViewById(R.id.dtketerangan);
        //
        btkontak = findViewById(R.id.dtkontak);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("ID");
            cate = bundle.getString("cate");
            clause = bundle.getString("clause");
        }

        if(cate.equals("Tanah")){
            tvluasbangunan.setVisibility(View.GONE);
            tvlantai.setVisibility(View.GONE);
            tvbedroom.setVisibility(View.GONE);
            tvbathroom.setVisibility(View.GONE);
            tvinterior.setVisibility(View.GONE);
            tvhalaman.setVisibility(View.GONE);
            tvparkir.setVisibility(View.GONE);
            tvlistrik.setVisibility(View.GONE);
            tvair.setVisibility(View.GONE);
            getdetailTanah();
            btkontak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialContactPhone(contact);
                }
            });
        }


        if(cate.equals("Apart")){
            getdetailApart();
            tvluastanah.setVisibility(View.GONE);
            tvparkir.setVisibility(View.GONE);
            tvair.setVisibility(View.GONE);
            btkontak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialContactPhone(contact);
                }
            });
        }

        if(cate.equals("Rumah")){
            getdetailRumah();
            btkontak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialContactPhone(contact);
                }
            });
        }

    }

    private void getdetailRumah() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + cate+ " where " + clause+ " = " + id+ ";";

            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while (rs.next()) {
                Map<String, Object> datanum = new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);


                tvtitle.setText(rs.getString(2));
                tvalamat.setText("Alamat: " + rs.getString(3));
                tvprov.setText("Provinsi: " + rs.getString(4));
                tvkota.setText("Kota: " + rs.getString(5));
                tvkeca.setText("Kecamatan: " +rs.getString(6));
                tvharga.setText("Harga: Rp " +rs.getString(7));
                tvseller.setText("Seller: " +rs.getString(8));
                contact = rs.getString(10);
                tvopsi.setText("Opsi: " +rs.getString(11));
                tvpanjang.setText("Panjang: " +rs.getString(12)+ " (m)");
                tvlebar.setText("Lebar: " +rs.getString(13)+ " (m)");
                tvluastanah.setText("Luas Tanah: " + rs.getString(14)+ " (m²)");
                tvluasbangunan.setText("Luas Bangunan: " +rs.getString(15)+ " (m²)");
                tvlantai.setText("Lantai: " +rs.getString(16));
                tvbedroom.setText("Kamar Tidur: " +rs.getString(17));
                tvbathroom.setText("Kamar Mandi: " +rs.getString(18));
                if(rs.getString(19).equals("1")){
                    tvinterior.setText("Interior: Ada");
                }
                if(rs.getString(19).equals("0")){
                    tvinterior.setText("Interior: Tidak");
                }
                if(rs.getString(20).equals("1")){
                    tvhalaman.setText("Halaman: Ada");
                }
                if(rs.getString(20).equals("0")){
                    tvhalaman.setText("Halaman: Tidak");
                }
                if(rs.getString(21).equals("1")){
                    tvparkir.setText("Garasi: Ada");
                }
                if(rs.getString(21).equals("0")){
                    tvparkir.setText("Garasi: Tidak");
                }
                tvlistrik.setText("Listrik: " +rs.getString(22)+ " (watt)");
                tvair.setText("Air: " +rs.getString(23));
                if(rs.getString(24).equals("1")){
                    tvsertifikat.setText("Sertifikat: Hak Milik");
                }
                if(rs.getString(24).equals("2")){
                    tvsertifikat.setText("Sertifikat: Hak Guna Bangunan");
                }
                if(rs.getString(24).equals("3")){
                    tvsertifikat.setText("Sertifikat: Tanah Girik");
                }
                if(rs.getString(24).equals("4")){
                    tvsertifikat.setText("Sertifikat: Belum Pecah");
                }
                if(rs.getString(24).equals("5")){
                    tvsertifikat.setText("Sertifikat: Akta Jual Beli");
                }
                if(rs.getString(24).equals("6")){
                    tvsertifikat.setText("Sertifikat: Perjanjian Pengikatan Jual Beli");
                }
                tvketerangan.setText(rs.getString(25));

                data1.add(datanum);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    private void getdetailApart() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + cate+ " where " + clause+ " = " + id+ ";";

            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while (rs.next()) {
                Map<String, Object> datanum = new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);


                tvtitle.setText(rs.getString(2));
                tvalamat.setText("Alamat: " + rs.getString(3));
                tvprov.setText("Provinsi: " + rs.getString(4));
                tvkota.setText("Kota: " + rs.getString(5));
                tvkeca.setText("Kecamatan: " +rs.getString(6));
                tvharga.setText("Harga: Rp " +rs.getString(7));
                tvseller.setText("Seller: " +rs.getString(8));
                contact = rs.getString(10);
                tvopsi.setText("Opsi: " +rs.getString(11));
                tvpanjang.setText("Panjang: " +rs.getString(12)+ " (m)");
                tvlebar.setText("Lebar: " +rs.getString(13)+ " (m)");
                tvluasbangunan.setText("Luas Bangunan: " +rs.getString(14)+ " (m²)");

                tvlantai.setText("Lantai: " +rs.getString(15));
                tvbedroom.setText("Kamar Tidur: " +rs.getString(16));
                tvbathroom.setText("Kamar Mandi: " +rs.getString(17));
                if(rs.getString(18).equals("1")){
                    tvinterior.setText("Interior: Ada");
                }
                if(rs.getString(18).equals("0")){
                    tvinterior.setText("Interior: Tidak");
                }
                if(rs.getString(19).equals("1")){
                    tvhalaman.setText("Halaman: Ada");
                }
                if(rs.getString(19).equals("0")){
                    tvhalaman.setText("Halaman: Tidak");
                }
                tvlistrik.setText("Listrik: " +rs.getString(20)+ " (watt)");

                if(rs.getString(21).equals("1")){
                    tvsertifikat.setText("Sertifikat: Hak Milik");
                }
                if(rs.getString(21).equals("2")){
                    tvsertifikat.setText("Sertifikat: Hak Guna Bangunan");
                }
                if(rs.getString(21).equals("3")){
                    tvsertifikat.setText("Sertifikat: Tanah Girik");
                }
                if(rs.getString(21).equals("4")){
                    tvsertifikat.setText("Sertifikat: Belum Pecah");
                }
                if(rs.getString(21).equals("5")){
                    tvsertifikat.setText("Sertifikat: Akta Jual Beli");
                }
                if(rs.getString(21).equals("6")){
                    tvsertifikat.setText("Sertifikat: Perjanjian Pengikatan Jual Beli");
                }
                tvketerangan.setText(rs.getString(22));

                data1.add(datanum);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void getdetailTanah() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + cate+ " where " + clause+ " = " + id+ ";";

            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while (rs.next()) {
                Map<String, Object> datanum = new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);


                tvtitle.setText(rs.getString(2));
                tvalamat.setText("Alamat: " + rs.getString(3));
                tvprov.setText("Provinsi: " + rs.getString(4));
                tvkota.setText("Kota: " + rs.getString(5));
                tvkeca.setText("Kecamatan: " +rs.getString(6));
                tvharga.setText("Harga: Rp " +rs.getString(7));
                tvseller.setText("Seller: " +rs.getString(8));
                contact = rs.getString(10);
                tvopsi.setText("Opsi: " +rs.getString(11));
                tvpanjang.setText("Panjang: " +rs.getString(12)+ " (m)");
                tvlebar.setText("Lebar: " +rs.getString(13)+ " (m)");
                tvluastanah.setText("Luas Tanah: " +rs.getString(14)+ " (m²)");

                if(rs.getString(15).equals("1")){
                    tvsertifikat.setText("Sertifikat: Hak Milik");
                }
                if(rs.getString(15).equals("2")){
                    tvsertifikat.setText("Sertifikat: Hak Guna Bangunan");
                }
                if(rs.getString(15).equals("3")){
                    tvsertifikat.setText("Sertifikat: Tanah Girik");
                }
                if(rs.getString(15).equals("4")){
                    tvsertifikat.setText("Sertifikat: Belum Pecah");
                }
                if(rs.getString(15).equals("5")){
                    tvsertifikat.setText("Sertifikat: Akta Jual Beli");
                }
                if(rs.getString(15).equals("6")){
                    tvsertifikat.setText("Sertifikat: Perjanjian Pengikatan Jual Beli");
                }
                tvketerangan.setText(rs.getString(16));

                data1.add(datanum);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }


}