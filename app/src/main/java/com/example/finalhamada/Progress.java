package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * شاشة Progress المبسطة
 */
public class Progress extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvTitle;

    private ImageView imgChart;
    private TextView tvChartTitle;
    private TextView tvChartSubtitle;

    private ImageView ivIcon;
    private TextView tvSummaryTitle;
    private TextView tvSummaryBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // ربط العناصر
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);

        imgChart = findViewById(R.id.imgChart);
        tvChartTitle = findViewById(R.id.tvChartTitle);
        tvChartSubtitle = findViewById(R.id.tvChartSubtitle);

        ivIcon = findViewById(R.id.ivIcon);
        tvSummaryTitle = findViewById(R.id.tvSummaryTitle);
        tvSummaryBody = findViewById(R.id.tvSummaryBody);

        // زر الرجوع للداشبورد
        btnBack.setOnClickListener(v -> startActivity(new Intent(Progress.this, DashboardActivity.class)));
    }
}
