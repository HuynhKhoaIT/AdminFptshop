package com.example.applestore.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banner implements Serializable {
    @SerializedName("maB")
    private  int maB;
    @SerializedName("tieuDeB")
    private  String tieuDeB;
    @SerializedName("anh")
    private  String anh;

    public Banner() {
    }
    public Banner(int maB, String tieuDeB, String anh) {
        this.maB = maB;
        this.tieuDeB = tieuDeB;
        this.anh = anh;
    }

    public int getMaB() {
        return maB;
    }

    public void setMaB(int maB) {
        this.maB = maB;
    }

    public String getTieuDeB() {
        return tieuDeB;
    }

    public void setTieuDeB(String tieuDeB) {
        this.tieuDeB = tieuDeB;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
