package com.example.docsach2;

public class Sach {
    public int idSach;
    public int idTheLoai;
    public String tenSach;
    public String moTa;
    public byte[] anhBia;

    public Sach(int idSach, int idTheLoai, String tenSach,String moTa, byte[] anhBia) {
        this.idSach = idSach;
        this.idTheLoai = idTheLoai;
        this.tenSach = tenSach;
        this.moTa = moTa;
        this.anhBia = anhBia;
    }
}
