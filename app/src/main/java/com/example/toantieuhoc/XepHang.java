package com.example.toantieuhoc;

import android.graphics.drawable.Drawable;

public class XepHang {
    private  int Hinh;
    private  String Hang;
    private int IDNguoiChoi;
    private String tennguoichoi;
    private String tongdiem;
    private String tongthoigian;

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

    public String getHang() {
        return Hang;
    }

    public void setHang(String hang) {
        Hang = hang;
    }


    public int getIDNguoiChoi() {
        return IDNguoiChoi;
    }

    public void setIDNguoiChoi(int IDNguoiChoi) {
        this.IDNguoiChoi = IDNguoiChoi;
    }

    public String getTennguoichoi() {
        return tennguoichoi;
    }

    public void setTennguoichoi(String tennguoichoi) {
        this.tennguoichoi = tennguoichoi;
    }

    public String getTongdiem() {
        return tongdiem;
    }

    public void setTongdiem(String tongdiem) {
        this.tongdiem = tongdiem;
    }

    public String getTongthoigian() {
        return tongthoigian;
    }

    public void setTongthoigian(String tongthoigian) {
        this.tongthoigian = tongthoigian;
    }

    public XepHang(int Hinh,String Hang,int IDNguoiChoi, String tennguoichoi, String tongdiem, String tongthoigian) {
        this.Hinh = Hinh;
        this.Hang = Hang;
        this.IDNguoiChoi = IDNguoiChoi;
        this.tennguoichoi = tennguoichoi;
        this.tongdiem = tongdiem;
        this.tongthoigian = tongthoigian;
    }
}
