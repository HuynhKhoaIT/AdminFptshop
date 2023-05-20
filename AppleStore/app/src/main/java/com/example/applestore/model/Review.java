package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {

    @SerializedName("maDG")
    int maDG;
    @SerializedName("sanPham")
    private Product sanPham;
    @SerializedName("khachHang")
    private User khachHang;
    @SerializedName("noiDung")
    String noiDung;
    @SerializedName("vote")
    int vote;

    public Review() {
    }

    public Review(int maDG, Product sanPham, User khachHang, String noiDung, int vote) {
        this.maDG = maDG;
        this.sanPham = sanPham;
        this.khachHang = khachHang;
        this.noiDung = noiDung;
        this.vote = vote;
    }

    public Review(Product sanPham, User khachHang, String noiDung, int vote) {
        this.sanPham = sanPham;
        this.khachHang = khachHang;
        this.noiDung = noiDung;
        this.vote = vote;
    }

    public int getMaDG() {
        return maDG;
    }

    public void setMaDG(int maDG) {
        this.maDG = maDG;
    }

    public Product getSanPham() {
        return sanPham;
    }

    public void setSanPham(Product sanPham) {
        this.sanPham = sanPham;
    }

    public User getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(User khachHang) {
        this.khachHang = khachHang;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
