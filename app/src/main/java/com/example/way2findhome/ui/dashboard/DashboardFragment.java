package com.example.way2findhome.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.way2findhome.ConnectionHelper;
import com.example.way2findhome.R;
import com.example.way2findhome.login;
import com.example.way2findhome.register;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {
    Spinner provspin;
    Spinner kotaspin;
    Spinner kecaspin;
    Connection connect;
    public String email, pass, userid, username;
    public String tempprov;
    public String tempkota;
    public String tempkeca;
    public String provdata;
    public String kotadata;
    public String kecadata;
    public Bitmap selectedImage;
    public InputStream imageStream;
    ImageView imageView;
    Connection con;
    int countt = 0;
    int opsiID, sertID, airID;
    long hargatemp = 1000000000000L;
    String encodedImage;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    public Bitmap image;
    public byte[] bytesImage;
    public TextView txluastanah, txluasbangunan, txlantai, txbedroom, txbathroom, txlistrik;
    public EditText etnama,etalamat, etluastanah, etluasbangunan, etlantai, etbedroom, etbathroom, etlistrik, etharga, etkontak, etpanjang, etlebar, etdetail;
    public RadioGroup halaman, interior, parkir, air, opsi, sertifikat;
    public Button insert, file;
    public Spinner sprov, skota, skeca;
    public String nama, alamat, provinsi, kota, keca, harga, seller, sellerID, jsopsi, kontak, panjang, lebar, luastanah, luasbangunan, sertif, keterangan, lantai, bedrom, bathrom, inte, hal, park, jsair, listrik;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        RadioGroup list_opsi = root.findViewById(R.id.opsi);
        list_opsi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                etnama = getActivity().findViewById(R.id.etnama);
                etalamat = getActivity().findViewById(R.id.etalamat);
                sprov = getActivity().findViewById(R.id.provspin);
                skota = getActivity().findViewById(R.id.kotaspin);
                skeca = getActivity().findViewById(R.id.kecaspin);
                etharga = getActivity().findViewById(R.id.etharga);
                etkontak = getActivity().findViewById(R.id.etkontak);
                opsi = getActivity().findViewById(R.id.txopsi);
                etpanjang = getActivity().findViewById(R.id.etpanjang);
                etlebar = getActivity().findViewById(R.id.etlebar);
                txluasbangunan = getActivity().findViewById(R.id.txluasbangunan);
                etluasbangunan = getActivity().findViewById(R.id.etluasbangunan);
                txluastanah = getActivity().findViewById(R.id.txluastanah);
                etluastanah = getActivity().findViewById(R.id.etluastanah);
                txlantai = getActivity().findViewById(R.id.txlantai);
                etlantai = getActivity().findViewById(R.id.etlantai);
                txbedroom = getActivity().findViewById(R.id.txbedroom);
                etbedroom = getActivity().findViewById(R.id.etbedroom);
                txbathroom = getActivity().findViewById(R.id.txbathroom);
                etbathroom = getActivity().findViewById(R.id.etbathroom);
                txlistrik = getActivity().findViewById(R.id.txlistrik);
                etlistrik = getActivity().findViewById(R.id.etlistrik);
                halaman = getActivity().findViewById(R.id.halaman);
                interior = getActivity().findViewById(R.id.interior);
                parkir = getActivity().findViewById(R.id.parkir);
                air = getActivity().findViewById(R.id.air);
                etdetail = getActivity().findViewById(R.id.etketerangan);
                sertifikat = getActivity().findViewById(R.id.sertifikat);
                insert = getActivity().findViewById(R.id.btnpasarkan);
                file = getActivity().findViewById(R.id.btnfile);
                imageView = getActivity().findViewById(R.id.imageView);

                switch (id){
                    case R.id.tanah:
//                        Toast.makeText(getActivity().getApplication(), "Tanah", Toast.LENGTH_SHORT).show();
                        txluasbangunan.setVisibility(View.GONE);
                        etluasbangunan.setVisibility(View.GONE);
                        txluastanah.setVisibility(View.VISIBLE);
                        etluastanah.setVisibility(View.VISIBLE);
                        txlantai.setVisibility(View.GONE);
                        etlantai.setVisibility(View.GONE);
                        txbedroom.setVisibility(View.GONE);
                        etbedroom.setVisibility(View.GONE);
                        txbathroom.setVisibility(View.GONE);
                        etbathroom.setVisibility(View.GONE);
                        txlistrik.setVisibility(View.GONE);
                        etlistrik.setVisibility(View.GONE);
                        halaman.setVisibility(View.GONE);
                        interior.setVisibility(View.GONE);
                        parkir.setVisibility(View.GONE);
                        air.setVisibility(View.GONE);

                        Bundle bundle = getActivity().getIntent().getExtras();
                        email = bundle.getString("Email");
                        pass = bundle.getString("Pass");
                        ProfileCatcher();
                        file.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openGallery();
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

                        insert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                countt = 0;

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
                                    new DashboardFragment.insertTanah().execute("");
                                }

                            }
                        });

                        break;
                    case R.id.rumah:
//                        Toast.makeText(getActivity().getApplication(), "Rumah", Toast.LENGTH_SHORT).show();
                        txluasbangunan.setVisibility(View.VISIBLE);
                        etluasbangunan.setVisibility(View.VISIBLE);
                        txluastanah.setVisibility(View.VISIBLE);
                        etluastanah.setVisibility(View.VISIBLE);
                        txlantai.setVisibility(View.VISIBLE);
                        etlantai.setVisibility(View.VISIBLE);
                        txbedroom.setVisibility(View.VISIBLE);
                        etbedroom.setVisibility(View.VISIBLE);
                        txbathroom.setVisibility(View.VISIBLE);
                        etbathroom.setVisibility(View.VISIBLE);
                        txlistrik.setVisibility(View.VISIBLE);
                        etlistrik.setVisibility(View.VISIBLE);
                        halaman.setVisibility(View.VISIBLE);
                        interior.setVisibility(View.VISIBLE);
                        parkir.setVisibility(View.VISIBLE);
                        air.setVisibility(View.VISIBLE);
                        Bundle bundle2 = getActivity().getIntent().getExtras();
                        email = bundle2.getString("Email");
                        pass = bundle2.getString("Pass");
                        ProfileCatcher();
                        file.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openGallery();
                            }
                        });
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
                        insert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countt = 0;
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
                                    new DashboardFragment.insertRumah().execute("");
                                }

                            }
                        });
                        break;
                    case R.id.apartemen:
//                        Toast.makeText(getActivity().getApplication(), "Apartemen", Toast.LENGTH_SHORT).show();
                        txluasbangunan.setVisibility(View.VISIBLE);
                        etluasbangunan.setVisibility(View.VISIBLE);
                        txluastanah.setVisibility(View.GONE);
                        etluastanah.setVisibility(View.GONE);
                        txlantai.setVisibility(View.VISIBLE);
                        etlantai.setVisibility(View.VISIBLE);
                        txbedroom.setVisibility(View.VISIBLE);
                        etbedroom.setVisibility(View.VISIBLE);
                        txbathroom.setVisibility(View.VISIBLE);
                        etbathroom.setVisibility(View.VISIBLE);
                        txlistrik.setVisibility(View.VISIBLE);
                        etlistrik.setVisibility(View.VISIBLE);
                        halaman.setVisibility(View.VISIBLE);
                        interior.setVisibility(View.VISIBLE);
                        parkir.setVisibility(View.GONE);
                        air.setVisibility(View.GONE);
                        Bundle bundle3 = getActivity().getIntent().getExtras();
                        email = bundle3.getString("Email");
                        pass = bundle3.getString("Pass");
                        ProfileCatcher();
                        file.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openGallery();
                            }
                        });
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
                        insert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countt = 0;
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
                                    new DashboardFragment.insertApart().execute("");
                                }

                            }
                        });
                        break;
                }
            }
        });

        provspin = (Spinner)root.findViewById(R.id.provspin);
        kotaspin = (Spinner)root.findViewById(R.id.kotaspin);
        kecaspin = (Spinner)root.findViewById(R.id.kecaspin);
        provspinner();
//        SharedPreferences prefprof = this.getActivity().getSharedPreferences("nama", Context.MODE_PRIVATE);
//        int positprov = prefprof.getInt("positionprov",-1);
//        if(positprov != -1){
//            provspin.setSelection(positprov);
//        }
        return root;
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            imageView.setVisibility(View.VISIBLE);
                            Intent data = result.getData();
                            //                        data.setData(imageUri);
                            imageUri = data.getData();
                            imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            selectedImage = BitmapFactory.decodeStream(imageStream);

//                            Toast.makeText(getActivity(), imageStream.toString(), Toast.LENGTH_LONG).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            imageView.setVisibility(View.GONE);
                        }
                        if(selectedImage != null){
                            imageView.setImageBitmap(selectedImage);
                            image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            bytesImage = byteArrayOutputStream.toByteArray();
                            encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                        }
                    }
                }
            });
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        launchSomeActivity.launch(gallery);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
//            imageUri = data.getData();
//            imageView.setImageURI(imageUri);
//        }
//    }
    public class insertTanah extends AsyncTask<String, String, String> {

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
        seller = username;
        sellerID = userid;
        kontak = etkontak.getText().toString();
        opsiID = opsi.getCheckedRadioButtonId();
        RadioButton ropsi = getActivity().findViewById(opsiID);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String count = "select TOP 1 tanahID from listTanah order by tanahID desc";
                    PreparedStatement stc =con.prepareStatement(count);
                    ResultSet rsc = stc.executeQuery();

                    while (rsc.next()){
                        countt = rsc.getInt(1);
                    }

                    countt = countt+1;
                    txtcount = Integer.toString(countt);

                    String query = "Insert into listTanah values('"+ txtcount + "','" + nama + "','" + alamat + "','" + provinsi + "','" + kota + "','" + keca + "','" + harga + "','" + seller + "','" + sellerID + "','" + kontak + "','" + jsopsi + "','" + panjang + "','" + lebar + "','" + luastanah + "','" +sertif + "','" + keterangan + "');";
                    String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);

                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO tanahimg (tanahID, images) VALUES (?,?)");
                    preparedStatement.setInt(1, countt);
                    preparedStatement.setBytes(2, bytesImage);
                    preparedStatement.execute();

                    String sql = "select * FROM listTanah WHERE tanahID = '" + txtcount + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                                showDialog();
                            }
                        });
                        z = "Success";


                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_LONG).show();
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

    public class insertRumah extends AsyncTask<String, String, String> {

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
            seller = username;
            sellerID = userid;
            kontak = etkontak.getText().toString();
            opsiID = opsi.getCheckedRadioButtonId();
            RadioButton ropsi = getActivity().findViewById(opsiID);
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
            RadioButton rair = getActivity().findViewById(airID);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String count = "select TOP 1 rumahID from listRumah order by rumahID desc";
                    PreparedStatement stc =con.prepareStatement(count);
                    ResultSet rsc = stc.executeQuery();

                    while (rsc.next()){
                        countt = rsc.getInt(1);
                    }

                    countt = countt+1;
                    txtcount = Integer.toString(countt);
                    String query = "Insert into listRumah values('"+ txtcount + "','" + nama + "','" + alamat + "','" + provinsi + "','" + kota + "','" + keca + "','" + harga + "','" + seller + "','" + sellerID + "','" + kontak + "','" + jsopsi + "','" + panjang + "','" + lebar + "','" + luastanah+ "','" +luasbangunan+ "','" +lantai+ "','" +bedrom+ "','" +bathrom+ "','" +inte+ "','" +hal+ "','" +park+ "','" +listrik+ "','" +jsair + "','" +sertif + "','" + keterangan + "');";
                    String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);

                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO rumahimg (rumahID, images) VALUES (?,?)");
                    preparedStatement.setInt(1, countt);
                    preparedStatement.setBytes(2, bytesImage);
                    preparedStatement.execute();

                    String sql = "select * FROM listRumah WHERE rumahID = '" + txtcount + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();

                            }
                        });
                        z = "Success";


                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_LONG).show();
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

    public class insertApart extends AsyncTask<String, String, String> {

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
            seller = username;
            sellerID = userid;
            kontak = etkontak.getText().toString();
            opsiID = opsi.getCheckedRadioButtonId();
            RadioButton ropsi = getActivity().findViewById(opsiID);
            jsopsi = ropsi.getText().toString();
            panjang = etpanjang.getText().toString();
            lebar = etlebar.getText().toString();
            luasbangunan = etluasbangunan.getText().toString();
            lantai = etlantai.getText().toString();
            bedrom = etbedroom.getText().toString();
            bathrom = etbathroom.getText().toString();
            listrik = etlistrik.getText().toString();
            keterangan = etdetail.getText().toString();

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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String count = "select TOP 1 apartID from listApart order by apartID desc";
                    PreparedStatement stc =con.prepareStatement(count);
                    ResultSet rsc = stc.executeQuery();

                    while (rsc.next()){
                        countt = rsc.getInt(1);
                    }

                    countt = countt+1;
                    txtcount = Integer.toString(countt);
                    String query = "Insert into listApart values('"+ txtcount + "','" + nama + "','" + alamat + "','" + provinsi + "','" + kota + "','" + keca + "','" + harga + "','" + seller + "','" + sellerID + "','" + kontak + "','" + jsopsi + "','" + panjang + "','" + lebar + "','" + luasbangunan+ "','" +lantai+ "','" +bedrom+ "','" +bathrom +"','" +inte+ "','" +hal+  "','" +listrik+ "','" +sertif + "','" + keterangan + "');";
                    String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);

                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO apartimg (apartID, images) VALUES (?,?)");
                    preparedStatement.setInt(1, countt);
                    preparedStatement.setBytes(2, bytesImage);
                    preparedStatement.execute();

                    String sql = "select * FROM listApart WHERE apartID = '" + txtcount + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();

                            }
                        });
                        z = "Success";


                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_LONG).show();
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

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title dialog
        alertDialogBuilder.setTitle("Froperty: " + nama);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Berhasil Memasarkan")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
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

            ArrayAdapter<String> array = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, data);

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

            ArrayAdapter array = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, data);

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

            ArrayAdapter array = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, data);

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

    public void ProfileCatcher() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select userID, nama from userApp WHERE email = '" + email + "' AND pass = '" + pass + "' ";

            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();


            if(rs.next()){

                userid = rs.getString(1);
                username = rs.getString(2);
            }

        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
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
