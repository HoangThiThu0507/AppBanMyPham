package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChiTietDonDangGiaoActivity extends AppCompatActivity {
    TextView tvName, tvPhoneNumber, tvAddress, tvTongTien, tvNgayDatHang, tvDaNhanHang;
    TextView tvTongTienHang;
    ListView lv_Product_DH;
    ImageView imgBack;
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdondanggiao);
        tvName = findViewById(R.id.tvName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvTongTien = findViewById(R.id.tvTongTien_DGH);
        lv_Product_DH = findViewById(R.id.lvProduct_DH);
        tvTongTienHang = findViewById(R.id.tvTienHang_DGH);
        tvNgayDatHang = findViewById(R.id.tvNgayDatHang);
        tvDaNhanHang = findViewById(R.id.tvDaNhanHang);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        GetChiTietDonDangGiao("ChiTietDonDangGiao");


    }
    private void GetChiTietDonDangGiao(String t ) {
        Bundle bundle = getIntent().getExtras();
        DonDangGiao donDangGiao=(DonDangGiao) bundle.get("ChiTietDonDangGiao");
        AdapterDatHang adapterDatHang = new AdapterDatHang(getApplicationContext(), donDangGiao.GioHangList);
        lv_Product_DH.setAdapter(adapterDatHang);
        tvTongTien.setText(formatPrice.format(donDangGiao.TongTien)+"đ");
        tvTongTienHang.setText(formatPrice.format(donDangGiao.TongTienHang)+"đ");
        tvNgayDatHang.setText(donDangGiao.NgayTao);
        tvName.setText(donDangGiao.getThongTinNhanHang().getName());
        tvPhoneNumber.setText(donDangGiao.getThongTinNhanHang().getPhonenumber());
        tvAddress.setText(donDangGiao.getThongTinNhanHang().getAddress());

        tvDaNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase;
                DatabaseReference databaseReference;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
                String date = df.format(Calendar.getInstance().getTime());
                databaseReference.child("LichSuMuaHang").child(user.getUid()).child(date).setValue(donDangGiao);
                databaseReference.child("DonDangGiao").child(user.getUid()).child(donDangGiao.getNgayTao()).removeValue();
                Intent intent = new Intent(getBaseContext(), LichSuMuaHangActivity.class);
                startActivity(intent);
                for(int i=0;i<MainActivity.mangDonDangGiao.size(); i++){
                    if(MainActivity.mangDonDangGiao.get(i).getNgayTao().equals(donDangGiao.getNgayTao())){
                        MainActivity.mangDonDangGiao.remove(i);
                        finish();
                        return;
                    }
                }
                Toast.makeText(v.getContext(),MainActivity.mangDonDangGiao.size()+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}