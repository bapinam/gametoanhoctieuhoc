package com.example.toantieuhoc;

public class NoiDungBaiHoc {
    private  int IdBaiHoc;
    private  String TenBaiHoc;
    private  String NoiDung;
    private  String DapAn;
    private  int IdTheLoai;
    private  int IDLopHoc;
    private  String trangthai;
    private  String diem;
    private  String thoigian;



    public NoiDungBaiHoc(int idBaiHoc, String tenBaiHoc, String noiDung, String dapAn, int idTheLoai, int IDLopHoc, String trangthai, String diem, String thoigian) {
        IdBaiHoc = idBaiHoc;
        TenBaiHoc = tenBaiHoc;
        NoiDung = noiDung;
        DapAn = dapAn;
        IdTheLoai = idTheLoai;
        this.IDLopHoc = IDLopHoc;
        this.trangthai = trangthai;
        this.diem = diem;
        this.thoigian = thoigian;
    }
    public String getTrangthai() {
        return trangthai;
    }

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

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }



    public int getIDLopHoc() {
        return IDLopHoc;
    }

    public void setIDLopHoc(int IDLopHoc) {
        this.IDLopHoc = IDLopHoc;
    }

    public int getIdBaiHoc() {
        return IdBaiHoc;
    }

    public void setIdBaiHoc(int idBaiHoc) {
        IdBaiHoc = idBaiHoc;
    }

    public String getTenBaiHoc() {
        return TenBaiHoc;
    }

    public void setTenBaiHoc(String tenBaiHoc) {
        TenBaiHoc = tenBaiHoc;
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


    public int getIdTheLoai() {
        return IdTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        IdTheLoai = idTheLoai;
    }
}
