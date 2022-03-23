package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThongTinNhanHangActivity extends AppCompatActivity {
    TextView tvName, tvPhoneNumber, tvAddress, tvSua, tvThem;
    ImageView imgBackTTNH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nhan_hang);
        tvName = findViewById(R.id.tvName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvSua = findViewById(R.id.tvSua);
        tvThem = findViewById(R.id.tvThem);
        imgBackTTNH = findViewById(R.id.imgBackTTNH);
        check();
        tvThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),CapNhatThongTinNhanHangActivity.class);
                startActivity(intent);//chuyển sang activity khác
            }
        });
        if(MainActivity.ThongTinCaNhan != null){
            tvName.setText(MainActivity.ThongTinCaNhan.getName());
            tvPhoneNumber.setText(MainActivity.ThongTinCaNhan.getPhonenumber());
            tvAddress.setText(MainActivity.ThongTinCaNhan.getAddress());
        }
        else if(MainActivity.ThongTinCaNhan == null){
            tvName.setVisibility(View.INVISIBLE);
            tvAddress.setVisibility(View.INVISIBLE);
            tvPhoneNumber.setVisibility(View.INVISIBLE);
        }
        tvSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CapNhatThongTinNhanHangActivity.class);
                intent.putExtra("name",MainActivity.ThongTinCaNhan.getName());
                intent.putExtra("address",MainActivity.ThongTinCaNhan.getAddress());
                intent.putExtra("phonenumber", MainActivity.ThongTinCaNhan.getPhonenumber());
                startActivity(intent);
                finish();
            }
        });
        tvThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CapNhatThongTinNhanHangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgBackTTNH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });
    }
    private void check(){
        if(MainActivity.ThongTinCaNhan == null ){
            tvSua.setVisibility(View.GONE);
            tvThem.setVisibility(View.VISIBLE);
            return;
        }
        if(MainActivity.ThongTinCaNhan != null){
            tvSua.setVisibility(View.VISIBLE);
            tvThem.setVisibility(View.GONE);
            return;
        }
    }
}