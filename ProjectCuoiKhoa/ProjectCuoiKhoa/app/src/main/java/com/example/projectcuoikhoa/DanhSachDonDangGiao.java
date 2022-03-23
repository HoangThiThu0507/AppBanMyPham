package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;

public class DanhSachDonDangGiao extends AppCompatActivity {
    ListView lvProduct_DDG;
    TextView tvThongBaoDDG;
    ImageView imgThongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_don_dang_giao);
        lvProduct_DDG = findViewById(R.id.lvProduct_DDG);
        tvThongBaoDDG = findViewById(R.id.tvThongBao_DDG);
        imgThongBao = findViewById(R.id.imgThongBao_DSDDG);
        if(MainActivity.mangDonDangGiao.size()>0){
            Collections.reverse(MainActivity.mangDonDangGiao);
            Adapter_Don_Dang_Giao adapter_don_dang_giao = new Adapter_Don_Dang_Giao(getApplicationContext(), MainActivity.mangDonDangGiao);
            lvProduct_DDG.setAdapter(adapter_don_dang_giao);
            return;
        }
        imgThongBao.setVisibility(View.VISIBLE);
        tvThongBaoDDG.setVisibility(View.VISIBLE);
        lvProduct_DDG.setVisibility(View.GONE);
    }
}