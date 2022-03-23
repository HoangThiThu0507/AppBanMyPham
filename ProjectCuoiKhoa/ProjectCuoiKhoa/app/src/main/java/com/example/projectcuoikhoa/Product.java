package com.example.projectcuoikhoa;

import java.io.Serializable;

public class Product implements Serializable{
    int price;
    String name;
    String img;
    String desciption;
    String brand;
    String origin;
    int id;
    String type;

    public Product(String name) {
        this.name = name;
    }

    public Product(int price, String name, String img, String desciption, String brand, String origin, int id, String type) {
        this.price = price;
        this.name = name;
        this.img = img;
        this.desciption = desciption;
        this.brand = brand;
        this.origin = origin;
        this.id = id;
        this.type = type;
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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}