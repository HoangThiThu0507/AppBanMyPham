package com.example.projectcuoikhoa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.List;

public class Adapter_Don_Dang_Giao extends BaseAdapter {
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    Context context;
    List<DonDangGiao> donDangGiaoList;
    public Adapter_Don_Dang_Giao(Context context, List<DonDangGiao> donDangGiaoList) {
        this.context = context;
        this.donDangGiaoList = donDangGiaoList;
    }

    @Override
    public int getCount() {
        if(donDangGiaoList != null) {
            return donDangGiaoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return donDangGiaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_don_dang_giao, parent,false);
        TextView tvName,tvSoLuong, tvPrice, tvTongTien, tvTongSoLuong, tvDaNhanHang;
        ImageView imgPicture;
        tvName = view.findViewById(R.id.tvNameProduct_DDG);
        tvPrice = view.findViewById(R.id.tvPrice_DDG);
        tvSoLuong = view.findViewById(R.id.tvSoLuong_DDG);
        imgPicture = view.findViewById(R.id.imgPictureProduct_DDG);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        tvTongSoLuong = view.findViewById(R.id.tvTongSoLuong);
        tvDaNhanHang = view.findViewById(R.id.tvDaNhanHang);
        LinearLayout llChiTietDonDangGiao= view.findViewById(R.id.llChiTietDonDangGiao);
        DonDangGiao donDangGiao = donDangGiaoList.get(position);
        int soluong = 0;
        for(int i=0;i<donDangGiao.getGioHangList().size();i++){
            soluong += donDangGiao.getGioHangList().get(i).getSoLuong();
        }
        tvName.setText(donDangGiao.getGioHangList().get(0).getName());
        tvPrice.setText(formatPrice.format(donDangGiao.getGioHangList().get(0).getPrice())+"");
        Glide.with(context)
                .load(donDangGiao.getGioHangList().get(0).getImg())
                .into(imgPicture);
        tvSoLuong.setText(donDangGiao.getGioHangList().get(0).getSoLuong()+"");
        tvTongTien.setText(formatPrice.format(donDangGiao.TongTien)+"");
        tvTongSoLuong.setText(soluong+"");
        llChiTietDonDangGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProductDetail(donDangGiaoList.get(position));
            }
        });
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
                databaseReference.child("LichSuMuaHang").child(user.getUid()).child(date).setValue(donDangGiaoList.get(position));
                onClickDaNhanHang(donDangGiaoList.get(position));
                databaseReference.child("DonDangGiao").child(user.getUid()).child(donDangGiaoList.get(position).getNgayTao()).removeValue();
                donDangGiaoList.remove(position);
                notifyDataSetChanged();
                //Toast.makeText(v.getContext(),donDangGiaoList.get(position).getNgayTao(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void onClickProductDetail(DonDangGiao donDangGiao) {
        Intent intent = new Intent(context, ChiTietDonDangGiaoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ChiTietDonDangGiao", donDangGiao);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    private void onClickDaNhanHang(DonDangGiao donDangGiao) {
        Intent intent = new Intent(context, LichSuMuaHangActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LichSuMuaHang", donDangGiao);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
