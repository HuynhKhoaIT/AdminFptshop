package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable {
    @SerializedName("maBV")
    private int maBV;
    @SerializedName("tieuDeBV")
    private String tieuDeBV;
    @SerializedName("noiDung")
    private String noiDung;
    @SerializedName("anh")
    private String anh;
    @SerializedName("ngayTao")
    private Date ngayTao;
    @SerializedName("ngayCapNhat")
    private Date ngayCapNhat;

    public Blog() {
    }

    public Blog(int maBV, String tieuDeBV, String noiDung, String anh, Date ngayTao, Date ngayCapNhat) {
        this.maBV = maBV;
        this.tieuDeBV = tieuDeBV;
        this.noiDung = noiDung;
        this.anh = anh;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }

    public int getMaBV() {
        return maBV;
    }

    public void setMaBV(int maBV) {
        this.maBV = maBV;
    }

    public String getTieuDeBV() {
        return tieuDeBV;
    }

    public void setTieuDeBV(String tieuDeBV) {
        this.tieuDeBV = tieuDeBV;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }
}
