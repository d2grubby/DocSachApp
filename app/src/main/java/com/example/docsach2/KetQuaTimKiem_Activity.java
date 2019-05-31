package com.example.docsach2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class KetQuaTimKiem_Activity extends MainActivity {
    final String DATABASE_NAME = "appdocsach2.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<Sach> list;
    AdapterSach adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_tim_kiem);
        addControls();
        readData();
    }

    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterSach(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Sach sach = list.get(position);
                Intent intent = new Intent(KetQuaTimKiem_Activity.this, Chi_Tiet_Sach.class);
                intent.putExtra("ID", sach.idSach);
                startActivity(intent);
            }
        });
    }

    private void readData() {
        Intent intent = getIntent();
        String ten = intent.getStringExtra("tenSach");
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM sach WHERE tensach LIKE ?", new String[]{"%" + ten + "%",});
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idSach = cursor.getInt(0);
            int idTheLoai = cursor.getInt(1);

            String tenSach = cursor.getString(2);
            String moTa = cursor.getString(3);
            byte[] anhBia = cursor.getBlob(4);

            list.add(new Sach(idSach, idTheLoai, tenSach,moTa, anhBia));
        }
        adapter.notifyDataSetChanged();
    }
}