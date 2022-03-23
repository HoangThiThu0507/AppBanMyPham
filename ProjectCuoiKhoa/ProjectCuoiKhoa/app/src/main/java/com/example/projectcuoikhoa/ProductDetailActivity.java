package com.example.projectcuoikhoa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectcuoikhoa.Apriori.AssociationLaw;
import com.example.projectcuoikhoa.Apriori.Law;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvDescription, tvBrand, tvOrigin, tvTangSL, tvGiamSL, tvXemThem;
    static TextView tvSoLuongGioHang;
    TextView tvSoLuong, tvMuaNgay;
    ImageView img, imgShoppingcart_detail, imgBack;
    int SoLuong =1;
    int id = 0;
    //String id = "";
    int price =0;
    String name="";
    String GiaTriImg="";
    LinearLayout llThemGiohang;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int width = LinearLayout.LayoutParams.MATCH_PARENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    Map<String, ItemSet> mangApriori= new HashMap<>();
    Set<ItemSet> setL ;
    int minSupport = 2;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        tvName = findViewById(R.id.tvNameProductDetail);
        tvPrice = findViewById(R.id.tvPriceDetail);
        tvDescription = findViewById(R.id.tvProductDetail);
        img = findViewById(R.id.img);
        tvBrand = findViewById(R.id.tvBrand);
        tvOrigin = findViewById(R.id.tvOrigin);
        tvTangSL = findViewById(R.id.tvTangSL);
        tvGiamSL = findViewById(R.id.tvGiamSL);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        imgShoppingcart_detail = findViewById(R.id.imgShoppingcart_detail);
        llThemGiohang = findViewById(R.id.llThemGioHang);
        imgBack = findViewById(R.id.imgBackDetail);
        tvSoLuongGioHang = findViewById(R.id.tvSoLuongGioHang);
        tvMuaNgay =findViewById(R.id.tvMuaNgay);
        tvXemThem = findViewById(R.id.tvXemThem);
        //GetInformation();
        //Bundle bundle = getIntent().getExtras();
        GetInformation("ProductDetail");
        TangSL();
        GiamSL();
        Shopping_cart();
        EventButton();
        SoLuongGioHang();
        OnClickMuaNgay();
        abc2(MainActivity.mangApriori);
        abc();
        apriori_gen();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        tvXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDescription.setLayoutParams(lp);
            }
        });

        getLawGen();
    }

    private void GetInformation(String t ) {
        Bundle bundle = getIntent().getExtras();
        Product product=(Product)bundle.get("ProductDetail");
        Glide.with(getApplication())
                .load(product.getImg())
                .into(img);
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice()+"đ");
        tvDescription.setText(product.getDesciption());
        tvBrand.setText(product.getBrand());
        tvOrigin.setText(product.getOrigin()+"");
        id = product.getId();
        price = product.getPrice();
        name = product.getName();
        GiaTriImg = product.getImg();
    }
    private void TangSL(){
        tvTangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoLuong = SoLuong+1;
                tvSoLuong.setText(SoLuong+"");
                return;
            }
        });
    }
    private void GiamSL(){
        tvGiamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SoLuong >= 2){
                    SoLuong = SoLuong-1;
                    tvSoLuong.setText(SoLuong+"");
                    return;
                }
            }
        });
    }
    private void Shopping_cart(){
        imgShoppingcart_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                if(user == null){
                    Toast.makeText(getApplication(),"Đăng nhập xem giỏ hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplication(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }
    public static void SoLuongGioHang(){
        int SoLuong_GH =0 ;
        for(int i=0;i<MainActivity.mangGioHang.size();i++){
            SoLuong_GH += MainActivity.mangGioHang.get(i).getSoLuong();
        }
        tvSoLuongGioHang.setText(SoLuong_GH+"");
    }
    private void EventButton() {
        llThemGiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                if(user == null){
                    Toast.makeText(getApplication(),"Đăng nhập để thêm giỏ hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MainActivity.mangGioHang.size()>0){
                    boolean exists = false;
                    int sl = Integer.parseInt(tvSoLuong.getText().toString());
                    for(int i=0;i<MainActivity.mangGioHang.size();i++){
                        if(MainActivity.mangGioHang.get(i).getId() == (id)){
                            MainActivity.mangGioHang.get(i).setSoLuong(MainActivity.mangGioHang.get(i).getSoLuong()+ sl);
                            databaseReference.child("GioHang").child(user.getUid()).child(MainActivity.mangGioHang.get(i).getId()+"").setValue(MainActivity.mangGioHang.get(i));
                            exists = true;
                            }
                        }
                        if(exists == false){//k tìm đc sản phẩm trùng add sản phẩm mới
                            //long GiaMoi = SoLuong * price;
                            MainActivity.mangGioHang.add(new GioHang( id, price, name, GiaTriImg, sl));
                            GioHang gioHang = new GioHang(id, price, name, GiaTriImg, Integer.parseInt(tvSoLuong.getText().toString()));
                            //MainActivity.mangGioHang.add(gioHang);
                            databaseReference.child("GioHang").child(user.getUid()+"").child(gioHang.getId()+"").setValue(gioHang);
                        }
                    }
                else {
                    long GiaMoi = SoLuong * price;
                    MainActivity.mangGioHang.add(new GioHang( id, price, name, GiaTriImg, SoLuong));
                    GioHang gioHang = new GioHang(id, price, name, GiaTriImg, Integer.parseInt(tvSoLuong.getText().toString()));
                    //MainActivity.mangGioHang.add(gioHang);
                    databaseReference.child("GioHang").child(user.getUid()+"").child(gioHang.getId()+"").setValue(gioHang);
                }
                SoLuongGioHang();
                //GioHang gioHang = new GioHang( id, price, name, GiaTriImg, Integer.parseInt(tvSoLuong.getText().toString()));
                Toast.makeText(getApplication(),"Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void OnClickMuaNgay() {
        tvMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                MainActivity.mangMuaNgay.clear();
                if(user == null){
                    Toast.makeText(getApplication(),"Đăng nhập để mua hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                    long GiaMoi = SoLuong * price;
                    MainActivity.mangMuaNgay.add(new GioHang( id, price, name, GiaTriImg, SoLuong));
                Intent intent = new Intent(getApplicationContext(),DatHangActivity.class);
                startActivity(intent);
            }
        });
    }


    public Map<String, ItemSet> abc2(List<DonDangGiao> mangDonHang){
//        Map<String, ItemSet> map = new HashMap<>();
        for (int i=0;i <MainActivity.mangApriori.size(); i++){
            ItemSet itemSet = new ItemSet();
            Set<Integer> itemSet2= new HashSet<>();
            for(int j = 0; j< mangDonHang.get(i).GioHangList.size(); j++){
                itemSet2.add(mangDonHang.get(i).GioHangList.get(j).id );
                itemSet = new ItemSet(itemSet2, 0);
            }
            mangApriori.put("ST00"+(i+1), itemSet);
        }

        for (Map.Entry<String, ItemSet> mapEntry : mangApriori.entrySet()){
            Log.i("Thu", mapEntry.getKey()+ "  "+ mapEntry.getValue().itemSet.toString());
        }
        return mangApriori;
    }

    public void abc(){
        setL = getCandidate();
        for(ItemSet it : setL ){
            Log.i("Thu2", it.itemSet.toString() +"--"+ it.support  );
        }
        getC2(setL, 2);
    }
    public Set<ItemSet> getCandidate(){
        Set<ItemSet> setLi = new HashSet<>();
        for(ItemSet listInt: mangApriori.values()){
            for(Integer i : listInt.itemSet){
                int count = count(mangApriori, i);
                Set<Integer> setItem= new HashSet<>();
                setItem.add(i);
                ItemSet itemSet = new ItemSet(setItem, count);
                if(!checkContain(itemSet, setLi)){
                    setLi.add(itemSet);
                }
            }
        }
        setLi = checkMinSupport(setLi);
        return setLi;
    }

    private Set<ItemSet> checkMinSupport(Set<ItemSet> setLi) {
        Set<ItemSet> setItemSet = new HashSet<>();
        for(ItemSet itemSet : setLi){
            if(itemSet.support >= 2){
                setItemSet.add(itemSet);
            }
        }
        return setItemSet;
    }

    private boolean checkContain(ItemSet itemSet, Set<ItemSet> setLi) {
        for(ItemSet it : setLi){
            if(it.itemSet.equals(itemSet.itemSet)) return true;
        }
        return false;
    }

    public int count(Map<String, ItemSet> map, int i ){
        int count = 0;
        for(ItemSet setLi: map.values()){
            for(Integer k : setLi.itemSet){
                if(k == i)
                    count ++;
            }
        }
        return count;
    }

    private Set<Set<Integer>> getC2(Set<ItemSet> set, int size){
        Set<Set<Integer>> setRes = new HashSet<>();
        int arr[] = new int[set.size()];
        int count = 0;
        for(ItemSet it :  set){
            for(Integer i : it.itemSet){
                arr[count] = i;
                Log.i("C2", arr[count]+"==");
            }
            count ++;
        }
        int n = arr.length;

        for(int i =0; i< (1 << n); i++){
            Set<Integer> setA = new HashSet<>();
            for(int j = 0; j<n; j++){
                if((i & (1 << j)) >0){
                    setA.add(arr[j]);
                }
            }
            if(setA.size() == size){
                setRes.add(setA);
            }
        }
        for(Set<Integer> it :  setRes){
            Log.i("C2", it.toString());
        }

        return (setRes.isEmpty()) ? null : setRes;
    }
    public Set<ItemSet> apriori_gen(){
        Log.i("C3", "Bước phát sinh lần thứ 1");
        for(ItemSet it :  setL){
            Log.i("L31", it.itemSet.toString()+".."+it.support);
        }
        Set<ItemSet> setRes = new HashSet<>();
        Set<ItemSet> setCan = getCandidate();
        Set<ItemSet> setL = new HashSet<>();
        setL.addAll(setCan);
        int size = 2;
        Set<Set<Integer>> setGen = new HashSet<>();
        while ((setGen = getC2(setCan, size)) != null){
            Log.i("C3", "Bước phát sinh lần thứ "+ size);
            Set<ItemSet> setItem = new HashSet<>();
            for(Set<Integer> item: setGen){
                if(has_subnet(item,setL)){
                    int count = countFrequent(item);
                    setItem.add(new ItemSet(item, count));

                }
            }
            setRes = checkMinSupport(setItem);
            setL.addAll(setRes);
            for(ItemSet it :  setL){
                Log.i("L3"+size, it.itemSet.toString()+".."+it.support);
            }
            size++;

        }
        return setRes;
    }

    private int countFrequent(Set<Integer> item) {
        int count = 0;
        Map<String, ItemSet> datdaaSet = abc2(MainActivity.mangApriori);
        for(ItemSet it: datdaaSet.values()){
            if(it.itemSet.containsAll(item)) count++;
        }
        return count;
    }

    private boolean has_subnet(Set<Integer> item,Set<ItemSet> setL) {
        Set<Set<Integer>> setSubnet = getSubnet(item);
        int count =0;
        for(Set<Integer> it : setSubnet){
            if(checkSubnet(it, setL)){
                count++;
            }
        }
        return (count == setSubnet.size())?true:false;
    }

    private boolean checkSubnet(Set<Integer> it,Set<ItemSet> setL) {
        for(ItemSet itemSet : setL){
            if(itemSet.itemSet.equals(it)) return true;
        }
        return false;
    }

    public Set<Set<Integer>> getSubnet(Set<Integer> set){
        Set<Set<Integer>> setRes = new HashSet<>();
        int arr[] = new int[set.size()];
        int count = 0;

            for(Integer i : set){
                arr[count] = i;
                count ++;
//                Log.i("C2", arr[count]+"==");
            }
        int n = arr.length;

        for(int i =0; i< (1 << n); i++){
            Set<Integer> setA = new HashSet<>();
            for(int j = 0; j<n; j++){
                if((i & (1 << j)) >0){
                    setA.add(arr[j]);
                }
            }
            if(setA.size() == set.size() -1){
                setRes.add(setA);
            }
        }
        return (setRes.isEmpty()) ? null : setRes;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Set<Law> getLawGen() {
        Set<Law> setLaw = new HashSet<>();
//		Map<Set<Integer>, Set<Integer>> map = new HashMap<>();
        for(ItemSet it: setL){
            if(it.itemSet.size() >= 2){
                String[] arr = new String[it.itemSet.size()];
                int count = 0;
                for(Integer i : it.itemSet){
                    arr[count] = i+"";
                    count++;
                }
                AssociationLaw as = new AssociationLaw(arr);
                Set<Law> setLawTmp = as.getLawGenerate();
                for(Law l : setLawTmp){
                    int countSetA = countFrequent(l.getSetA());
                    Set<Integer> setB = new HashSet<>();
                    setB.addAll(l.getSetA());
                    setB.addAll(l.getSetB());
                    int countSetB = countFrequent(setB);
                    double min_conf = getMin_conf(countSetA, countSetB);

                    l.setMin_conf(min_conf);
                    setLaw.add(l);
                }

            }
        }
        for(Law l :  setLaw){
            Log.i("law", l.print());
        }
        Log.i("law", setLaw.size()+"");
        return setLaw;
    }

    private double getMin_conf(int countSetA, int countSetB){
        double res = (double) countSetB/countSetA;
        return res;
    }
}