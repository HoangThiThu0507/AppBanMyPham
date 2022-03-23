package com.example.projectcuoikhoa;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class AccountActivity extends AppCompatActivity {
    LinearLayout llChuaDN, llDangNhapRoi, llthongtinkhachhang;
    TextView tvUserName, tvLogOut, tvThongTinNhanHang, tvDonDangGiao, tvLogin, tvSignUp, tvDonDaGiao;
    ImageView imgAvatar, imgBack;
    LinearLayout llDiaChiShop, llTroGiup;
    public final int MY_REQUEST_CODE = 10;

    Uri uri;
    MainActivity mainActivity;
    Bundle savedInstanceState;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                if(intent == null){
                    return;
                }
                uri = intent.getData();
                setUri(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    AccountActivity.this.setBitmapImageView(bitmap);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(uri)
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        showUserInfor();

                                    }
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        llDangNhapRoi = findViewById(R.id.llDangNhapRoi);
        tvUserName = findViewById(R.id.tvUserName);
        imgAvatar = findViewById(R.id.imgAvatar);
        tvLogOut = findViewById(R.id.tvLogOut);
        llthongtinkhachhang = findViewById(R.id.llthongtinhtaikhoan);
        tvThongTinNhanHang = findViewById(R.id.tvThongtinnhanhang);
        tvDonDangGiao = findViewById(R.id.tvDonDangGiao);
        llChuaDN = findViewById(R.id.llchuadangnhap);
        tvLogin = findViewById(R.id.tvLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        imgBack = findViewById(R.id.imgBack);
        tvDonDangGiao = findViewById(R.id.tvDonDangGiao);
        tvDonDaGiao = findViewById((R.id.tvDonDaGiao));
        llDiaChiShop = findViewById(R.id.llDiaChiShop);
        llTroGiup= findViewById(R.id.llTroGiup);
        showUserInfor();
        LogOut();
        setAvatar();
        ChuyenHuong();
        //updateAvatar();
    }


    private void ChuyenHuong() {
        tvThongTinNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ThongTinNhanHangActivity.class);
                startActivity(intent);
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        tvDonDaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),LichSuMuaHangActivity.class);
                startActivity(intent);
            }
        });
        tvDonDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),DanhSachDonDangGiao.class);
                startActivity(intent);
            }
        });
        llDiaChiShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MapsActivity.class);
                startActivity(intent);
            }
        });
        llTroGiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),CauHoiTroGiupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showUserInfor(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            llChuaDN.setVisibility(View.VISIBLE);
            llDangNhapRoi.setVisibility(View.GONE);
            llthongtinkhachhang.setVisibility(View.INVISIBLE);
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        llDangNhapRoi.setVisibility(View.VISIBLE);
        llChuaDN.setVisibility(View.GONE);
        tvUserName.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_baseline_person_24).into(imgAvatar);
    }
    private void LogOut(){
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);//chuyển sang activity khác
            }
        });
    }
    private void setAvatar(){
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }

    /*private void updateAvatar() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                //.setDisplayName("Jane Q. User")
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showUserInfor();
                        }
                    }
                });
    }*/
    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(AccountActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openGallery();
        }
        else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {// cho phép sử dụng permission
                openGallery();
            }
        }
    }
    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
        //startActivityForResult(intent, MY_REQUEST_CODE);
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        imgAvatar.setImageBitmap(bitmapImageView);
    }
}