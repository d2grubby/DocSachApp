package com.example.docsach2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DangNhap_Activity extends MainActivity {
    final String DATABASE_NAME = "appdocsach2.sqlite";
    SQLiteDatabase database;
    EditText edtUser, edtPass;
    Button btnLogin, btnRegister;

    public static ArrayList<KhachHang> thongTinUser;
    int idKH;
    int idYeuThich;
    String tenKH;
    String tenDN;
    String matKhau;

    public int userpassword(String user, String password){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE tendn = ? and matkhau = ?", new String[]{user, password});
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            idKH = cursor.getInt(0);
            tenKH = cursor.getString(1);
            tenDN = cursor.getString(2);
            matKhau = cursor.getString(3);
            return 1;
        }
        else return 0;
    }

    private void layIDYeuThich(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM yeuthich WHERE idkh = ?", new String[]{String.valueOf(idKH)});
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            idYeuThich = cursor.getInt(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        addControls();
    }
    private void addControls(){
        database = Database.initDatabase(this, DATABASE_NAME);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtPass.getText().toString();
                String user = edtUser.getText().toString();
                int checkUserPass = userpassword(user, pass);
                if(checkUserPass == 1){
                    Intent intent = new Intent(DangNhap_Activity.this, MainActivity.class);
                    layIDYeuThich();
                    thongTinUser.add(new KhachHang(idKH,idYeuThich,tenKH,tenDN,matKhau));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap_Activity.this, DangKy_Activity.class));
            }
        });
    }
}