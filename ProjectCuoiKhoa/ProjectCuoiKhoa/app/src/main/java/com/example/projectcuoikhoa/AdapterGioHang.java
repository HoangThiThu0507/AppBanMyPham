package com.example.projectcuoikhoa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterGioHang extends BaseAdapter {
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    Context context;
    List<GioHang> gioHangList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public AdapterGioHang(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @Override
    public int getCount() {
        if(gioHangList != null) {
            return gioHangList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return gioHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_giohang, parent,false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        TextView tvName, tvTangSL,tvGiamSL,tvSoLuong, tvPrice;
        ImageView imgPicture;
        tvName = view.findViewById(R.id.tvNameProduct_GH);
        tvPrice = view.findViewById(R.id.tvPrice_GH);
        tvTangSL = view.findViewById(R.id.tvTangSL_GH);
        tvGiamSL = view.findViewById(R.id.tvGiamSL_GH);
        tvSoLuong = view.findViewById(R.id.tvSoLuong_GH);
        imgPicture = view.findViewById(R.id.imgPictureProduct_GH);
        GioHang giohang = gioHangList.get(position);
        tvName.setText(giohang.getName());
        tvPrice.setText(formatPrice.format(giohang.getPrice()) +"");
        Glide.with(context)
                .load(giohang.getImg())
                .into(imgPicture);
        tvSoLuong.setText(giohang.getSoLuong()+"");
        tvGiamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (Integer.parseInt(tvSoLuong.getText().toString()) > 1){
                    int soluongmoi = Integer.parseInt(tvSoLuong.getText().toString())-1;
                    tvSoLuong.setText(soluongmoi+"");
                    MainActivity.mangGioHang.get(position).setSoLuong(soluongmoi);
                    databaseReference.child("GioHang").child(user.getUid()).child(giohang.getId()+"").child("soLuong").setValue(soluongmoi);
                    GioHangActivity.tongtien();
                    //GioHangActivity.SoLuongGioHang();
                    return;
                }*/
                if(Integer.parseInt(tvSoLuong.getText().toString()) == 1) {
                    androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("")
                            .setMessage("Bạn có chắc chắc chắn muốn xóa sản phẩm này")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    gioHangList.remove(position);
                                    databaseReference.child("GioHang").child(user.getUid()).child(giohang.getId()+"").removeValue();
                                    notifyDataSetChanged();
                                    GioHangActivity.tongtien();
                                    GioHangActivity.SoLuongGioHang();
                                    //ProductListFragment.SoLuongGioHang();
                                    //ChamSocMatFragment.SoLuongGioHang();
                                    //ProductDetailActivity.SoLuongGioHang();
                                    //SoLuong();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create();
                    alertDialog.show();
                    return;
                }
                int soluongmoi = Integer.parseInt(tvSoLuong.getText().toString())-1;
                tvSoLuong.setText(soluongmoi+"");
                MainActivity.mangGioHang.get(position).setSoLuong(soluongmoi);
                databaseReference.child("GioHang").child(user.getUid()).child(giohang.getId()+"").child("soLuong").setValue(soluongmoi);
                GioHangActivity.tongtien();
                GioHangActivity.SoLuongGioHang();
            }
        });
        tvTangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoi = Integer.parseInt(tvSoLuong.getText().toString()) + 1;
                tvSoLuong.setText(soluongmoi+"");
                MainActivity.mangGioHang.get(position).setSoLuong(soluongmoi);
                notifyDataSetChanged();
                databaseReference.child("GioHang").child(user.getUid()).child(giohang.getId()+"").child("soLuong").setValue(soluongmoi);
                GioHangActivity.tongtien();
                GioHangActivity.SoLuongGioHang();
            }
        });
        return view;
    }
}
