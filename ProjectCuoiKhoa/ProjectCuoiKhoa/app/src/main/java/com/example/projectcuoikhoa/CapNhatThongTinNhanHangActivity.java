package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CapNhatThongTinNhanHangActivity extends AppCompatActivity {
    EditText etName, etPhoneNumber, etAddress;
    TextView tvLuu, tvSua;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_thong_tin_nhan_hang);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        tvLuu = findViewById(R.id.tvLuu);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String phonenumber = intent.getStringExtra("phonenumber");
        etName.setText(name);
        etAddress.setText(address);
        etPhoneNumber.setText(phonenumber);
        tvLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClick();
                finish();
            }
        });
    }
    private void OnClick() {
        String name = etName.getText().toString().trim();
        String phonenumber = etPhoneNumber.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        if(TextUtils.isEmpty(name)){
            etName.setError("Chưa nhập họ tên");
            return;
        }
        if(TextUtils.isEmpty(phonenumber)){
            etPhoneNumber.setError("Chưa nhập số điện thoại");
            return;
        }
        if(TextUtils.isEmpty(address)){
            etAddress.setError("Chưa nhập địa chỉ");
            return;
        }
        Address thongtin = new Address(name , phonenumber, address);
        databaseReference.child("DiaChi").child(user.getUid()).setValue(thongtin);
        Intent intent = new Intent(getApplicationContext(), ThongTinNhanHangActivity.class);
        startActivity(intent);
    }
}