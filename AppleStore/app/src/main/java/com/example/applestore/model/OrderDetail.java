package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    @SerializedName("maCTDH")
    private int maCTDH;
    @SerializedName("sanPham2")
    private Product sanPham2;
    @SerializedName("donHang")
    private Order donHang;
    @SerializedName("soLuong")
    int soLuong;
    @SerializedName("tongTien")
    int tongTien;


    public OrderDetail() {
    }

    public OrderDetail(int maCTDH, Product sanPham2, Order donHang, int soLuong, int tongTien) {
        this.maCTDH = maCTDH;
        this.sanPham2 = sanPham2;
        this.donHang = donHang;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public int getMaCTDH() {
        return maCTDH;
    }

    public void setMaCTDH(int maCTDH) {
        this.maCTDH = maCTDH;
    }

    public Product getSanPham2() {
        return sanPham2;
    }

    public void setSanPham2(Product sanPham2) {
        this.sanPham2 = sanPham2;
    }

    public Order getDonHang() {
        return donHang;
    }

    public void setDonHang(Order donHang) {
        this.donHang = donHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}

