package com.example.way2findhome;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.way2findhome.ui.notifications.NotificationsFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class login extends AppCompatActivity {

    EditText etEmail, etPass;
    Button btnLogin;
    TextView btnRegis;
    Connection con;
    CheckBox rememberMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        etEmail = findViewById(R.id.edtEmailAddress);
        etPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegis = findViewById(R.id.btnRegis);

        rememberMe=findViewById(R.id.saveLoginCheckBox);
        sharedPreferences=getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String mail=sharedPreferences.getString("email","");
        String passwords=sharedPreferences.getString("passowrd","");

        etEmail.setText(mail);
        etPass.setText(passwords);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rememberMe.isChecked()){
                    editor.putString("email",etEmail.getText().toString());
                    editor.putString("passowrd",etPass.getText().toString());
                    editor.commit();
                }else{
                    editor.putString("email","");
                    editor.putString("passowrd","");
                    editor.commit();
                }
                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Cannot be empty!");
                    etEmail.requestFocus();
                }
                if(etPass.getText().toString().isEmpty()){
                    etPass.setError("Cannot be empty!");
                    etPass.requestFocus();
                }
                if(!etEmail.getText().toString().isEmpty() && !etPass.getText().toString().isEmpty()){
                    new login.checkLogin().execute("");
                }


            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
                finish();
            }
        });
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
        alertDialogBuilder.setTitle("Keluar Aplikasi");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk Keluar!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        finish();
                        System.exit(0);
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

    public class checkLogin extends AsyncTask<String, String, String>{

        String z = null;
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {

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
                        Toast.makeText(login.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String emailPass = etEmail.getText().toString();
                    String passPass = etPass.getText().toString();
                    String sql = "SELECT * FROM userApp WHERE email = '" + etEmail.getText() + "' AND pass = '" + etPass.getText() + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(login.this, "Login Success", Toast.LENGTH_LONG).show();
                                Intent main = new Intent(login.this, MainPage.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("Email", emailPass);
                                bundle.putString("Pass", passPass);

                                main.putExtras(bundle);

                                startActivity(main);
                            }
                        });
                        z = "Success";


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(login.this, "Check email or password", Toast.LENGTH_LONG).show();
                            }
                        });

                        etEmail.setText("");
                        etEmail.setText("");
                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
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
