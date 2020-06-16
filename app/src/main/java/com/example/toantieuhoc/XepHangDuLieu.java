package com.example.toantieuhoc;

public class XepHangDuLieu {
    private int idnguoichoi;
    private String tenuser;
    private String tongdiem;
    private String tongthoigian;

    public XepHangDuLieu(int idnguoichoi, String tenuser, String tongdiem, String tongthoigian) {
        this.idnguoichoi = idnguoichoi;
        this.tenuser = tenuser;
        this.tongdiem = tongdiem;
        this.tongthoigian = tongthoigian;
    }

    public int getIdnguoichoi() {
        return idnguoichoi;
    }

    public void setIdnguoichoi(int idnguoichoi) {
        this.idnguoichoi = idnguoichoi;
    }

    public String getTenuser() {
        return tenuser;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
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
}
