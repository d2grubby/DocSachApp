package com.example.docsach2;

import android.content.Intent;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class Doc_Sach_Activity extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_sach);
        Intent intent = getIntent();
        String pdfName = intent.getStringExtra("pdfName");
        final PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset(pdfName)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)

                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }
}

