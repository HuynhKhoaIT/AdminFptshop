package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("maDH")
    int maDH;
    @SerializedName("tongTien")
    int tongTien;
    @SerializedName("diaChi")
    String diaChi;
    @SerializedName("ngayDatHang")
    Date ngayDatHang;
    @SerializedName("listChiTietDonHang")
    private ArrayList<OrderDetail> listChiTietDonHang;
    @SerializedName("trangThai")
    private OrderStatus trangThai;
    @SerializedName("khachHang")
    private User khachHang;


    public Order() {
    }

    public Order(int maDH, int tongTien, String diaChi, Date ngayDatHang, ArrayList<OrderDetail> listChiTietDonHang, OrderStatus trangThai, User khachHang) {
        this.maDH = maDH;
        this.tongTien = tongTien;
        this.diaChi = diaChi;
        this.ngayDatHang = ngayDatHang;
        this.listChiTietDonHang = listChiTietDonHang;
        this.trangThai = trangThai;
        this.khachHang = khachHang;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(Date ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public ArrayList<OrderDetail> getListChiTietDonHang() {
        return listChiTietDonHang;
    }

    public void setListChiTietDonHang(ArrayList<OrderDetail> listChiTietDonHang) {
        this.listChiTietDonHang = listChiTietDonHang;
    }

    public OrderStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(OrderStatus trangThai) {
        this.trangThai = trangThai;
    }

    public User getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(User khachHang) {
        this.khachHang = khachHang;
    }
}
