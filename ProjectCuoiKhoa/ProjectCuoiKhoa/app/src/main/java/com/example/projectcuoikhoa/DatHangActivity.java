package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatHangActivity extends AppCompatActivity {
    TextView tvName, tvPhoneNumber, tvAddress, tvTongTien;
    TextView tvTongTienHang, tvDatHang;
    ListView lv_Product_DH;
   // DatHangActivity datHangActivity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    long TongTien_1, TongTienHang_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        tvName = findViewById(R.id.tvName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvTongTien = findViewById(R.id.tvTongTien);
        lv_Product_DH = findViewById(R.id.lvProduct_DH);
        tvTongTienHang = findViewById(R.id.tvTongTienHang);
        tvDatHang = findViewById(R.id.tvDatHang);
        tvName.setText(MainActivity.ThongTinCaNhan.getName());
        tvPhoneNumber.setText(MainActivity.ThongTinCaNhan.getPhonenumber());
        tvAddress.setText(MainActivity.ThongTinCaNhan.getAddress());
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        if(MainActivity.mangMuaNgay.size() >0){
            DecimalFormat formatPrice = new DecimalFormat("###,###,###");
            AdapterDatHang adapterProduct = new AdapterDatHang(getApplicationContext(), MainActivity.mangMuaNgay);
            lv_Product_DH.setAdapter(adapterProduct);
            long tongtien = MainActivity.mangMuaNgay.get(0).getSoLuong()*MainActivity.mangMuaNgay.get(0).getPrice();
            tvTongTienHang.setText(formatPrice.format(tongtien) + "đ");
            long tong = tongtien+25000;
            tvTongTien.setText(tong +"đ");
            TongTien_1 = tong;
            TongTienHang_1 = tongtien;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
            tvDatHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = df.format(Calendar.getInstance().getTime());
                    DonDangGiao donDangGiao = new DonDangGiao(MainActivity.ThongTinCaNhan,MainActivity.mangMuaNgay,TongTien_1, TongTienHang_1, date);
                    databaseReference.child("DonDangGiao").child(user.getUid()).child(date).setValue(donDangGiao);
                    databaseReference.child("Apriori").child("DH"+ (MainActivity.mangApriori.size()+1)).setValue(donDangGiao);
                    MainActivity.mangMuaNgay.clear();
                    Intent intent = new Intent(getBaseContext(), DanhSachDonDangGiao.class);
                    startActivity(intent);
                }
            });
        }
        else if(MainActivity.mangGioHang.size() > 0) {
            AdapterDatHang adapterProduct = new AdapterDatHang(getApplicationContext(), MainActivity.mangGioHang);
            lv_Product_DH.setAdapter(adapterProduct);
            tong();
            tvDatHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    luuDon();
                    MainActivity.mangGioHang.clear();
                    MainActivity.mangMuaNgay.clear();
                    databaseReference.child("GioHang").child(user.getUid()).removeValue();
                    Intent intent = new Intent(getBaseContext(), DanhSachDonDangGiao.class);
                    startActivity(intent);
                    finish();
                    GioHangActivity.setGioHang();
                    GioHangActivity.SoLuongGioHang();
                }
            });
        }
    }
    private void luuDon(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());
        //Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
        DonDangGiao donDangGiao = new DonDangGiao(MainActivity.ThongTinCaNhan,MainActivity.mangGioHang,TongTien_1, TongTienHang_1, date);
        databaseReference.child("DonDangGiao").child(user.getUid()).child(date).setValue(donDangGiao);
        databaseReference.child("Apriori").child("DH"+ (MainActivity.mangApriori.size()+1)).setValue(donDangGiao);
    }
    public void tong() {
        DecimalFormat formatPrice = new DecimalFormat("###,###,###");
        long tongtien = 0;
        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
            tongtien += (MainActivity.mangGioHang.get(i).getPrice() * MainActivity.mangGioHang.get(i).getSoLuong());
        }
        tvTongTienHang.setText(formatPrice.format(tongtien) + "đ");
        long tong = tongtien+25000;
        TongTien_1 = tong;
        TongTienHang_1 = tongtien;
        tvTongTien.setText(tong +"đ");
    }
}