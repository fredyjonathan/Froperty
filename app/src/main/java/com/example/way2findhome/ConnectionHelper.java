package com.example.way2findhome;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection com;
    public static String uname, pass, ip, port, database;
    @SuppressLint("NewAPI")
    public Connection connectionclass(){
        ip = "192.168.56.1";
        database = "wilayah";
        uname = "FJ";
        pass = "110502";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"+ ip + ":" + port + ";" + "databasename=" + database+ ";user=" + uname + ";password=" + pass + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (Exception ex){
            Log.e("Error ", ex.getMessage());
        }

        return connection;
    }
}
