package com.example.toantieuhoc;

import android.widget.ImageView;

import java.io.PipedReader;
import java.io.Serializable;

public class BaiHoc implements Serializable{
    private String trangthai;
    private int HinhAnh;
    private  String ten;
    private  int IDBaiHoc;
    private  int IDTheLoai;
    private  String NoiDung;
    private  String DapAn;
    private  int IDLopHoc;
    private int IDNguoiChoi;
    private String diem;

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    private String thoigian;
    public BaiHoc(int hinhAnh, String ten, int IDBaiHoc, int IDTheLoai, String NoiDung, String DapAn, int IDLopHoc, int IDNguoichoi, String trangthai,String diem, String thoigian) {
        HinhAnh = hinhAnh;
        this.ten = ten;
        this.IDBaiHoc = IDBaiHoc;
        this.IDTheLoai = IDTheLoai;
        this.NoiDung = NoiDung;
        this.DapAn = DapAn;
        this.IDLopHoc = IDLopHoc;
        this.IDNguoiChoi = IDNguoichoi;
        this.trangthai = trangthai;
        this.diem = diem;
        this.thoigian = thoigian;
    }


    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getIDNguoiChoi() {
        return IDNguoiChoi;
    }

    public void setIDNguoiChoi(int IDNguoiChoi) {
        this.IDNguoiChoi = IDNguoiChoi;
    }

    public int getIDLopHoc() {
        return IDLopHoc;
    }

    public void setIDLopHoc(int IDLopHoc) {
        this.IDLopHoc = IDLopHoc;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getDapAn() {
        return DapAn;
    }

    public void setDapAn(String dapAn) {
        DapAn = dapAn;
    }

    public int getIDBaiHoc() {
        return IDBaiHoc;
    }

    public void setIDBaiHoc(int IDBaiHoc) {
        this.IDBaiHoc = IDBaiHoc;
    }

    public int getIDTheLoai() {
        return IDTheLoai;
    }

    public void setIDTheLoai(int IDTheLoai) {
        this.IDTheLoai = IDTheLoai;
    }

    public int getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }


}
