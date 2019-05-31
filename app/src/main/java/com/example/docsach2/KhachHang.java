package com.example.docsach2;

public class KhachHang {
    public int idKH;
    public int idyeuthich;
    public String tenKH;
    public String tenDN;
    public String matKhau;

    public KhachHang(int idKH, int idyeuthich, String tenKH, String tenDN, String matKhau) {
        this.idKH = idKH;
        this.idyeuthich = idyeuthich;
        this.tenKH = tenKH;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
    }

    public int getIdyeuthich() {
        return idyeuthich;
    }

    public void setIdyeuthich(int idyeuthich) {
        this.idyeuthich = idyeuthich;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
