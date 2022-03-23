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

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterProduct extends BaseAdapter {
    private Context context;
    List<Product> products;
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    public AdapterProduct(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_productlist, parent,false);
        TextView tvName = view.findViewById(R.id.tvNameProduct);
        ImageView imgPicture = view.findViewById(R.id.imgPicture);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        tvName.setText(products.get(position).getName());
        LinearLayout layout_item = view.findViewById(R.id.layout_item);
        //tvPrice.setText(products.get(position).getPrice()+"");
        tvPrice.setText(formatPrice.format(products.get(position).getPrice())+"");
        Glide.with(context)
                .load(products.get(position).getImg())
                .into(imgPicture);
        layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),products.get(position).getBrand(),Toast.LENGTH_SHORT).show();
                onClickProductDetail(products.get(position));
            }

        });
        return view;
    }
    private void onClickProductDetail(Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProductDetail", product);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

