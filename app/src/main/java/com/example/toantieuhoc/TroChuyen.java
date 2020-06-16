package com.example.toantieuhoc;

public class TroChuyen {
    String noidung;
    String tenuser;
    public TroChuyen(String noidung, String tenuser) {
        this.noidung = noidung;
        this.tenuser = tenuser;
    }

    public String getTenuser() {
        return tenuser;
    }



    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
