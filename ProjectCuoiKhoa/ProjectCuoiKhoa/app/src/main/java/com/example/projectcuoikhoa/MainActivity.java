package com.example.projectcuoikhoa;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectcuoikhoa.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    static ActivityMainBinding binding;
    ArrayList<MenuItem> arrayList;
    AdapterMenu adapterMenu;
    public static List<GioHang> mangGioHang;
    public static List<GioHang> mangMuaNgay;
    public static List<DonDangGiao> mangDonDangGiao;
    public static List<DonDangGiao> mangLichSuMuaHang;
    public static List<DonDangGiao> mangApriori;
    FragmentTransaction fragmentTransaction;
    AccountActivity accountActivity = new AccountActivity();
    public static Address ThongTinCaNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        ActionMenu();
        ActionBar();
        login();
        if(mangGioHang == null){
            mangGioHang = new ArrayList<>();
        }
        if(mangDonDangGiao == null){
            mangDonDangGiao = new ArrayList<>();
        }
        if(mangLichSuMuaHang == null){
            mangLichSuMuaHang = new ArrayList<>();
        }
        if(mangMuaNgay == null){
            mangMuaNgay = new ArrayList<>();
        }
        if(mangApriori == null){
            mangApriori = new ArrayList<>();
        }
        getDataProduct();
        getHome();
        showUserInfor();
        SignUp();
        //getThongTinCaNhan();
        getFragment(MainFragment.newInstance());
        getDonDagnGiao();
        getLichSuMuaHang();
        getThongTinCaNhan();
        getApriori();
    }
    private void ActionMenu() {
        arrayList=new ArrayList<>();
        arrayList.add(new MenuItem("TẤT CẢ SẢN PHẨM"));
        arrayList.add(new MenuItem("BODY"));
        arrayList.add(new MenuItem("NƯỚC HOA"));
        arrayList.add(new MenuItem("TRANG ĐIỂM"));
        arrayList.add(new MenuItem("DƯỠNG TÓC"));
        arrayList.add(new MenuItem("CHĂM SÓC MẶT"));
        arrayList.add(new MenuItem("SON"));
        adapterMenu = new AdapterMenu(this, R.layout.item_menu,arrayList);
        binding.lvView.setAdapter(adapterMenu);
        binding.lvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrayList.get(position).itemName.equals("TẤT CẢ SẢN PHẨM")){
                    getFragment(ProductListFragment.newInstance());
                    return;
                }
                if(arrayList.get(position).itemName.equals("BODY")){
                    getFragment(ProductListFragment.newInstance());
                    return;
                }
                if(arrayList.get(position).itemName.equals("NƯỚC HOA")){
                    getFragment(NuocHoaFragment.newInstance());
                    return;
                }
                if(arrayList.get(position).itemName.equals("TRANG ĐIỂM")){
                    getFragment(TrangDiemFragment.newInstance());
                    return;
                }
                if(arrayList.get(position).itemName.equals("DƯỠNG TÓC")){
                    getFragment(DuongTocFragment.newInstance());
                    return;
                }
                if(arrayList.get(position).itemName.equals("CHĂM SÓC MẶT")){
                    getFragment(ChamSocMatFragment.newInstance());
                    return;
                }
                if(arrayList.get(position).itemName.equals("SON")){
                    getFragment(SonFragment.newInstance());
                    return;
                }
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void login(){
        //getFragment(MainFragment.newInstance());
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);//chuyển sang activity khác

            }
        });
    }
    private void SignUp(){
        //getFragment(MainFragment.newInstance());
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent);//chuyển sang activity khác

            }
        });
    }
    private void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentMain, fragment)
                .commit();
    }
    private void getHome(){
        binding.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);//chuyển sang activity khác
            }
        });
    }
    public void showUserInfor(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            binding.llchuadangnhap.setVisibility(View.VISIBLE);
            binding.llDangNhapRoi.setVisibility(View.GONE);
            return;
        }
        //String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        binding.llchuadangnhap.setVisibility(View.GONE);
        binding.llDangNhapRoi.setVisibility(View.VISIBLE);
        binding.tvUserName.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_baseline_person_24).into(binding.imgAvatar);
        //binding.imgAvatar.setImageURI(photoUrl);
    }
    private List<GioHang> getDataProduct(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("GioHang").child(user.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mangGioHang.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        GioHang gioHang = data.getValue(GioHang.class);
                            mangGioHang.add(gioHang);
                    }
                    //Toast.makeText(getApplicationContext(),mangGioHang.size()+"1",Toast.LENGTH_SHORT).show();
                    MainFragment.SoLuongGiohang();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            mangGioHang.clear();
        }
        return mangGioHang;
    }
    private Address getThongTinCaNhan() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("DiaChi").child(user.getUid());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ThongTinCaNhan = new Address();
                        ThongTinCaNhan = snapshot.getValue(Address.class);
                        //Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return ThongTinCaNhan;
    }
    private List<DonDangGiao> getDonDagnGiao(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("DonDangGiao").child(user.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mangDonDangGiao.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DonDangGiao donDangGiao = data.getValue(DonDangGiao.class);
                        mangDonDangGiao.add(donDangGiao);
                    }
                    //Toast.makeText(getApplicationContext(),mangDonDangGiao.size()+"",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            mangDonDangGiao.clear();
        }
        return mangDonDangGiao;
    }
    private List<DonDangGiao> getLichSuMuaHang(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("LichSuMuaHang").child(user.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mangLichSuMuaHang.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DonDangGiao donDangGiao = data.getValue(DonDangGiao.class);
                        mangLichSuMuaHang.add(donDangGiao);
                    }
                    //Toast.makeText(getApplicationContext(),mangLichSuMuaHang.size()+"",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            mangLichSuMuaHang.clear();
        }
        return mangLichSuMuaHang;
    }

    private List<DonDangGiao> getApriori(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Apriori");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mangApriori.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DonDangGiao donDangGiao = data.getValue(DonDangGiao.class);
                        mangApriori.add(donDangGiao);
                    }
//                    Toast.makeText(getApplicationContext(),mangApriori.size()+"",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return mangApriori;
    }
}