package com.example.projectcuoikhoa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChamSocMatFragment extends Fragment {
    ImageView imgBack,imgGioHang;
    GridView lvProduct;
    String str;
    static TextView tvSoLuongGioHang;
    AdapterProduct adapterProduct;
    public static ChamSocMatFragment newInstance() {
        Bundle args = new Bundle();
        ChamSocMatFragment fragment = new ChamSocMatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_list, container, false);
        lvProduct=view.findViewById(R.id.lvProduct);
        imgBack=view.findViewById(R.id.imgBack);
        imgGioHang=view.findViewById(R.id.imgGioHang);
        tvSoLuongGioHang=view.findViewById(R.id.tvSoLuongGioHang);
        SoLuongGioHang();
        new ReadJSONObject().execute("https://demo6088585.mockable.io/APIDemo");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, MainFragment.newInstance()).commit();
            }
        });
        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public static void SoLuongGioHang(){
        int SoLuong = 0;
        for(int i=0;i<MainActivity.mangGioHang.size();i++){
            SoLuong += MainActivity.mangGioHang.get(i).getSoLuong();
        }
        tvSoLuongGioHang.setText(SoLuong+"");
    }
    private class ReadJSONObject extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                Product product;
                str = s;
                List<Product> products = new ArrayList<>();
                JSONArray array = new JSONArray((s));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    int price = jsonObject.getInt("price");
                    String img = jsonObject.getString("picture_url");
                    String description = jsonObject.getString("description");
                    String brand = jsonObject.getString("brand");
                    String origin = jsonObject.getString("origin");
                    int id = jsonObject.getInt("id");
                    String type = jsonObject.getString("type");
                    product = new Product(price, name, img, description,brand,origin,id,type);
                    if(type.equals("chăm sóc mặt")){
                        products.add(product);
                    }
                }
                adapterProduct = new AdapterProduct(getContext(), products);
                lvProduct.setAdapter(adapterProduct);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
