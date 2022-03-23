package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    static GridView lv_Product_GH;
    TextView tvMuaHang;
    static TextView tvGioHangTrong;
    static TextView tvSoLuong;
    static TextView tvTongTien;
    CheckBox cbChonTatCa;
    AdapterGioHang adapterGioHang;
    ImageView imgBack;
    //int soluongmoi = Integer.parseInt(tvSoLuong.getText().toString());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        //tvThongBao = findViewById(R.id.tvThongBao);
        lv_Product_GH = findViewById(R.id.lvProduct_GH);
        tvMuaHang = findViewById(R.id.tvMuaHang);
        cbChonTatCa = findViewById(R.id.cbChonTatCa);
        tvTongTien= findViewById(R.id.tvTongTien);
        imgBack = findViewById(R.id.imgBack_GH);
        tvSoLuong = findViewById(R.id.tvSoLuongGioHang);
        tvGioHangTrong = findViewById(R.id.tvGioHAngTrong);
        //tvSoLuong = findViewById(R.id.tvSoLuong_GH);
        //if (MainActivity.mangGioHang.size() > 0){
        //    AdapterGioHang adapterProduct = new AdapterGioHang(getApplicationContext(), MainActivity.mangGioHang);
        //    lv_Product_GH.setAdapter(adapterProduct);
        //}
        if(MainActivity.mangGioHang == null){
            MainActivity.mangGioHang = new ArrayList<>();
        }
        setGioHang();
        tongtien();
        Back();
        SoLuongGioHang();
        MuaHang();
    }
    public static void setGioHang(){
        if (MainActivity.mangGioHang.size() > 0){
            AdapterGioHang adapterProduct = new AdapterGioHang(lv_Product_GH.getContext(), MainActivity.mangGioHang);
            lv_Product_GH.setAdapter(adapterProduct);
            tvGioHangTrong.setVisibility(View.GONE);
        }
        else{
            lv_Product_GH.setVisibility(View.GONE);
            tvGioHangTrong.setVisibility(View.VISIBLE);
        }
    }
    public static void SoLuongGioHang(){
        int SoLuong_GH =0 ;
        for(int i=0;i<MainActivity.mangGioHang.size();i++){
            SoLuong_GH += MainActivity.mangGioHang.get(i).getSoLuong();
        }
        tvSoLuong.setText(SoLuong_GH+"");
    }
    public static void tongtien(){
        DecimalFormat formatPrice = new DecimalFormat("###,###,###");
        long tongtien =0;
        for(int i=0 ;i<MainActivity.mangGioHang.size(); i++){
            tongtien += (MainActivity.mangGioHang.get(i).getPrice()*MainActivity.mangGioHang.get(i).getSoLuong());
        }
        tvTongTien.setText(formatPrice.format(tongtien) +"đ");
    }
    private void Back(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void MuaHang(){
        tvMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.ThongTinCaNhan == null){
                    Toast.makeText(getApplicationContext(),"Vui lòng điền thông tin tại mục thông tin nhận hàng ",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getBaseContext(),DatHangActivity.class);
                startActivity(intent);
            }
        });
    }
}