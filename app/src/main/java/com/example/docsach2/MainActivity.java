package com.example.docsach2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "appdocsach2.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<Sach> list;
    AdapterSach adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterSach(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Sach sach = list.get(position);
                Intent intent = new Intent(MainActivity.this, Chi_Tiet_Sach.class);
                intent.putExtra("ID", sach.idSach);
                startActivity(intent);
            }
        });

        if(DangNhap_Activity.thongTinUser != null){
        }else{
            DangNhap_Activity.thongTinUser = new ArrayList<>();
        }

        readData();
    }

    //Danh sách Sách
    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM sach", null);
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

    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doc_sach, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem login = menu.findItem(R.id.menuLogin);
        MenuItem register = menu.findItem(R.id.menuRegister);
        MenuItem yeuthich = menu.findItem(R.id.menuYeuThich);
        MenuItem logout = menu.findItem(R.id.menuLogout);
        if(DangNhap_Activity.thongTinUser.size() == 0)
        {
        }
        else
        {
            yeuthich.setVisible(true);
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuTheLoai){

        }
        else if(id == R.id.menuTrangChu){
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(id == R.id.menuLogin){
            startActivity(new Intent(this, DangNhap_Activity.class));
        }
        else if(id == R.id.menuRegister){
            startActivity(new Intent(this, DangKy_Activity.class));
        }
        else if(id == R.id.menuSearch){
            startActivity(new Intent(this, TimKiem_Activity.class));
        }
        else if(id == R.id.menuYeuThich){
            startActivity(new Intent(this, YeuThich_Activity.class));
        }
        else if(id == R.id.menuLogout){
            DangNhap_Activity.thongTinUser.clear();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
