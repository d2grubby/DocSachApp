package com.example.docsach2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterYeuThich extends BaseAdapter {

    Activity context;
    ArrayList<YeuThich> list;

    public AdapterYeuThich(Activity context, ArrayList<YeuThich> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.yeu_thich, null);
        ImageView imgSach = (ImageView) row.findViewById(R.id.imgSach);
        TextView txtTenSach = (TextView) row.findViewById(R.id.txtTenSach);
        TextView txtTheLoai = (TextView) row.findViewById(R.id.txtTheLoai);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        final YeuThich yeuthich = list.get(position);
        txtTenSach.setText(yeuthich.tenSach + "");
        txtTheLoai.setText("Thể loại: " + yeuthich.theLoai + "");

        Bitmap bmSach1 = BitmapFactory.decodeByteArray(yeuthich.anhBia, 0, yeuthich.anhBia.length);
        imgSach.setImageBitmap(bmSach1);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(yeuthich.idSach);
                Intent intent = new Intent(context, YeuThich_Activity.class);
                context.startActivity(intent);
                }
        });

        return row;
    }

    private void delete (int idsach){
        SQLiteDatabase database = Database.initDatabase(context, "appdocsach2.sqlite");
        database.delete("chitietyeuthich", "idsach = ?", new String[]{idsach + ""});

        notifyDataSetChanged();
    }
}