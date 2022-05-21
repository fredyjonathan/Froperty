package com.example.way2findhome.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.way2findhome.ConnectionHelper;
import com.example.way2findhome.Pemasaran;
import com.example.way2findhome.R;
import com.example.way2findhome.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demodify extends AppCompatActivity {

    public String contact;
    String cate, id, clause, uid;
    Connection connect;
    ImageView ivimage;
    Connection con;
    TextView tvtitle, tvharga, tvseller, tvalamat, tvprov, tvkota, tvkeca, tvopsi, tvpanjang,
            tvlebar, tvluastanah, tvluasbangunan, tvlantai, tvbedroom, tvbathroom, tvinterior,
            tvhalaman, tvparkir, tvlistrik, tvair, tvsertifikat, tvketerangan;
    Button btedit, bthapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demodify);
        Bundle bundle2 = getIntent().getExtras();
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
        btedit = findViewById(R.id.dtedit);
        bthapus = findViewById(R.id.dthapus);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("ID");
            cate = bundle.getString("cate");
            clause = bundle.getString("clause");
            uid = bundle.getString("uid");
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
            bthapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            btedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent modi = new Intent(Demodify.this, modify.class);
                    Bundle tbd = new Bundle();
                    tbd.putString("kate", "Tanah");
                    tbd.putString("id", id);

                    modi.putExtras(tbd);
                    finish();
                    startActivity(modi);
                }
            });
        }


        if(cate.equals("Apart")){
            getdetailApart();
            tvluastanah.setVisibility(View.GONE);
            tvparkir.setVisibility(View.GONE);
            tvair.setVisibility(View.GONE);
            bthapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            btedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent modi = new Intent(Demodify.this, modify.class);
                    Bundle tbd = new Bundle();
                    tbd.putString("kate", "Apart");
                    tbd.putString("id", id);

                    modi.putExtras(tbd);
                    finish();
                    startActivity(modi);
                }
            });
        }

        if(cate.equals("Rumah")){
            getdetailRumah();
            bthapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });

            btedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent modi = new Intent(Demodify.this, modify.class);
                    Bundle tbd = new Bundle();
                    tbd.putString("kate", "Rumah");
                    tbd.putString("id", id);

                    modi.putExtras(tbd);
                    finish();
                    startActivity(modi);
                }
            });
        }

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Intent intent1 = new Intent(this, Pemasaran.class);
            Bundle bundle3 = new Bundle();
            bundle3.putString("UID", uid);
            intent1.putExtras(bundle3);
            startActivity(intent1);
            finish();
        }
        else {
            Intent intent1 = new Intent(this, Pemasaran.class);
            Bundle bundle3 = new Bundle();
            bundle3.putString("UID", uid);
            intent1.putExtras(bundle3);
            startActivity(intent1);
            finish();
            return;
        }
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Hapus Properti");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk Hapus!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        deletepro();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void deletepro(){
        ConnectionHelper connectionHelper = new ConnectionHelper();
        con = connectionHelper.connectionclass();
        try {
            String query = "delete from list" + cate+ " where " + clause+ " = " + id+ ";";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.executeUpdate();
            Intent intent1 = new Intent(this, Pemasaran.class);
            Bundle bundle3 = new Bundle();
            bundle3.putString("UID", uid);
            intent1.putExtras(bundle3);
            startActivity(intent1);
            finish();

        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
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
            List<Map<String, Object>> datarumah1 = null;
            datarumah1 = new ArrayList<Map<String, Object>>();

            while (rs.next()) {
                Map<String, Object> datarumah = new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);

                datarumah.put("idrumah", rs.getString(1));
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
                tvluasbangunan.setText("Luas Bangunan: " +rs.getString(15) + " (m²)");
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

                datarumah1.add(datarumah);
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
            List<Map<String, Object>> dataapart1 = null;
            dataapart1 = new ArrayList<Map<String, Object>>();

            while (rs.next()) {
                Map<String, Object> dataapart= new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);

                dataapart.put("idapart", rs.getString(1));
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

                dataapart1.add(dataapart);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

//    private void dialContactPhone(final String phoneNumber) {
//        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
//    }

    private void getdetailTanah() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + cate+ " where " + clause+ " = " + id+ ";";

            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> datatanah1 = null;
            datatanah1 = new ArrayList<Map<String, Object>>();

            while (rs.next()) {
                Map<String, Object> datatanah = new HashMap<String, Object>();
//                byte[] bytesImageDB = rs.getBytes(1);
//                Bitmap bitmapImageDB = BitmapFactory.decodeByteArray(bytesImageDB, 0, bytesImageDB.length);
//                data0.add(bitmapImageDB);
//                iv.setImageBitmap(bitmapImageDB);

                datatanah.put("idtanah", rs.getString(1));
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

                datatanah1.add(datatanah);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }


}