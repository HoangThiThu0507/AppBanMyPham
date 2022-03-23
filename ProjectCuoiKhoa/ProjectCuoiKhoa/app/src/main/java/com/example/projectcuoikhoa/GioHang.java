package com.example.projectcuoikhoa;

import java.io.Serializable;

public class GioHang implements Serializable {
    int id;
    int price;
    String name;
    String img;
    int soLuong;

    public GioHang(int id, int price, String name, String img, int soLuong) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.img = img;
        this.soLuong = soLuong;
    }

    public GioHang() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
