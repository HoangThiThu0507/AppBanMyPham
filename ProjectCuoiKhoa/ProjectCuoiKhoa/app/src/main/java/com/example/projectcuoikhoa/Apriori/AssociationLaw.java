package com.example.projectcuoikhoa.Apriori;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssociationLaw {
    String dsCacPhanTu = "";

    public AssociationLaw(String[] ds) {
        for (String s : ds) {
            dsCacPhanTu += s;
        }
    }

    private List<String> conVoiNPhanTu(String str, int n) {
        List<String> ds = new LinkedList<>();
        for (int start = 0; start < str.length(); start += n) {
            int end = Math.min(start + n, str.length());
            String sublist = str.substring(start, end);
            ds.add(sublist);
        }

        return ds;
    }

    private ArrayList<String> permutation() {
        return permutation(dsCacPhanTu);
    }

    private ArrayList<String> permutation(String s) {
        ArrayList<String> res = new ArrayList<String>();
        if (s.length() == 1) {
            res.add(s);
        } else if (s.length() > 1) {
            int lastIndex = s.length() - 1;
            // Find out the last character
            String last = s.substring(lastIndex);
            // Rest of the string
            String rest = s.substring(0, lastIndex);
            // Perform permutation on the rest string and
            // merge with the last character
            res = merge(permutation(rest), last);
        }
        return res;
    }

    private ArrayList<String> merge(ArrayList<String> list, String c) {
        ArrayList<String> res = new ArrayList<>();
        // Loop through all the string in the list
        for (String s : list) {
            // For each string, insert the last character to all possible
            // positions
            // and add them to the new list
            for (int i = 0; i <= s.length(); ++i) {
                String ps = new StringBuffer(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }

    private Set<String> chuoiCon() {
        Set<String> set = new LinkedHashSet<>();
        for (int i = 1; i <= dsCacPhanTu.length(); i++) {
            ArrayList<String> permutation = permutation();
            for (String s : permutation) {
                List<String> strings = conVoiNPhanTu(s, i);
                set.addAll(strings);
            }
        }
        return set;
    }

    public static boolean coTonTai(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            if (s2.contains(s1.charAt(i) + "")) {
                return true;
            }
        }
        for (int i = 0; i < s2.length(); i++) {
            if (s1.contains(s2.charAt(i) + "")) {
                return true;
            }
        }
        return false;
    }

    private String loaiBo1PhanTu(String nguon, String canXoa) {
        nguon = nguon.replace(canXoa, "");
        return nguon;
    }

    public Map<String, Set<String>> result() {
        Map<String, Set<String>> result = new LinkedHashMap<>();
        Set<String> set = chuoiCon();
        Set<String> keyDaThem = new LinkedHashSet<>();
        List<String> collect = new ArrayList<>(set);
        for (int i = 0; i < collect.size(); i++) {
            for (int j = 0; j < collect.size(); j++) {
                String s1 = collect.get(i);
                String s2 = collect.get(j);
                s2 = loaiBo1PhanTu(s2, s1);
                if (!coTonTai(s1, s2) && !s1.equals(s2) && !s1.equals("") && !s2.equals("")
                        && s1.length() + s2.length() == dsCacPhanTu.length()) {
                    Set<String> s = null;
                    if (result.containsKey(s1)) {
                        s = result.get(s1);
                        if (!daTonTai(s, s2)) {
                            s.add(s2);
                        }
                    } else {
                        s = new LinkedHashSet<>();

                    }
                    if (!daTonTai(keyDaThem, s1)) {
                        keyDaThem.add(s1);
                        result.put(s1, s);
                    }
                }
            }
        }

        return result;
    }

    public static boolean daTonTai /* �? t?n t?i trong set hay ch�a */(Set<String> set, String s) {
        for (String str : set) {
            if (hashCodeNew(str) == hashCodeNew(s)) {
                return true;
            }
        }
        return false;
    }

    public static int hashCodeNew(String s) {
        int re = 0;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            re += c;
        }
        return re;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> finish() {
        List<String> strings = new LinkedList<>();
        result().forEach((k, v) -> {
            v.forEach(s -> {
                strings.add(k + "-->" + s);
            });
        });
        return strings;
    }
    // customize by me
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Set<Law> getLawGenerate() {
        Set<Law> setLaw = new HashSet<>();
//		Map<Set<Integer>, Set<Integer>> map = new HashMap<>();
        result().forEach((k, v) -> {
            Set<Integer> set1 = new HashSet<>();
            for (int i = 0; i < k.length(); i++) {
                set1.add(Integer.parseInt(k.charAt(i) + ""));
            }
            Set<Integer> set2 = new HashSet<>();
            for (String s : v) {
                for (int i = 0; i < s.length(); i++) {
                    set2.add(Integer.parseInt(s.charAt(i) + ""));
                }
            }

            Law law = new Law(set1,set2,0);
            setLaw.add(law);

//			map.put(set1, set2);
        });
        return setLaw;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static void main(String[] args) {
//        AssociationLaw cach1 = new AssociationLaw(new String[] { "1", "2","3"});
//        System.out.println(cach1.finish());
//
//        Set<Law > set = cach1.getLawGenerate();
//        for(Law l : set){
//            System.out.println(l.print());
//        }
//    }
}
