package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class TheLoaiBaiHocActivity extends AppCompatActivity {

    ImageView imgDemSo, imgDienSo, imgBaiToan;
    ImageButton imgBackHomeTL;
    int lopHoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_the_loai_bai_hoc);
        Init();
        GetIntent();
        Act();
    }

    private void GetIntent() {
        Intent intent = getIntent();
        lopHoc = intent.getIntExtra("tenLop",123);
        //Toast.makeText(TheLoaiBaiHocActivity.this, "Lớp"+lopHoc, Toast.LENGTH_LONG).show();
    }

    private void Act() {
        imgBackHomeTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(TheLoaiBaiHocActivity.this, R.raw.click_tul);
                player.start();
                finish();
            }
        });

        // phien bản đếm số
        imgDemSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(TheLoaiBaiHocActivity.this, R.raw.click_tul);
                player.start();
                Intent i = new Intent(TheLoaiBaiHocActivity.this, BaiHocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("lopHoc", lopHoc);
                bundle.putInt("theLoai",2);
                i.putExtra("dulieu", bundle);
                startActivity(i);
            }
        });

        // phiên bản so sánh
        imgDienSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(TheLoaiBaiHocActivity.this, R.raw.click_tul);
                player.start();
                Intent i = new Intent(TheLoaiBaiHocActivity.this, BaiHocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("lopHoc", lopHoc);
                bundle.putInt("theLoai",1);
                i.putExtra("dulieu", bundle);
                startActivity(i);
            }
        });

        imgBaiToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(TheLoaiBaiHocActivity.this, R.raw.click_tul);
                player.start();
                Toast.makeText(getApplicationContext(),"Tạm thời chưa mở phiên bản này!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init() {
        imgBackHomeTL = findViewById(R.id.imageButtonBackHomeTL);
        imgDemSo      = findViewById(R.id.imageTLDemSo);
        imgDienSo     = findViewById(R.id.imageTLDienSo);
        imgBaiToan    = findViewById(R.id.imageTLBaiToan);
    }
}
