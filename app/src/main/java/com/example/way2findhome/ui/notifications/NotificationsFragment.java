package com.example.way2findhome.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.way2findhome.AboutUs;
import com.example.way2findhome.ConnectionHelper;
import com.example.way2findhome.Favorite;
import com.example.way2findhome.Help;
import com.example.way2findhome.MainActivity;
import com.example.way2findhome.MainPage;
import com.example.way2findhome.Pemasaran;
import com.example.way2findhome.R;
import com.example.way2findhome.list;
import com.example.way2findhome.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment{

    ListView ListMenu;
    TextView uid, uname, umail;
    public String email, pass, userid, username;
    Connection connect;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle!=null){
            email = bundle.getString("Email");
            pass = bundle.getString("Pass");

            uid = (TextView)root.findViewById(R.id.userid);
            uname = (TextView)root.findViewById(R.id.username);
            umail = (TextView)root.findViewById(R.id.email);

            ProfileCatcher();
            uid  .setText(userid);
            uname.setText(username);
            umail.setText(email);


        }

        ListMenu = (ListView)root.findViewById(R.id.accMenu);
        final String MenuList[] = new String[] {"Properti", "Bantuan", "Tentang Froperty"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listcustom, R.id.textView, MenuList);
        ListMenu.setAdapter(arrayAdapter);
        ListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent1 = new Intent(getActivity(), Pemasaran.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("UID", userid);
                    intent1.putExtras(bundle2);
                    startActivity(intent1);
                }
                if(position == 1){
                    Intent intent3 = new Intent(getActivity(), Help.class);
                    startActivity(intent3);
                }
                if(position == 2){
                    Intent intent4 = new Intent(getActivity(), AboutUs.class);
                    startActivity(intent4);
                }
            }
        });

        return root;
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

}