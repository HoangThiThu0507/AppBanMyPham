package com.example.projectcuoikhoa;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment {
    Context context;
    ImageView imgDuongThe, imgAvatar, imgChat, imgNuocHoa, imgTrangDiem, imgDuongToc, imgChamSocMat, imgSon;
    static AutoCompleteTextView tvSearch;
    TextView iconSearch, tvSanPhamMoi;
    static  TextView tvSoLuongGioHang;
    ImageView imgGioHang;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imgDuongThe = view.findViewById(R.id.imgDuongThe);
        tvSearch = view.findViewById(R.id.tvSearchProduct);
        iconSearch = view.findViewById(R.id.iconSearch);
        imgAvatar = view.findViewById(R.id.avatar);
        imgChat = view.findViewById(R.id.imgChat);
        imgGioHang = view.findViewById(R.id.imgGioHang);
        imgNuocHoa = view.findViewById(R.id.imgNuocHoa);
        imgTrangDiem = view.findViewById(R.id.imgTrangDiem);
        imgDuongToc = view.findViewById(R.id.imgDuongToc);
        imgChamSocMat = view.findViewById(R.id.imgChamSocMat);
        imgSon = view.findViewById(R.id.imgSon);
        tvSanPhamMoi = view.findViewById(R.id.tvSanPhamMoi);
        tvSoLuongGioHang = view.findViewById(R.id.tvSoLuongGioHang_home);
        Account();
        Giohang();
        new ReadJSONObject().execute("https://demo6088585.mockable.io/APIDemo");
        gettvSearch();
        Search();
        Chat();
        //Toast.makeText(getContext(),gioHangList.size()+"",Toast.LENGTH_SHORT).show();
        SoLuongGiohang();
        imgDuongThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fragmentMain, ProductListFragment.newInstance()).commit();
            }
        });
        imgNuocHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, NuocHoaFragment.newInstance()).commit();
            }
        });
        imgTrangDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, TrangDiemFragment.newInstance()).commit();
            }
        });
        imgChamSocMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, ChamSocMatFragment.newInstance()).commit();
            }
        });
        imgDuongToc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, DuongTocFragment.newInstance()).commit();
            }
        });
        imgSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, SonFragment.newInstance()).commit();
            }
        });
        tvSanPhamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, HangMoiVeFragment.newInstance()).commit();

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void Giohang() {
        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                if(user == null){
                    Toast.makeText(getContext(),"Đăng nhập xem giỏ hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void SoLuongGiohang(){
        int SoLuong = 0;
        for(int i=0;i<MainActivity.mangGioHang.size();i++){
            SoLuong += MainActivity.mangGioHang.get(i).getSoLuong();
        }
        tvSoLuongGioHang.setText(SoLuong+"");
    }
    private void Chat(){
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Account() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AccountActivity.class);
                startActivity(intent);
            }
        });
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
                List<Product> products= new ArrayList<>();
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
                    product = new Product(price, name, img, description, brand, origin, id, type);
                    products.add(product);
                }
                ProductSearchAdapter productSearchAdapter = new ProductSearchAdapter(getContext(),R.layout.item_search_productname, products);

                tvSearch.setAdapter(productSearchAdapter);
                tvSearch.setWidth(LinearLayout.LayoutParams.MATCH_PARENT - 20);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void Search(){
        iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, SearchFragment.newInstance()).commit();
            }
        });
    }
    public static Editable gettvSearch(){
        return tvSearch.getText();
    }
}
