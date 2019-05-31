package com.example.docsach2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DangKy_Activity extends MainActivity {
    final String DATABASE_NAME = "appdocsach2.sqlite";
    SQLiteDatabase database;
    Button btnDangKy, btnDangNhap;
    EditText edtTen, edtTenDangNhap, edtMatKhau;
    int idKHMoiNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControls();
        addEvents();
    }

    public Boolean checkUser(String user){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE tendn = ?", new String[]{user});
        if(cursor.getCount() > 0) return false;
        else return true;
    }

    private void addControls(){
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtTenDangNhap = (EditText) findViewById(R.id.edtTenDangNhap);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
    }

    private void addEvents(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = edtTen.getText().toString();
                String s2 = edtTenDangNhap.getText().toString();
                String s3 = edtMatKhau.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals("")){
                    Toast.makeText(getApplicationContext(), "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkUser = checkUser(s2);
                    if(checkUser == true){
                        insertKhachHang();
                        insertYeuThich();
                        }
                        else if(checkUser == false){
                            Toast.makeText(getApplicationContext(), "Tên đăng nhập không khả dụng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKy_Activity.this, DangNhap_Activity.class));
            }
        });
    }

    private void insertKhachHang(){
        String ten = edtTen.getText().toString();
        String tendangnhap = edtTenDangNhap.getText().toString();
        String matkhau = edtMatKhau.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("tenkh", ten);
        contentValues.put("tendn", tendangnhap);
        contentValues.put("matkhau", matkhau);

        SQLiteDatabase database = Database.initDatabase(this, "appdocsach2.sqlite");
        database.insert("khachhang",null, contentValues);
        Intent intent = new Intent(this, DangNhap_Activity.class);
        startActivity(intent);
    }
    private void insertYeuThich(){
        //Lấy id của khách hàng vừa đăng ký
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang ORDER BY idkh DESC LIMIT 1", null);
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            idKHMoiNhat = cursor.getInt(0);
        }
        int idkh = idKHMoiNhat;

        //Và Insert vào bảng Yêu thích
        ContentValues contentValues = new ContentValues();
        contentValues.put("idkh", idkh);

        SQLiteDatabase database = Database.initDatabase(this, "appdocsach2.sqlite");
        database.insert("yeuthich",null, contentValues);
        Intent intent = new Intent(this, DangNhap_Activity.class);
        startActivity(intent);
    }

}