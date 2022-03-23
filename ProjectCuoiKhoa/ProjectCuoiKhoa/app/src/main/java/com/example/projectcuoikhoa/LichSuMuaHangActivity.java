package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

public class LichSuMuaHangActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ListView lvLichSuMuaHang;
    TextView tvThongBao;
    ImageView imgThongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_hang);
        lvLichSuMuaHang = findViewById(R.id.lvProduct_LSMH);
        tvThongBao = findViewById(R.id.tvThongBao);
        imgThongBao = findViewById(R.id.imgThongbao);
        if(MainActivity.mangLichSuMuaHang.size()>0){
            Collections.reverse(MainActivity.mangLichSuMuaHang);
            AdapterLichSuMuaHang adapterLichSuMuaHang = new AdapterLichSuMuaHang(getApplicationContext(), MainActivity.mangLichSuMuaHang);
            lvLichSuMuaHang.setAdapter(adapterLichSuMuaHang);
        }
        else {
            imgThongBao.setVisibility(View.VISIBLE);
            tvThongBao.setVisibility(View.VISIBLE);
            lvLichSuMuaHang.setVisibility(View.GONE);
        }
    }
    /*private void GetChiTietDonDangGiao(String t ) {
        Bundle bundle = getIntent().getExtras();
        DonDangGiao donDangGiao=(DonDangGiao) bundle.get("LichSuMuaHang");
        AdapterLichSuMuaHang adapterDatHang = new AdapterLichSuMuaHang(getApplicationContext(), donDangGiao.GioHangList);
        lvLichSuMuaHang.setAdapter(adapterDatHang);
    }*/

}