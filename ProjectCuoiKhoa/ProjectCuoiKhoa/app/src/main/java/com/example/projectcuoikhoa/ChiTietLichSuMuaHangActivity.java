package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ChiTietLichSuMuaHangActivity extends AppCompatActivity {
    TextView tvName, tvPhoneNumber, tvAddress, tvTongTien, tvNgayDatHang, tvDaNhanHang;
    TextView tvTongTienHang;
    GridView lv_Product;
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_su_mua_hang);
        tvName = findViewById(R.id.tvName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvTongTien = findViewById(R.id.tvTongTien_LSMH);
        lv_Product = findViewById(R.id.lvProduct_CTLSMH);
        tvTongTienHang = findViewById(R.id.tvTienHang_LSMH);
        tvNgayDatHang = findViewById(R.id.tvNgayDatHang);
        tvDaNhanHang = findViewById(R.id.tvDaNhanHang);
        GetChiTietDonDangGiao("ChiTietLichSuMuaHang");

    }
    private void GetChiTietDonDangGiao(String t ) {
        Bundle bundle = getIntent().getExtras();
        DonDangGiao donDangGiao = (DonDangGiao) bundle.get("ChiTietLichSuMuaHang");
        AdapterDatHang adapterDatHang = new AdapterDatHang(getApplicationContext(), donDangGiao.GioHangList);
        lv_Product.setAdapter(adapterDatHang);
        tvTongTien.setText(formatPrice.format(donDangGiao.TongTien) + "đ");
        tvTongTienHang.setText(formatPrice.format(donDangGiao.TongTienHang) + "đ");
        tvNgayDatHang.setText(donDangGiao.NgayTao);
        tvName.setText(donDangGiao.getThongTinNhanHang().getName());
        tvPhoneNumber.setText(donDangGiao.getThongTinNhanHang().getPhonenumber());
        tvAddress.setText(donDangGiao.getThongTinNhanHang().getAddress());
    }
}