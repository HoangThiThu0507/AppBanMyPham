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

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterLichSuMuaHang extends BaseAdapter {
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    Context context;
    List<DonDangGiao> LichSuMuaHangist;

    public AdapterLichSuMuaHang(Context context, List<DonDangGiao> LichSuMuaHangist) {
        this.context = context;
        this.LichSuMuaHangist = LichSuMuaHangist;
    }

    @Override
    public int getCount() {
        if(LichSuMuaHangist != null) {
            return LichSuMuaHangist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return LichSuMuaHangist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_lich_su_mua_hang, parent,false);
        TextView tvName,tvSoLuong, tvPrice, tvTongTien, tvTongSoLuong;
        ImageView imgPicture;
        LinearLayout llProduct_CTLSMH;
        tvName = view.findViewById(R.id.tvNameProduct_LSMH);
        tvPrice = view.findViewById(R.id.tvPrice_LSMH);
        tvSoLuong = view.findViewById(R.id.tvSoLuong_LSMH);
        imgPicture = view.findViewById(R.id.imgPictureProduct_LSMH);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        tvTongSoLuong = view.findViewById(R.id.tvTongSoLuong);
        llProduct_CTLSMH = view.findViewById(R.id.lvProduct_CTLSMH);
        DonDangGiao don = LichSuMuaHangist.get(position);
        int soluong = 0;
        for(int i=0;i<don.getGioHangList().size();i++){
            soluong += don.getGioHangList().get(i).getSoLuong();
        }
        tvName.setText(don.getGioHangList().get(0).getName());
        tvPrice.setText(formatPrice.format(don.getGioHangList().get(0).getPrice())+"");
        Glide.with(context)
                .load(don.getGioHangList().get(0).getImg())
                .into(imgPicture);
        tvSoLuong.setText(don.getGioHangList().get(0).getSoLuong()+"");
        tvTongTien.setText(formatPrice.format(don.TongTien)+"");
        tvTongSoLuong.setText(soluong+"");

        llProduct_CTLSMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProductDetail(LichSuMuaHangist.get(position));
            }
        });
        return view;
    }

    private void onClickProductDetail(DonDangGiao donDangGiao) {
        Intent intent = new Intent(context, ChiTietLichSuMuaHangActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ChiTietLichSuMuaHang", donDangGiao);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
