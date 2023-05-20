package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductPicture implements Serializable {
    @SerializedName("maAnhSP")
    int maAnhSP;
    @SerializedName("sanPham1")
    private Product sanPham1;
    @SerializedName("anh")
    String anh;

    public ProductPicture() {
    }

    public ProductPicture(int maAnhSP, Product sanPham1, String anh) {
        this.maAnhSP = maAnhSP;
        this.sanPham1 = sanPham1;
        this.anh = anh;
    }

    public int getMaAnhSP() {
        return maAnhSP;
    }

    public void setMaAnhSP(int maAnhSP) {
        this.maAnhSP = maAnhSP;
    }

    public Product getSanPham1() {
        return sanPham1;
    }

    public void setSanPham1(Product sanPham1) {
        this.sanPham1 = sanPham1;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
