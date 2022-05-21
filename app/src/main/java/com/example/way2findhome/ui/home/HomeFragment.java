package com.example.way2findhome.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.way2findhome.ConnectionHelper;
import com.example.way2findhome.Pemasaran;
import com.example.way2findhome.R;
import com.example.way2findhome.ViewPagerAdapter;
import com.example.way2findhome.detail;
import com.example.way2findhome.list;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;

    ViewPager mViewPager;
    int[] images = {R.drawable.banner1, R.drawable.b2, R.drawable.b3};
    ViewPagerAdapter mViewPagerAdapter;
    TextView uname, txtanah, txapart, txrumah, txhargatanah, txhargarumah, txhargaapart, txluastanah, txluasapart, txluasrumah;
    public String email, pass, userid, username;
    Connection connect;
    EditText search;
    ImageView ivtanah, ivapart, ivrumah;
    Button tanahbtn, apartbtn, rumahbtn, viewtanah, viewrumah, viewapart;
    public String gettanahid, getrumahid, getapartid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mViewPager = (ViewPager) root.findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(this.getActivity(), images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 4-1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle!=null){
            email = bundle.getString("Email");
            pass = bundle.getString("Pass");

            uname = (TextView)root.findViewById(R.id.txan);

            ProfileCatcher();
            uname.setText("Hai, " + username);

        }
        search = root.findViewById(R.id.searchEditText);
        ivtanah = root.findViewById(R.id.itanah);
        ivapart = root.findViewById(R.id.iapart);
        ivrumah = root.findViewById(R.id.irumah);
        tanahbtn = root.findViewById(R.id.hmtanah);
        apartbtn = root.findViewById(R.id.hmapart);
        rumahbtn = root.findViewById(R.id.hmrumah);
        String tanah = "listTanah";
        String tanahimg = "tanahimg";
        String tanahid = "tanahID";
        String apart = "listApart";
        String apartimg = "apartimg";
        String apartid = "apartID";
        String rumah = "listRumah";
        String rumahimg = "rumahimg";
        String rumahid = "rumahID";
        String tn = "tanah";
        String an = "apart";
        String rn = "rumah";

        txtanah = root.findViewById(R.id.txtitletanah);
        txhargatanah = root.findViewById(R.id.txhargatanah);
        txluastanah = root.findViewById(R.id.txluastanah);
        txapart = root.findViewById(R.id.txtitleapart);
        txhargaapart = root.findViewById(R.id.txhargaapart);
        txluasapart = root.findViewById(R.id.txluasapart);
        txrumah = root.findViewById(R.id.txtitlerumah);
        txhargarumah = root.findViewById(R.id.txhargarumah);
        txluasrumah = root.findViewById(R.id.txluasrumah);
        viewtanah = root.findViewById(R.id.viewtanah);
        viewrumah = root.findViewById(R.id.viewrumah);
        viewapart = root.findViewById(R.id.viewapart);

        tanahget();
        rumahget();
        apartget();

        viewtanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), detail.class);
                Bundle bundle = new Bundle();
                bundle.putString("cate", "Tanah");
                bundle.putString("clause", "tanahID");
                bundle.putString("ID", gettanahid);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        viewrumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), detail.class);
                Bundle bundle = new Bundle();
                bundle.putString("cate", "Rumah");
                bundle.putString("clause", "rumahID");
                bundle.putString("ID", getrumahid);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        viewapart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), detail.class);
                Bundle bundle = new Bundle();
                bundle.putString("cate", "Apart");
                bundle.putString("clause", "apartID");
                bundle.putString("ID", getapartid);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        ivtanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intanah = new Intent(getActivity(), list.class);
                Bundle bundle = new Bundle();
                bundle.putString("list", tanah);
                bundle.putString("img", tanahimg);
                bundle.putString("id", tanahid);
                bundle.putString("n", tn);
                intanah.putExtras(bundle);
                startActivity(intanah);
            }
        });

        tanahbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intanah = new Intent(getActivity(), list.class);
                Bundle bundle = new Bundle();
                bundle.putString("list", tanah);
                bundle.putString("img", tanahimg);
                bundle.putString("id", tanahid);
                bundle.putString("n", tn);
                intanah.putExtras(bundle);
                startActivity(intanah);
            }
        });

        ivapart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inapart = new Intent(getActivity(), list.class);
                Bundle bundle = new Bundle();
                bundle.putString("list", apart);
                bundle.putString("img", apartimg);
                bundle.putString("id", apartid);
                bundle.putString("n", an);
                inapart.putExtras(bundle);
                startActivity(inapart);
            }
        });
        apartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inapart = new Intent(getActivity(), list.class);
                Bundle bundle = new Bundle();
                bundle.putString("list", apart);
                bundle.putString("img", apartimg);
                bundle.putString("id", apartid);
                bundle.putString("n", an);
                inapart.putExtras(bundle);
                startActivity(inapart);
            }
        });

        ivrumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inrumah = new Intent(getActivity(), list.class);
                Bundle bundle = new Bundle();
                bundle.putString("list", rumah);
                bundle.putString("img", rumahimg);
                bundle.putString("id", rumahid);
                bundle.putString("n", rn);
                inrumah.putExtras(bundle);
                startActivity(inrumah);
            }
        });
        rumahbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inrumah = new Intent(getActivity(), list.class);
                Bundle bundle = new Bundle();
                bundle.putString("list", rumah);
                bundle.putString("img", rumahimg);
                bundle.putString("id", rumahid);
                bundle.putString("n", rn);
                inrumah.putExtras(bundle);
                startActivity(inrumah);
            }
        });

        return root;
    }

    public void tanahget() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "Select Top 1 * from listTanah order by NEWID()";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while(rs.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();

                txtanah.setText(rs.getString(2));
                txhargatanah.setText("Rp " + rs.getString(7));
                txluastanah.setText(rs.getString(14)  + " (m²)");
                gettanahid = rs.getString(1);
                datanum.put("tanahid", rs.getString(1));
                data1.add(datanum);
            }

        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    public void rumahget() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "Select Top 1 * from listRumah order by NEWID()";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while(rs.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();

                txrumah.setText(rs.getString(2));
                txhargarumah.setText("Rp " + rs.getString(7));
                txluasrumah.setText(rs.getString(14)  + " (m²)");
                getrumahid = rs.getString(1);
                datanum.put("rumahid", rs.getString(1));
                data1.add(datanum);
            }

        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
        }
    }

    public void apartget() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "Select Top 1 * from listApart order by NEWID()";
            PreparedStatement stat = connect.prepareStatement(query);
            ResultSet rs = stat.executeQuery();

            ArrayList<Bitmap> data0 = new ArrayList<Bitmap>();
            List<Map<String, Object>> data1 = null;
            data1 = new ArrayList<Map<String, Object>>();

            while(rs.next()){
                Map<String, Object> datanum = new HashMap<String, Object>();

                txapart.setText(rs.getString(2));
                txhargaapart.setText("Rp " + rs.getString(7));
                txluasapart.setText(rs.getString(14)  + " (m²)");
                getapartid = rs.getString(1);
                datanum.put("apartid", rs.getString(1));
                data1.add(datanum);
            }

        } catch (Exception e) {
            Log.e("SQL Error : ", e.getMessage());
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
}