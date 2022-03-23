package com.example.projectcuoikhoa;

import java.io.Serializable;

public class Address implements Serializable {
    String name, phonenumber, address;

    public Address(String name, String phonenumber, String address) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    public Address() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
