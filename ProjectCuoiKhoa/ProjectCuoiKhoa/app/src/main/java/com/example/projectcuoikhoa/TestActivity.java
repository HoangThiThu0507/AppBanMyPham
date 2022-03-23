package com.example.projectcuoikhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestActivity extends AppCompatActivity {
    public static List<DonDangGiao> mangApriori;
    TextView tvTest;
    int minSupport = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvTest = findViewById(R.id.tvTest);
        if(mangApriori == null){
            mangApriori = new ArrayList<>();
        }
        getApriori();
        //abc(MainActivity.mangApriori);
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
                    abc(mangApriori);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return mangApriori;
    }
    public void abc(List<DonDangGiao> mangApriori){
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> abc = new ArrayList<>();
        abc.add(2);
        abc.add(3);
        for (int i=0;i <mangApriori.size(); i++){
            List<Integer> ItemSet = new ArrayList<>();
            for(int j = 0; j< mangApriori.get(i).GioHangList.size(); j++){
                ItemSet.add(mangApriori.get(i).GioHangList.get(j).id);
            }
            map.put("ST00"+(i+1), ItemSet);
        }

        for (Map.Entry<String, List<Integer>> mapEntry : map.entrySet()){
            Log.i("Thu", mapEntry.getKey()+ "  "+ mapEntry.getValue().toString());
        }
        Set<ItemSet> itemSets = getCandidate(map);
        for(ItemSet it : itemSets ){
            Log.i("Thu2", it.itemSet.toString() +"--"+ it.support  );
        }
        getC2(itemSets, 2);
    }
    public Set<ItemSet> getCandidate(Map<String, List<Integer>> map){
        Set<ItemSet> setLi = new HashSet<>();
        for(List<Integer> listInt: map.values()){
            for(Integer i : listInt){
                int count = count(map, i);
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

    public int count(Map<String, List<Integer>> map, int i ){
        int count = 0;
        for(List<Integer> setLi: map.values()){
            for(Integer k : setLi){
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
    public Set<ItemSet> apriori_gen(Map<String, List<Integer>> map){
        Set<ItemSet> setRes = new HashSet<>();
        Set<ItemSet> setCan = getCandidate(map);
        Set<ItemSet> setL = new HashSet<>();
        setL.addAll(setCan);
        int size = 2;
        Set<Set<Integer>> setGen = new HashSet<>();
        while ((setGen = getC2(setCan, size)) != null){
            Set<ItemSet> setItem = new HashSet<>();
            for(Set<Integer> item: setGen){
                if(has_subnet(item)){
                    int count = countFrequent(item);
                    setItem.add(new ItemSet(item, count));
                    
                }
            }
            setRes = checkMinSupport(setItem);
            setL.addAll(setRes);
            for(ItemSet it :  setL){
                Log.i("C3", it.itemSet.toString()+"=="+it.support);
            }
            size++;

        }
        return setRes;
    }

    private int countFrequent(Set<Integer> item) {
//        int count = 0;
//        for(ItemSet it: )
        return 0;
    }

    private boolean has_subnet(Set<Integer> item) {
        return false;
    }
}