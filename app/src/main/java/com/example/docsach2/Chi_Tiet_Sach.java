package com.example.docsach2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Chi_Tiet_Sach extends MainActivity{
    final String DATABASE_NAME = "appdocsach2.sqlite";
    final int REQUEST_CHOOSE_PHOTO = 321;
    int id = -1;
    SQLiteDatabase database;
    Button btnYeuThich,btnDoc;
    TextView txtTenSach,txtTheLoai,txtMoTa;
    ImageView imgSach;

    String pdfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sach);

        addControl();
        initUI();
        addEvent();

//        if(DangNhap.mangUserType.size() > 0 ){
//            btnGuiBL.setVisibility(View.VISIBLE);
//        }
//        else{
//            btnGuiBL.setVisibility(View.GONE);
//        }
    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select tensach, tentheloai, mota, anhbia, pdfName from sach, theloai where sach.idtheloai = theloai.idtheloai and idsach = ? group by tensach, tentheloai, mota, anhbia, pdfName",new String[]{id + ""});
        cursor.moveToFirst();
        String tenSach = cursor.getString(0);
        String theLoai = cursor.getString(1);
        String moTa = cursor.getString(2);
        final byte[] anhBia = cursor.getBlob(3);
        pdfName = cursor.getString(4);

        Bitmap bmSach = BitmapFactory.decodeByteArray(anhBia, 0, anhBia.length);
        imgSach.setImageBitmap(bmSach);
        txtTenSach.setText(tenSach);
        txtTheLoai.setText("Thể loại: " + theLoai);
        txtMoTa.setText(moTa);
    }

    private void addControl() {
        txtTenSach = (TextView) findViewById(R.id.txtTenSach);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        txtTheLoai = (TextView) findViewById(R.id.txtTheLoai);
        imgSach = (ImageView) findViewById(R.id.imgSach);
        btnDoc = (Button) findViewById(R.id.btnDoc);
        btnYeuThich = (Button) findViewById(R.id.btnYeuThich);
    }

    private void addEvent() {
        btnYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DangNhap_Activity.thongTinUser.size() > 0){
                    Intent intent = getIntent();
                    int idYeuThich = DangNhap_Activity.thongTinUser.get(0).getIdyeuthich();
                    id = intent.getIntExtra("ID",-1);
                    int checkFavoriteBook = checkFavoriteBook(idYeuThich, id);
                    if(checkFavoriteBook == 1){
                        Toast.makeText(getApplicationContext(),"Sách đã có trong danh sách yêu thích rồi", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        insertChiTietYeuThich();
                        Toast.makeText(getApplicationContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    startActivity(new Intent(Chi_Tiet_Sach.this, DangNhap_Activity.class));
                }
            }
        });
        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chi_Tiet_Sach.this, Doc_Sach_Activity.class);
                intent.putExtra("pdfName", pdfName);
                startActivity(intent);
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK)
            if(requestCode == REQUEST_CHOOSE_PHOTO) {
                try{
                    Uri imageUrl = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgSach.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }
    //Thêm sách vào danh sách yêu thích
    private void insertChiTietYeuThich(){
        Intent intent = getIntent();
        int idYeuThich = DangNhap_Activity.thongTinUser.get(0).getIdyeuthich();
        id = intent.getIntExtra("ID",-1);

        ContentValues contentValues = new ContentValues();
        contentValues.put("idyeuthich", idYeuThich);
        contentValues.put("idsach", id);

        SQLiteDatabase database = Database.initDatabase(this, "appdocsach2.sqlite");
        database.insert("chitietyeuthich",null, contentValues);
    }

    //Kiểm tra xem sách đó đã thêm vào danh sách yêu thích chưa

    public int checkFavoriteBook(int idyeuthich, int idsach){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM chitietyeuthich WHERE idyeuthich = ? and idsach = ?", new String[]{String.valueOf(idyeuthich), String.valueOf(idsach)});
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            return 1;
        }
        else return 0;
    }
}
