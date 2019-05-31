package com.example.docsach2;

public class YeuThich {
    public int idYeuThich;
    public int idSach;
    public String tenSach;
    public String theLoai;
    public byte[] anhBia;

    public YeuThich(int idYeuThich, int idSach, String tenSach, String theLoai, byte[] anhBia) {
        this.idYeuThich = idYeuThich;
        this.idSach = idSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.anhBia = anhBia;
    }

    public int getIdYeuThich() {
        return idYeuThich;
    }

    public void setIdYeuThich(int idYeuThich) {
        this.idYeuThich = idYeuThich;
    }

    public int getIdSach() {
        return idSach;
    }

    public void setIdSach(int idSach) {
        this.idSach = idSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public byte[] getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(byte[] anhBia) {
        this.anhBia = anhBia;
    }
}
