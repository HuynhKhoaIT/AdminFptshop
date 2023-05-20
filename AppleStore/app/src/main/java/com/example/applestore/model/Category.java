package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    @SerializedName("maDM")
    int maDM;
    @SerializedName("tenDM")
    String tenDM;
    @SerializedName("anhdm")
    String anhdm;
    @SerializedName("listSanPham")
    private ArrayList<Product> listSanPham;

    public Category() {
    }

    public Category(int maDM, String tenDM, String anhdm, ArrayList<Product> listSanPham) {
        this.maDM = maDM;
        this.tenDM = tenDM;
        this.anhdm = anhdm;
        this.listSanPham = listSanPham;
    }
    public Category(String anhdm, String tenDM) {
        this.anhdm = anhdm;
        this.tenDM = tenDM;
    }

    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getAnhdm() {
        return anhdm;
    }

    public void setAnhdm(String anhdm) {
        this.anhdm = anhdm;
    }

    public ArrayList<Product> getListSanPham() {
        return listSanPham;
    }

    public void setListSanPham(ArrayList<Product> listSanPham) {
        this.listSanPham = listSanPham;
    }
}
