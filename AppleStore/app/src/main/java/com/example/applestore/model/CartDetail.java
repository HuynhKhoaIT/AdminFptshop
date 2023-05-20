package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartDetail implements Serializable {
    @SerializedName("maCTGH")
    int maCTGH;
    @SerializedName("gioHang")
    private Cart gioHang;
    @SerializedName("sanPham3")
    private Product sanPham3;
    @SerializedName("soLuong")
    int soLuong;
    @SerializedName("idDelete")
    int idDelete;

    public CartDetail() {
    }

    public CartDetail(int maCTGH, Cart gioHang, Product sanPham3, int soLuong, int idDelete) {
        this.maCTGH = maCTGH;
        this.gioHang = gioHang;
        this.sanPham3 = sanPham3;
        this.soLuong = soLuong;
        this.idDelete = idDelete;
    }
    public CartDetail(int idSP,int soLuong) {
        this.sanPham3 = new Product(idSP);
        this.soLuong = soLuong;
        this.idDelete = 0;
    }

    public int getMaCTGH() {
        return maCTGH;
    }

    public void setMaCTGH(int maCTGH) {
        this.maCTGH = maCTGH;
    }

    public Cart getGioHang() {
        return gioHang;
    }

    public void setGioHang(Cart gioHang) {
        this.gioHang = gioHang;
    }

    public Product getSanPham3() {
        return sanPham3;
    }

    public void setSanPham3(Product sanPham3) {
        this.sanPham3 = sanPham3;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getIdDelete() {
        return idDelete;
    }

    public void setIdDelete(int idDelete) {
        this.idDelete = idDelete;
    }
}
