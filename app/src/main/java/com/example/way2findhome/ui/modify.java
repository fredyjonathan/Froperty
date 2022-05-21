package com.example.way2findhome.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.way2findhome.ConnectionHelper;
import com.example.way2findhome.Pemasaran;
import com.example.way2findhome.R;
import com.example.way2findhome.ui.dashboard.DashboardFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class modify extends AppCompatActivity {


    Spinner provspin;
    Spinner kotaspin;
    Spinner kecaspin;
    Connection connect, con;
    public String idk, kate;
    public String tempprov;
    public String tempkota;
    public String tempkeca;
    TextView tvtitle, tvharga, tvseller, tvalamat, tvprov, tvkota, tvkeca, tvopsi, tvpanjang,
            tvlebar, tvluastanah, tvluasbangunan, tvlantai, tvbedroom, tvbathroom, tvinterior,
            tvhalaman, tvparkir, tvlistrik, tvair, tvsertifikat, tvketerangan;
    public EditText etnama,etalamat, etluastanah, etluasbangunan, etlantai, etbedroom, etbathroom, etlistrik, etharga, etkontak, etpanjang, etlebar, etdetail;
    public String provdata, kotadata, kecadata;
    public RadioGroup halaman, interior, parkir, air, opsi, sertifikat;
    public RadioButton jual, sewa, shm, shgb, stg, sbp, ajb, ppjb, intada, inttidak, halada, haltidak, parkada, parktidak, airpam, airsumur;
    public Button btnubah;
    public String nama, alamat, provinsi, kota, keca, harga, seller, sellerID, jsopsi, kontak, panjang, lebar, luastanah, luasbangunan, sertif, keterangan, lantai, bedrom, bathrom, inte, hal, park, jsair, listrik;
    int opsiID, sertID, airID;
    public Spinner sprov, skota, skeca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        //
        etnama = findViewById(R.id.etnama);
        etalamat =  findViewById(R.id.etalamat);
        sprov = findViewById(R.id.provspin);
        skota = findViewById(R.id.kotaspin);
        skeca = findViewById(R.id.kecaspin);
//        provdata =  findViewById(R.id.provspin);
//        kotadata =  findViewById(R.id.kotaspin);
//        kecadata =  findViewById(R.id.kecaspin);
        etharga =  findViewById(R.id.etharga);
        etkontak =  findViewById(R.id.etkontak);
        opsi =  findViewById(R.id.txopsi);
        etpanjang =  findViewById(R.id.etpanjang);
        etlebar =  findViewById(R.id.etlebar);
        tvluasbangunan =  findViewById(R.id.txluasbangunan);
        etluasbangunan =  findViewById(R.id.etluasbangunan);
        tvluastanah =  findViewById(R.id.txluastanah);
        etluastanah =  findViewById(R.id.etluastanah);
        tvlantai =  findViewById(R.id.txlantai);
        etlantai =  findViewById(R.id.etlantai);
        tvbedroom =  findViewById(R.id.txbedroom);
        etbedroom =  findViewById(R.id.etbedroom);
        tvbathroom =  findViewById(R.id.txbathroom);
        etbathroom =  findViewById(R.id.etbathroom);
        tvlistrik =  findViewById(R.id.txlistrik);
        etlistrik =  findViewById(R.id.etlistrik);
        halaman =  findViewById(R.id.halaman);
        interior =  findViewById(R.id.interior);
        parkir =  findViewById(R.id.parkir);
        air =  findViewById(R.id.air);
        etdetail =  findViewById(R.id.etketerangan);
        sertifikat =  findViewById(R.id.sertifikat);
        jual = findViewById(R.id.jual);
        sewa = findViewById(R.id.sewa);

        shm = findViewById(R.id.shm);
        shgb = findViewById(R.id.shgb);
        stg = findViewById(R.id.stg);
        sbp = findViewById(R.id.sbp);
        ajb = findViewById(R.id.ajb);
        ppjb = findViewById(R.id.ppjb);

        provspin = (Spinner)findViewById(R.id.provspin);
        kotaspin = (Spinner)findViewById(R.id.kotaspin);
        kecaspin = (Spinner)findViewById(R.id.kecaspin);

        intada = findViewById(R.id.int_ada);
        inttidak = findViewById(R.id.int_tidak);
        halada = findViewById(R.id.hal_ada);
        haltidak = findViewById(R.id.hal_tidak);
        parkada = findViewById(R.id.par_ada);
        parktidak = findViewById(R.id.par_tidak);
        airpam = findViewById(R.id.pam);
        airsumur = findViewById(R.id.sumurtanah);

        btnubah = findViewById(R.id.btnubah);

        provspinner();

        provspinner();
        Bundle tanbdl = getIntent().getExtras();
        if(tanbdl != null){
            idk = tanbdl.getString("id");
            kate = tanbdl.getString("kate");
        }

        if(kate.equals("Tanah")){
            tvluasbangunan.setVisibility(View.GONE);
            etluasbangunan.setVisibility(View.GONE);
            tvlantai.setVisibility(View.GONE);
            etlantai.setVisibility(View.GONE);
            tvbedroom.setVisibility(View.GONE);
            etbedroom.setVisibility(View.GONE);
            tvbathroom.setVisibility(View.GONE);
            etbathroom.setVisibility(View.GONE);
            interior.setVisibility(View.GONE);
            halaman.setVisibility(View.GONE);
            parkir.setVisibility(View.GONE);
            tvlistrik.setVisibility(View.GONE);
            etlistrik.setVisibility(View.GONE);
            air.setVisibility(View.GONE);
            getdetailTanah();
            sertif = "1";
            sertifikat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.shm:
                            sertif = "1";
                            break;
                        case R.id.shgb:
                            sertif = "2";
                            break;
                        case R.id.stg:
                            sertif = "3";
                            break;
                        case R.id.sbp:
                            sertif = "4";
                            break;
                        case R.id.ajb:
                            sertif = "5";
                            break;
                        case R.id.ppjb:
                            sertif = "6";
                            break;
                    }
                }
            });
            btnubah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etnama.getText().toString().isEmpty()){
                        etnama.setError("Tidak boleh kosong!");
                        etnama.requestFocus();
                    }
                    if(etalamat.getText().toString().isEmpty()){
                        etalamat.setError("Tidak boleh kosong!");
                        etalamat.requestFocus();
                    }
                    if(etharga.getText().toString().isEmpty()){
                        etharga.setError("Tidak boleh kosong!, 0 - 1000000000000");
                        etharga.requestFocus();
                    }
                    if(etkontak.getText().toString().isEmpty()){
                        etkontak.setError("Tidak boleh kosong!");
                        etkontak.requestFocus();
                    }
                    if(etpanjang.getText().toString().isEmpty()){
                        etpanjang.setError("Tidak boleh kosong!");
                        etpanjang.requestFocus();
                    }
                    if(etlebar.getText().toString().isEmpty()){
                        etlebar.setError("Tidak boleh kosong!");
                        etlebar.requestFocus();
                    }
                    if(etluastanah.getText().toString().isEmpty()){
                        etluastanah.setError("Tidak boleh kosong!");
                        etluastanah.requestFocus();
                    }
                    if(etdetail.getText().toString().isEmpty()){
                        etdetail.setError("Tidak boleh kosong!");
                        etdetail.requestFocus();
                    }
                    if(
                            !etnama.getText().toString().isEmpty() &&
                                    !etalamat.getText().toString().isEmpty() &&
                                    !etharga.getText().toString().isEmpty() &&
                                    !etkontak.getText().toString().isEmpty() &&
                                    !etpanjang.getText().toString().isEmpty() &&
                                    !etlebar.getText().toString().isEmpty() &&
                                    !etluastanah.getText().toString().isEmpty() &&
                                    !etdetail.getText().toString().isEmpty()
                    ){
                        new modify.modifytanah().execute("");
                    }
                }
            });
        }

        if(kate.equals("Rumah")){
            tvluastanah.setVisibility(View.VISIBLE);
            etluastanah.setVisibility(View.VISIBLE);
            tvluasbangunan.setVisibility(View.VISIBLE);
            etluasbangunan.setVisibility(View.VISIBLE);
            tvlantai.setVisibility(View.VISIBLE);
            etlantai.setVisibility(View.VISIBLE);
            tvbedroom.setVisibility(View.VISIBLE);
            etbedroom.setVisibility(View.VISIBLE);
            tvbathroom.setVisibility(View.VISIBLE);
            etbathroom.setVisibility(View.VISIBLE);
            interior.setVisibility(View.VISIBLE);
            halaman.setVisibility(View.VISIBLE);
            parkir.setVisibility(View.VISIBLE);
            tvlistrik.setVisibility(View.VISIBLE);
            etlistrik.setVisibility(View.VISIBLE);
            air.setVisibility(View.VISIBLE);
            getdetailRumah();
            inte = "1";
            interior.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.int_ada:
                            inte = "1";
                            break;
                        case R.id.int_tidak:
                            inte = "0";
                    }
                }
            });
            hal = "1";
            halaman.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.hal_ada:
                            hal = "1";
                            break;
                        case R.id.hal_tidak:
                            hal = "0";
                    }
                }
            });
            park = "1";
            parkir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.par_ada:
                            park = "1";
                            break;
                        case R.id.par_tidak:
                            park = "0";
                    }
                }
            });
            sertif = "1";
            sertifikat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.shm:
                            sertif = "1";
                            break;
                        case R.id.shgb:
                            sertif = "2";
                            break;
                        case R.id.stg:
                            sertif = "3";
                            break;
                        case R.id.sbp:
                            sertif = "4";
                            break;
                        case R.id.ajb:
                            sertif = "5";
                            break;
                        case R.id.ppjb:
                            sertif = "6";
                            break;
                    }
                }
            });
            btnubah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etnama.getText().toString().isEmpty()){
                        etnama.setError("Tidak boleh kosong!");
                        etnama.requestFocus();
                    }
                    if(etalamat.getText().toString().isEmpty()){
                        etalamat.setError("Tidak boleh kosong!");
                        etalamat.requestFocus();
                    }
                    if(etharga.getText().toString().isEmpty()){
                        etharga.setError("Tidak boleh kosong!, 0 - 1000000000000");
                        etharga.requestFocus();
                    }
                    if(etkontak.getText().toString().isEmpty()){
                        etkontak.setError("Tidak boleh kosong!");
                        etkontak.requestFocus();
                    }
                    if(etpanjang.getText().toString().isEmpty()){
                        etpanjang.setError("Tidak boleh kosong!");
                        etpanjang.requestFocus();
                    }
                    if(etlebar.getText().toString().isEmpty()){
                        etlebar.setError("Tidak boleh kosong!");
                        etlebar.requestFocus();
                    }
                    if(etluastanah.getText().toString().isEmpty()){
                        etluastanah.setError("Tidak boleh kosong!");
                        etluastanah.requestFocus();
                    }
                    if(etluasbangunan.getText().toString().isEmpty()){
                        etluasbangunan.setError("Tidak boleh kosong!");
                        etluasbangunan.requestFocus();
                    }
                    if(etlantai.getText().toString().isEmpty()){
                        etlantai.setError("Tidak boleh kosong!");
                        etlantai.requestFocus();
                    }
                    if(etbedroom.getText().toString().isEmpty()){
                        etbedroom.setError("Tidak boleh kosong!");
                        etbedroom.requestFocus();
                    }
                    if(etbathroom.getText().toString().isEmpty()){
                        etbathroom.setError("Tidak boleh kosong!");
                        etbathroom.requestFocus();
                    }
                    if(etlistrik.getText().toString().isEmpty()){
                        etlistrik.setError("Tidak boleh kosong!");
                        etlistrik.requestFocus();
                    }
                    if(etdetail.getText().toString().isEmpty()){
                        etdetail.setError("Tidak boleh kosong!");
                        etdetail.requestFocus();
                    }
                    if(
                            !etnama.getText().toString().isEmpty() &&
                                    !etalamat.getText().toString().isEmpty() &&
                                    !etharga.getText().toString().isEmpty() &&
                                    !etkontak.getText().toString().isEmpty() &&
                                    !etpanjang.getText().toString().isEmpty() &&
                                    !etlebar.getText().toString().isEmpty() &&
                                    !etluastanah.getText().toString().isEmpty() &&
                                    !etluasbangunan.getText().toString().isEmpty() &&
                                    !etlantai.getText().toString().isEmpty() &&
                                    !etbedroom.getText().toString().isEmpty() &&
                                    !etbathroom.getText().toString().isEmpty() &&
                                    !etlistrik.getText().toString().isEmpty() &&
                                    !etdetail.getText().toString().isEmpty()
                    ){
                        new modify.modifyrumah().execute("");
                    }
                }
            });
        }

        if(kate.equals("Apart")){
            tvluastanah.setVisibility(View.GONE);
            etluastanah.setVisibility(View.GONE);
            tvluasbangunan.setVisibility(View.VISIBLE);
            etluasbangunan.setVisibility(View.VISIBLE);
            tvlantai.setVisibility(View.VISIBLE);
            etlantai.setVisibility(View.VISIBLE);
            tvbedroom.setVisibility(View.VISIBLE);
            etbedroom.setVisibility(View.VISIBLE);
            tvbathroom.setVisibility(View.VISIBLE);
            etbathroom.setVisibility(View.VISIBLE);
            interior.setVisibility(View.VISIBLE);
            halaman.setVisibility(View.VISIBLE);
            parkir.setVisibility(View.GONE);
            tvlistrik.setVisibility(View.VISIBLE);
            etlistrik.setVisibility(View.VISIBLE);
            air.setVisibility(View.GONE);
            getdetailApart();
            inte = "1";
            interior.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.int_ada:
                            inte = "1";
                            break;
                        case R.id.int_tidak:
                            inte = "0";
                    }
                }
            });
            hal = "1";
            halaman.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.hal_ada:
                            hal = "1";
                            break;
                        case R.id.hal_tidak:
                            hal = "0";
                    }
                }
            });
            park = "1";
            parkir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.par_ada:
                            park = "1";
                            break;
                        case R.id.par_tidak:
                            park = "0";
                    }
                }
            });
            sertif = "1";
            sertifikat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.shm:
                            sertif = "1";
                            break;
                        case R.id.shgb:
                            sertif = "2";
                            break;
                        case R.id.stg:
                            sertif = "3";
                            break;
                        case R.id.sbp:
                            sertif = "4";
                            break;
                        case R.id.ajb:
                            sertif = "5";
                            break;
                        case R.id.ppjb:
                            sertif = "6";
                            break;
                    }
                }
            });
            btnubah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(etnama.getText().toString().isEmpty()){
                        etnama.setError("Tidak boleh kosong!");
                        etnama.requestFocus();
                    }
                    if(etalamat.getText().toString().isEmpty()){
                        etalamat.setError("Tidak boleh kosong!");
                        etalamat.requestFocus();
                    }
                    if(etharga.getText().toString().isEmpty()){
                        etharga.setError("Tidak boleh kosong!, 0 - 1000000000000");
                        etharga.requestFocus();
                    }
                    if(etkontak.getText().toString().isEmpty()){
                        etkontak.setError("Tidak boleh kosong!");
                        etkontak.requestFocus();
                    }
                    if(etpanjang.getText().toString().isEmpty()){
                        etpanjang.setError("Tidak boleh kosong!");
                        etpanjang.requestFocus();
                    }
                    if(etlebar.getText().toString().isEmpty()){
                        etlebar.setError("Tidak boleh kosong!");
                        etlebar.requestFocus();
                    }
                    if(etluasbangunan.getText().toString().isEmpty()){
                        etluasbangunan.setError("Tidak boleh kosong!");
                        etluasbangunan.requestFocus();
                    }
                    if(etlantai.getText().toString().isEmpty()){
                        etlantai.setError("Tidak boleh kosong!");
                        etlantai.requestFocus();
                    }
                    if(etbedroom.getText().toString().isEmpty()){
                        etbedroom.setError("Tidak boleh kosong!");
                        etbedroom.requestFocus();
                    }
                    if(etbathroom.getText().toString().isEmpty()){
                        etbathroom.setError("Tidak boleh kosong!");
                        etbathroom.requestFocus();
                    }
                    if(etlistrik.getText().toString().isEmpty()){
                        etlistrik.setError("Tidak boleh kosong!");
                        etlistrik.requestFocus();
                    }
                    if(etdetail.getText().toString().isEmpty()){
                        etdetail.setError("Tidak boleh kosong!");
                        etdetail.requestFocus();
                    }
                    if(
                            !etnama.getText().toString().isEmpty() &&
                                    !etalamat.getText().toString().isEmpty() &&
                                    !etharga.getText().toString().isEmpty() &&
                                    !etkontak.getText().toString().isEmpty() &&
                                    !etpanjang.getText().toString().isEmpty() &&
                                    !etlebar.getText().toString().isEmpty() &&
                                    !etluasbangunan.getText().toString().isEmpty() &&
                                    !etlantai.getText().toString().isEmpty() &&
                                    !etbedroom.getText().toString().isEmpty() &&
                                    !etbathroom.getText().toString().isEmpty() &&
                                    !etlistrik.getText().toString().isEmpty() &&
                                    !etdetail.getText().toString().isEmpty()
                    ){
                        new modify.modifyapart().execute("");
                    }

                }
            });
        }

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            showDialog();
        }
        else {
            showDialog();
            return;
        }
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Ubah Properti");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk batalkan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        modify.this.finish();
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

    public class modifyapart extends AsyncTask<String, String, String> {

        String z = null;
        String txtcount;
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            nama = etnama.getText().toString();
            alamat = etalamat.getText().toString();
            provinsi = sprov.getSelectedItem().toString();
            kota = skota.getSelectedItem().toString();
            keca = skeca.getSelectedItem().toString();
            harga = etharga.getText().toString();
            kontak = etkontak.getText().toString();
            opsiID = opsi.getCheckedRadioButtonId();
            RadioButton ropsi = findViewById(opsiID);
            jsopsi = ropsi.getText().toString();
            panjang = etpanjang.getText().toString();
            lebar = etlebar.getText().toString();
            luasbangunan = etluasbangunan.getText().toString();
            lantai = etlantai.getText().toString();
            bedrom = etbedroom.getText().toString();
            bathrom = etbathroom.getText().toString();
            listrik = etlistrik.getText().toString();
            keterangan = etdetail.getText().toString();

//        Toast.makeText(getActivity(), "Insert into listTanah values('"+ txtcount + "','" + nama + "','" + alamat + "','" + provinsi + "','" + kota + "','" + keca + "','" + harga + "','" + seller + "','" + sellerID + "','" + kontak + "','" + jsopsi + "','" + panjang + "','" + lebar + "','" + luastanah + "','" +sertif + "','" + keterangan + "');", Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            con = connectionHelper.connectionclass();
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(modify.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {


                    String query =
                            "update listApart set apart = '" + nama+ "', alamat = '" + alamat+ "', lokasi_provinsi = '" + provinsi+ "', lokasi_kota = '" + kota + "', lokasi_kabupaten = '" + keca + "', harga  = '" + harga + "', kontakSeller = '" + kontak + "', opsi  = '" + jsopsi + "', panjang = '" + panjang + "', lebar = '" + lebar + "', luasbangunan = '" + luasbangunan+ "', lantai = '" + lantai + "', jumlahkamartidur = '" + bedrom+ "', jumlahkamarmandi = '" +bathrom+ "', interior = '" +inte+ "', halaman = '" +hal+ "', listrik = '" +listrik+ "', sertifikat = '" + sertif + "', keterangan = '" + keterangan + "' where apartID = '" + idk+"'"; String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);


                    String sql = "select * FROM listApart WHERE apartID = '" + idk + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(modify.this, "Success", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(modify.this, Pemasaran.class);
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("UID", sellerID);
                                intent1.putExtras(bundle3);
                                finish();
                                startActivity(intent1);
                            }
                        });
                        z = "Success";


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(modify.this, "Fail", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }

    }
    public class modifyrumah extends AsyncTask<String, String, String> {

        String z = null;
        String txtcount;
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            nama = etnama.getText().toString();
            alamat = etalamat.getText().toString();
            provinsi = sprov.getSelectedItem().toString();
            kota = skota.getSelectedItem().toString();
            keca = skeca.getSelectedItem().toString();
            harga = etharga.getText().toString();
            kontak = etkontak.getText().toString();
            opsiID = opsi.getCheckedRadioButtonId();
            RadioButton ropsi = findViewById(opsiID);
            jsopsi = ropsi.getText().toString();
            panjang = etpanjang.getText().toString();
            lebar = etlebar.getText().toString();
            luastanah = etluastanah.getText().toString();
            luasbangunan = etluasbangunan.getText().toString();
            lantai = etlantai.getText().toString();
            bedrom = etbedroom.getText().toString();
            bathrom = etbathroom.getText().toString();
            listrik = etlistrik.getText().toString();
            airID = air.getCheckedRadioButtonId();
            RadioButton rair = findViewById(airID);
            jsair = rair.getText().toString();
            keterangan = etdetail.getText().toString();

//        Toast.makeText(getActivity(), "Insert into listTanah values('"+ txtcount + "','" + nama + "','" + alamat + "','" + provinsi + "','" + kota + "','" + keca + "','" + harga + "','" + seller + "','" + sellerID + "','" + kontak + "','" + jsopsi + "','" + panjang + "','" + lebar + "','" + luastanah + "','" +sertif + "','" + keterangan + "');", Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            con = connectionHelper.connectionclass();
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(modify.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {


                    String query =
                            "update listRumah set rumah = '" + nama+ "', alamat = '" + alamat+ "', lokasi_provinsi = '" + provinsi+ "', lokasi_kota = '" + kota + "', lokasi_kabupaten = '" + keca + "', harga  = '" + harga + "', kontakSeller = '" + kontak + "', opsi  = '" + jsopsi + "', panjang = '" + panjang + "', lebar = '" + lebar + "', luastanah = '" + luastanah + "', luasbangunan = '" + luasbangunan+ "', lantai = '" + lantai + "', jumlahkamartidur = '" + bedrom+ "', jumlahkamarmandi = '" +bathrom+ "', interior = '" +inte+ "', halaman = '" +hal+ "', parkir = '" +park+ "', listrik = '" +listrik+ "', air = '" +jsair+ "', sertifikat = '" + sertif + "', keterangan = '" + keterangan + "' where rumahID = '" + idk+"'";
                    String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);


                    String sql = "select * FROM listRumah WHERE rumahID = '" + idk + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(modify.this, "Success", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(modify.this, Pemasaran.class);
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("UID", sellerID);
                                intent1.putExtras(bundle3);
                                finish();
                                startActivity(intent1);
                            }
                        });
                        z = "Success";


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(modify.this, "Fail", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }

    }
    public class modifytanah extends AsyncTask<String, String, String> {

        String z = null;
        String txtcount;
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            nama = etnama.getText().toString();
            alamat = etalamat.getText().toString();
            provinsi = sprov.getSelectedItem().toString();
            kota = skota.getSelectedItem().toString();
            keca = skeca.getSelectedItem().toString();
            harga = etharga.getText().toString();
            kontak = etkontak.getText().toString();
            opsiID = opsi.getCheckedRadioButtonId();
            RadioButton ropsi = findViewById(opsiID);
            jsopsi = ropsi.getText().toString();
            panjang = etpanjang.getText().toString();
            lebar = etlebar.getText().toString();
            luastanah = etluastanah.getText().toString();

            keterangan = etdetail.getText().toString();

//        Toast.makeText(getActivity(), "Insert into listTanah values('"+ txtcount + "','" + nama + "','" + alamat + "','" + provinsi + "','" + kota + "','" + keca + "','" + harga + "','" + seller + "','" + sellerID + "','" + kontak + "','" + jsopsi + "','" + panjang + "','" + lebar + "','" + luastanah + "','" +sertif + "','" + keterangan + "');", Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            con = connectionHelper.connectionclass();
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(modify.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {


                    String query =
                            "update listTanah set tanah = '" + nama+ "', alamat = '" + alamat+ "', lokasi_provinsi = '" + provinsi+ "', lokasi_kota = '" + kota + "', lokasi_kabupaten = '" + keca + "', harga  = '" + harga + "', kontakSeller = '" + kontak + "', opsi  = '" + jsopsi + "', panjang = '" + panjang + "', lebar = '" + lebar + "', luastanah = '" + luastanah + "', sertifikat = '" + sertif + "', keterangan = '" + keterangan + "' where tanahID = '" + idk+"'";
                    String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);


                    String sql = "select * FROM listTanah WHERE tanahID = '" + idk + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(modify.this, "Success", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(modify.this, Pemasaran.class);
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("UID", sellerID);
                                intent1.putExtras(bundle3);
                                finish();
                                startActivity(intent1);
                            }
                        });
                        z = "Success";


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(modify.this, "Fail", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }

    }

    private void getdetailApart() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + kate+ " where apartID = " + idk + ";";

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

                datarumah.put("idapart", rs.getString(1));
                etnama.setText(rs.getString(2));
                etalamat.setText(rs.getString(3));
//                tvprov.setText("Provinsi: " + rs.getString(4));
//                tvkota.setText("Kota: " + rs.getString(5));
//                tvkeca.setText("Kecamatan: " +rs.getString(6));
                etharga.setText(rs.getString(7));
//                tvseller.setText("Seller: " +rs.getString(8));
                sellerID = rs.getString(9);
                etkontak.setText(rs.getString(10));
//                tvopsi.setText("Opsi: " +rs.getString(11));
                if(rs.getString(11).equals("Jual")){
                    jual.setChecked(true);
                    sewa.setChecked(false);
                }
                if(rs.getString(11).equals("Sewa")){
                    jual.setChecked(false);
                    sewa.setChecked(true);
                }
                etpanjang.setText(rs.getString(12));
                etlebar.setText(rs.getString(13));
                etluasbangunan.setText(rs.getString(13));
                etlantai.setText(rs.getString(15));
                etbedroom.setText(rs.getString(16));
                etbathroom.setText(rs.getString(17));
                if(rs.getString(18).equals("1")){
                    intada.setChecked(true);
                }
                if(rs.getString(18).equals("0")){
                    inttidak.setChecked(true);
                }
                if(rs.getString(19).equals("1")){
                    halada.setChecked(true);
                }
                if(rs.getString(19).equals("0")){
                    haltidak.setChecked(true);
                }

                etlistrik.setText(rs.getString(20));
//                tvair.setText("Air: " +rs.getString(23));

                if(rs.getString(21).equals("1")){
                    shm.setChecked(true);
                }
                if(rs.getString(21).equals("2")){
                    shgb.setChecked(true);
                }
                if(rs.getString(21).equals("3")){
                    stg.setChecked(true);
                }
                if(rs.getString(21).equals("4")){
                    sbp.setChecked(true);
                }
                if(rs.getString(21).equals("5")){
                    ajb.setChecked(true);
                }
                if(rs.getString(21).equals("6")){
                    ppjb.setChecked(true);
                }
                etdetail.setText(rs.getString(22));

                datarumah1.add(datarumah);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    private void getdetailRumah() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + kate+ " where rumahID = " + idk + ";";

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

                datarumah.put("idapart", rs.getString(1));
                etnama.setText(rs.getString(2));
                etalamat.setText(rs.getString(3));
//                tvprov.setText("Provinsi: " + rs.getString(4));
//                tvkota.setText("Kota: " + rs.getString(5));
//                tvkeca.setText("Kecamatan: " +rs.getString(6));
                etharga.setText(rs.getString(7));
//                tvseller.setText("Seller: " +rs.getString(8));
                sellerID = rs.getString(9);
                etkontak.setText(rs.getString(10));
//                tvopsi.setText("Opsi: " +rs.getString(11));
                if(rs.getString(11).equals("Jual")){
                    jual.setChecked(true);
                    sewa.setChecked(false);
                }
                if(rs.getString(11).equals("Sewa")){
                    jual.setChecked(false);
                    sewa.setChecked(true);
                }
                etpanjang.setText(rs.getString(12));
                etlebar.setText(rs.getString(13));
                etluastanah.setText(rs.getString(14));
                etluasbangunan.setText(rs.getString(15));
                etlantai.setText(rs.getString(16));
                etbedroom.setText(rs.getString(17));
                etbathroom.setText(rs.getString(18));
                if(rs.getString(19).equals("1")){
                    intada.setChecked(true);
                }
                if(rs.getString(19).equals("0")){
                    inttidak.setChecked(true);
                }
                if(rs.getString(20).equals("1")){
                    halada.setChecked(true);
                }
                if(rs.getString(20).equals("0")){
                    haltidak.setChecked(true);
                }
                if(rs.getString(21).equals("1")){
                    parkada.setChecked(true);
                }
                if(rs.getString(21).equals("0")){
                    parktidak.setChecked(true);
                }
                etlistrik.setText(rs.getString(22));
//                tvair.setText("Air: " +rs.getString(23));
                if(rs.getString(23).equals("PAM")){
                    airpam.setChecked(true);
                }
                if(rs.getString(23).equals("Sumur")){
                    airsumur.setChecked(true);
                }
                if(rs.getString(24).equals("1")){
                    shm.setChecked(true);
                }
                if(rs.getString(24).equals("2")){
                    shgb.setChecked(true);
                }
                if(rs.getString(24).equals("3")){
                    stg.setChecked(true);
                }
                if(rs.getString(24).equals("4")){
                    sbp.setChecked(true);
                }
                if(rs.getString(24).equals("5")){
                    ajb.setChecked(true);
                }
                if(rs.getString(24).equals("6")){
                    ppjb.setChecked(true);
                }
                etdetail.setText(rs.getString(25));

                datarumah1.add(datarumah);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    private void getdetailTanah() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select * from list" + kate+ " where tanahID = " + idk + ";";

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
                etnama.setText(rs.getString(2));
                etalamat.setText(rs.getString(3));
//                tvprov.setText("Provinsi: " + rs.getString(4));
//                tvkota.setText("Kota: " + rs.getString(5));
//                tvkeca.setText("Kecamatan: " +rs.getString(6));
                etharga.setText(rs.getString(7));
                sellerID = rs.getString(9);
                etkontak.setText(rs.getString(10));

//                tvopsi.setText("Opsi: " +rs.getString(11));
                if(rs.getString(11).equals("Jual")){
                    jual.setChecked(true);
                    sewa.setChecked(false);
                }
                if(rs.getString(11).equals("Sewa")){
                    jual.setChecked(false);
                    sewa.setChecked(true);
                }
                etpanjang.setText(rs.getString(12));
                etlebar.setText(rs.getString(13));
                tvluastanah.setVisibility(View.VISIBLE);
                etluastanah.setVisibility(View.VISIBLE);
                etluastanah.setText(rs.getString(14));

                if(rs.getString(15).equals("1")){
                    shm.setChecked(true);
                }
                if(rs.getString(15).equals("2")){
                    shgb.setChecked(true);
                }
                if(rs.getString(15).equals("3")){
                    stg.setChecked(true);
                }
                if(rs.getString(15).equals("4")){
                    sbp.setChecked(true);
                }
                if(rs.getString(15).equals("5")){
                    ajb.setChecked(true);
                }
                if(rs.getString(15).equals("6")){
                    ppjb.setChecked(true);
                }
                etdetail.setText(rs.getString(16));

                datatanah1.add(datatanah);
            }


        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    public void provspinner() {
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

            ArrayAdapter<String> array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);

            provspin.setAdapter(array);
            provspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tempprov = provspin.getSelectedItem().toString();
//                    int posprov = provspin.getSelectedItemPosition();
//                    SharedPreferences prefprov = getActivity().getSharedPreferences("nama", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editprov = prefprov.edit();
//                    editprov.putInt("positionprov", posprov);
//                    editprov.commit();
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

            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);

            kotaspin.setAdapter(array);
//            SharedPreferences prefkota = getActivity().getSharedPreferences("regname", Context.MODE_PRIVATE);
//            int positkota = prefkota.getInt("positionkota",-1);
//            if(positkota != -1){
//                kotaspin.setSelection(positkota);
//            }
            kotaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tempkota = kotaspin.getSelectedItem().toString();
//                    int poskota = kotaspin.getSelectedItemPosition();
//                    SharedPreferences prefkota = getActivity().getSharedPreferences("regname", 0);
//                    SharedPreferences.Editor editkota = prefkota.edit();
//                    editkota.putInt("positionkota", poskota);
//                    editkota.commit();
                    kecaspinner();
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

            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);

            kecaspin.setAdapter(array);
//            SharedPreferences prefkeca = getActivity().getSharedPreferences("disname", Context.MODE_PRIVATE);
//            int positkeca = prefkeca.getInt("positionkeca",-1);
//            if(positkeca != -1){
//                kecaspin.setSelection(positkeca);
//            }
            kecaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tempkeca = kecaspin.getSelectedItem().toString();
                    kecadata = new String(tempkeca);
//                    int poskeca = kecaspin.getSelectedItemPosition();
//                    SharedPreferences prefkeca = getActivity().getSharedPreferences("disname", 0);
//                    SharedPreferences.Editor editkeca = prefkeca.edit();
//                    editkeca.putInt("positionkeca", poskeca);
//                    editkeca.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch (Exception ex){

        }

    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server+"/" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
        }

        return connection;
    }
}