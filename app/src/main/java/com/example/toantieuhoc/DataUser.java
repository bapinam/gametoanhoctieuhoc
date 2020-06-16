package com.example.toantieuhoc;

public class DataUser {
    private  int idnguoichoi;
    private  String tenuser;
    private  int vang;
    private  int idlop;


    public DataUser(int idnguoichoi, String tenuser, int vang, int idlop) {
        this.idnguoichoi = idnguoichoi;
        this.tenuser = tenuser;
        this.vang = vang;
        this.idlop = idlop;
    }
    public int getIdlop() {
        return idlop;
    }

    public void setIdlop(int idlop) {
        this.idlop = idlop;
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

    public int getVang() {
        return vang;
    }

    public void setVang(int vang) {
        this.vang = vang;
    }

}
