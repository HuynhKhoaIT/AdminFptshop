package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    @SerializedName("maKH")
    private int maKH;
    @SerializedName("tenKH")
    private String tenKH;
    @SerializedName("tenTK")
    private String tenTK;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("diaChi")
    private String diaChi;
    @SerializedName("mK")
    private String mK;
    @SerializedName("isUser")
    private int isUser;
    @SerializedName("isAdmin")
    private int isAdmin;

    @SerializedName("listDonHang")
    private ArrayList<Order> listDonHang;

    @SerializedName("gioHang")
    private Cart gioHang;

    public User() {
    }
    public User(int maKH) {
        this.maKH = maKH;
    }


    public User(String tenKH, String email, String phone, String diaChi, String mK, int isUser, int isAdmin){
        this.tenKH = tenKH;
        this.email = email;
        this.phone = phone;
        this.diaChi = diaChi;
        this.mK = mK;
        this.isUser = isUser;
        this.isAdmin = isAdmin;
    }
    public User(int maKH, String tenKH, String email, String phone, String diaChi, String mK, int isUser, int isAdmin){
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.email = email;
        this.phone = phone;
        this.diaChi = diaChi;
        this.mK = mK;
        this.isUser = isUser;
        this.isAdmin = isAdmin;
    }
    public User(int maKH, String tenKH, String tenTK, String email, String phone, String diaChi, String mK, int isUser, int isAdmin, ArrayList<Order> listDonHang, Cart gioHang) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.tenTK = tenTK;
        this.email = email;
        this.phone = phone;
        this.diaChi = diaChi;
        this.mK = mK;
        this.isUser = isUser;
        this.isAdmin = isAdmin;
        this.listDonHang = listDonHang;
        this.gioHang = gioHang;
    }



    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getmK() {
        return mK;
    }

    public void setmK(String mK) {
        this.mK = mK;
    }

    public int getIsUser() {
        return isUser;
    }

    public void setIsUser(int isUser) {
        this.isUser = isUser;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public ArrayList<Order> getListDonHang() {
        return listDonHang;
    }

    public void setListDonHang(ArrayList<Order> listDonHang) {
        this.listDonHang = listDonHang;
    }

    public Cart getGioHang() {
        return gioHang;
    }

    public void setGioHang(Cart gioHang) {
        this.gioHang = gioHang;
    }
}
