package com.example.way2findhome;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class register extends AppCompatActivity {
    int countt = 0;
    EditText etEmail, etPass, etName, etconpass;
    Button btnRegis;
    TextView btnLogin;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        etName = findViewById(R.id.edtName);
        etEmail = findViewById(R.id.edtEmailAddress);
        etPass = findViewById(R.id.edtPassword);
        etconpass = findViewById(R.id.edtconpass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegis = findViewById(R.id.btnRegis);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this,login.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countt = 0;
                String pass = etPass.getText().toString();
                String cpass = etconpass.getText().toString();
                String name = etName.getEditableText().toString().trim();
                String email = etEmail.getEditableText().toString().trim();
                String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+[.]+[Cc]+[Oo]+[Mm]";

                if(name.isEmpty()){
                    etName.setError("Cannot be empty!");
                    etName.requestFocus();
                }
                if(!pass.equals(cpass)){
                    etconpass.setError("Check password again!");
                    etconpass.requestFocus();
                }

                if(email.isEmpty()){
                    etEmail.setError("Cannot be empty!");
                    etEmail.requestFocus();
                }

                if(pass.isEmpty()){
                    etPass.setError("Cannot be empty!");
                    etPass.requestFocus();
                }

                if(cpass.isEmpty()){
                    etconpass.setError("Cannot be empty!");
                    etconpass.requestFocus();
                }

                if(!email.matches(regex)){
                    etEmail.setError("Email must include @ .com");
                    etEmail.requestFocus();
                }
                if(pass.equals(cpass) && email.matches(regex) && !name.isEmpty() && !pass.isEmpty() && !cpass.isEmpty()){
                    new register.checkRegis().execute("");
                }

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

    public class checkRegis extends AsyncTask<String, String, String> {

        String z = null;
        String email, name, pass, txtcount;
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            email = etEmail.getText().toString();
            name = etName.getText().toString();
            pass = etPass.getText().toString();

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
                        Toast.makeText(register.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String count = "select * from userApp";
                    PreparedStatement stc =con.prepareStatement(count);
                    ResultSet rsc = stc.executeQuery();

                    while (rsc.next()){
                        countt++;
                    }

                    countt = countt+1;
                    txtcount = Integer.toString(countt);
                    String query = "INSERT INTO userApp VALUES ('" + txtcount + "','" + name + "','" + email + "','" + pass + "');";
                    String register = query;
                    Statement stat = con.createStatement();
                    stat.executeUpdate(register);

                    String sql = "select * FROM userApp WHERE email = '" + etEmail.getText() + "' AND pass = '" + etPass.getText() + "' ";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(register.this, "Register Success", Toast.LENGTH_LONG).show();
                                Intent main2 = new Intent(register.this, login.class);
                                startActivity(main2);
                            }
                        });
                        z = "Success";


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(register.this, "Register Fail", Toast.LENGTH_LONG).show();
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