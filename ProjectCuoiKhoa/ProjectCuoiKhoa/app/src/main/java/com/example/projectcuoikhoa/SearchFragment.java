package com.example.projectcuoikhoa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchFragment extends Fragment {
    ImageView imgBack;
    GridView lvProduct;
    Product product2;
    TextView v;
    String str;
    Editable data ;
    AdapterProduct adapterProduct;
    List<Product> productList;
    ProductListFragment productListFragment;
    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_list, container, false);
        lvProduct=view.findViewById(R.id.lvProduct);
        imgBack=view.findViewById(R.id.imgBack);
        new ReadJSONObject().execute("https://demo6088585.mockable.io/APIDemo");
        data = MainFragment.gettvSearch();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentMain, MainFragment.newInstance()).commit();
            }
        });
        return view;
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
                    String filter = data.toString().toLowerCase().trim();
                    if(name.toLowerCase().contains(filter)) {
                        products.add(product);
                    }
                }
                AdapterProduct adapterProduct = new AdapterProduct(getContext(), products);
                lvProduct.setAdapter(adapterProduct);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
