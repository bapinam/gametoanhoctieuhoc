package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LopHocActivity extends AppCompatActivity {

    private ImageView iv1,iv2,iv3,iv4,iv5;
    ImageButton ibtnBack;
    int idnguoichoi, vang, idlop;
    String tenuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lop_hoc);
        AnhXa();
        ChuyenDoiAnhSizeQuaLon();
        Click_Lop();
        MoBundle();

    }

    private void MoBundle() {
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("dulieuNC");
        if(bundle !=null) {
            idnguoichoi = bundle.getInt("idnguoichoi",-1);
            vang = bundle.getInt("vang",0);
            idlop = bundle.getInt("idlop",0);
            tenuser = bundle.getString("tenuser","");
        }
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int vangMoi = sharedPreferences.getInt("vangMoi",-1);
        if(vangMoi==-1)
        {
            editor.putInt("idnguoichoi",idnguoichoi);
            editor.putInt("vang",vang);
            editor.putInt("idlop",idlop);
            editor.putString("tenuser",tenuser);
        }else {
            editor.putInt("idnguoichoi",idnguoichoi);
            editor.putInt("vang",vangMoi);
            editor.putInt("idlop",idlop);
            editor.putString("tenuser",tenuser);
        }

        editor.apply();
    }

    private void Click_Lop() {
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoBundle();
                MediaPlayer player = MediaPlayer.create(LopHocActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(LopHocActivity.this, TheLoaiBaiHocActivity.class);
                intent.putExtra("tenLop", 1);
                startActivity(intent);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoBundle();
                MediaPlayer player = MediaPlayer.create(LopHocActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(LopHocActivity.this, TheLoaiBaiHocActivity.class);
                intent.putExtra("tenLop", 2);
                startActivity(intent);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoBundle();
                MediaPlayer player = MediaPlayer.create(LopHocActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(LopHocActivity.this, TheLoaiBaiHocActivity.class);
                intent.putExtra("tenLop", 3);
                startActivity(intent);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoBundle();
                MediaPlayer player = MediaPlayer.create(LopHocActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(LopHocActivity.this, TheLoaiBaiHocActivity.class);
                intent.putExtra("tenLop", 4);
                startActivity(intent);
            }
        });

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoBundle();
                MediaPlayer player = MediaPlayer.create(LopHocActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(LopHocActivity.this, TheLoaiBaiHocActivity.class);
                intent.putExtra("tenLop", 5);
                startActivity(intent);
            }
        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(LopHocActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(LopHocActivity.this, MainActivity.class);
                startActivity(intent);
                MoBundle();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LopHocActivity.this, MainActivity.class);
        startActivity(intent);
        MoBundle();
        finish();
    }

    private  void AnhXa()
    {
        iv1 = findViewById(R.id.imageLopMot);
        iv2 = findViewById(R.id.imageLopHai);
        iv3 = findViewById(R.id.imageLopBa);
        iv4 = findViewById(R.id.imageLopBon);
        iv5 = findViewById(R.id.imageLopNam);
        ibtnBack = findViewById(R.id.imageButtonBackHome);
    }

    private void ChuyenDoiAnhSizeQuaLon()
    {
        Glide.with(this).load(R.drawable.lopmot).override(300,300).into(iv1);
        Glide.with(this).load(R.drawable.lophai).override(300,300).into(iv2);
        Glide.with(this).load(R.drawable.lopba).override(300,300).into(iv3);
        Glide.with(this).load(R.drawable.lopbon).override(300,300).into(iv4);
        Glide.with(this).load(R.drawable.lopnam).override(300,300).into(iv5);
    }
}
