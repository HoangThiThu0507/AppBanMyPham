package com.example.projectcuoikhoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchAdapter extends ArrayAdapter<Product> {

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<Product> listSearchProduct;
    public boolean abc = true;

    public ProductSearchAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        listSearchProduct= new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_productname,parent,false);
        }

        TextView tvSearchName = convertView.findViewById(R.id.tvSearchProductName);
        Product product = getItem(position);
        tvSearchName.setText(product.getName());
        return convertView;
    }
    @NonNull
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Product> listSuggest = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    listSuggest.addAll(listSearchProduct);
                }
                else {
                    String filter = constraint.toString().toLowerCase().trim();
                    for (Product p : listSearchProduct){
                        if (p.getName().toLowerCase().contains(filter)){
                            listSuggest.add(p);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listSuggest;
                filterResults.count = listSuggest.size();
                if(listSuggest.size() ==0 ){
                    Toast.makeText(getContext(), "khong tim thay", Toast.LENGTH_SHORT).show();
                    abc = false;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll( (List<Product>) results.values);

                notifyDataSetInvalidated();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ( (Product) resultValue).getName();
            }
        };
    }
}
