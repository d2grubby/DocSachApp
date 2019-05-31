package com.example.docsach2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TimKiem_Activity extends MainActivity {
    EditText edtTimKiem;
    Button btnTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensach = edtTimKiem.getText().toString();
                Intent intent = new Intent(TimKiem_Activity.this, KetQuaTimKiem_Activity.class);
                intent.putExtra("tenSach", tensach);
                startActivity(intent);
            }
        });
    }
}
