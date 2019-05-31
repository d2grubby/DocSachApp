package com.example.docsach2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class YeuThich_Activity extends MainActivity {
    final String DATABASE_NAME = "appdocsach2.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<YeuThich> list;
    AdapterYeuThich adapter;
    int idDangNhap = DangNhap_Activity.thongTinUser.get(0).getIdKH();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);

        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterYeuThich(this, list);
        listView.setAdapter(adapter);

        readData();
    }
    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select chitietyeuthich.idyeuthich, chitietyeuthich.idsach, tensach, tentheloai, anhbia from sach, theloai, chitietyeuthich, yeuthich,khachhang where sach.idtheloai = theloai.idtheloai and khachhang.idkh = yeuthich.idkh and chitietyeuthich.idsach = sach.idsach and yeuthich.idyeuthich = chitietyeuthich.idyeuthich and khachhang.idkh = ? group by chitietyeuthich.idyeuthich, chitietyeuthich.idsach, tensach, tentheloai, anhbia",new String[]{idDangNhap + ""});
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idyeuthich = cursor.getInt(0);
            int idsach = cursor.getInt(1);
            String tenSach = cursor.getString(2);
            String tenTheLoai = cursor.getString(3);
            byte[] anhBia = cursor.getBlob(4);

            list.add(new YeuThich(idyeuthich, idsach, tenSach,tenTheLoai, anhBia));
        }
        adapter.notifyDataSetChanged();
    }
}