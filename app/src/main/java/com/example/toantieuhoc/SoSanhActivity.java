package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.toantieuhoc.BaiHocActivity.ip;
import static com.example.toantieuhoc.BaiHocAdapter.getBaiHoc;

public class SoSanhActivity extends AppCompatActivity {

    ImageButton imageButtonBackSSDS;
    ImageView imageViewNgoiSaoSS3,imageViewNgoiSaoSS2, imageViewNgoiSaoSS1;
    Button btnNoiDung1, btnNoiDung2, btnNoiDung3, btnNoiDung4, btnNoiDung5,btnNoiDung6;
    TextView txtClock;
    String[] arrNoiDung ;
    List<String> listTrangThai =  new ArrayList<>();
    List<String> listButton = new ArrayList<>();
    int SoLanDung = 0;
    int SoLanSai = 0;
    String NoiDung ="" ;
    String DapAn ="";
    int IDTheLoai = 0;
    int IDBaiHoc = 0;
    int IDLopHoc = 0;
    int diem = 0;
    int idnguoichoi = 0;
    String trangthai;
    int vitri = -1;
    private static final  long START_TIME_IN_MILLIS = 120000; // 5 số 0 -> 1 phút 60 giay, 3 phút, lấy 3 x 60 = 180 x thêm 1000 . 1s = 1000
    private CountDownTimer downTimer;
    private boolean mTimerRunning;
    private  long mTimeLeftMillis = START_TIME_IN_MILLIS;
    MediaPlayer playerDH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_so_sanh);
        Init();
        // MoGoiDuLieu();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        if(bundle !=null) {
            BaiHoc baiHoc = (BaiHoc) bundle.getSerializable("dataBaiHoc");
            NoiDung = baiHoc.getNoiDung();
            DapAn = baiHoc.getDapAn();
            IDBaiHoc = baiHoc.getIDBaiHoc();
            IDTheLoai = baiHoc.getIDTheLoai();
            IDLopHoc = baiHoc.getIDLopHoc();
            idnguoichoi = baiHoc.getIDNguoiChoi();

            vitri = bundle.getInt("vitri",-1);
            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            int id =  sharedPreferences.getInt("idbaihoc",-1);
            String tt = sharedPreferences.getString("trangthai",null);
            if(id == IDBaiHoc)
            {
                trangthai ="xong";
            }
            else
            {
                trangthai = baiHoc.getTrangthai();
            }
        }
        GanNoiDung();
        Action();
        startTimer();
        BackHome();
    }

    private void Init() {
        btnNoiDung1 = findViewById(R.id.buttonNoiDung1);
        btnNoiDung2 = findViewById(R.id.buttonNoiDung2);
        btnNoiDung3 = findViewById(R.id.buttonNoiDung3);
        btnNoiDung4 = findViewById(R.id.buttonNoiDung4);
        btnNoiDung5 = findViewById(R.id.buttonNoiDung5);
        btnNoiDung6 = findViewById(R.id.buttonNoiDung6);

        imageButtonBackSSDS = findViewById(R.id.imageButtonBackSSDS);
        imageViewNgoiSaoSS3 = findViewById(R.id.imageViewNgoiSaoSS3);
        imageViewNgoiSaoSS2 = findViewById(R.id.imageViewNgoiSaoSS2);
        imageViewNgoiSaoSS1 = findViewById(R.id.imageViewNgoiSaoSS1);

        txtClock = findViewById(R.id.textViewTimeSS);
        imageButtonBackSSDS = findViewById(R.id.imageButtonBackSSDS);
    }

    private void Action() {

        btnNoiDung1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
              btnNoiDung1.setBackgroundColor(Color.parseColor("#ff7f50"));  // #8BC34A
                LuuGiaTri(btnNoiDung1.getText().toString(), "btn1");
                listButton.add("1");
                SoSanh();

            }
        });

        btnNoiDung2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                btnNoiDung2.setBackgroundColor(Color.parseColor("#ff7f50"));
                LuuGiaTri(btnNoiDung2.getText().toString(),"btn2");
                listButton.add("2");
                SoSanh();
            }
        });

        btnNoiDung3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                btnNoiDung3.setBackgroundColor(Color.parseColor("#ff7f50"));
                LuuGiaTri(btnNoiDung3.getText().toString(),"btn3");
                listButton.add("3");
                SoSanh();
            }
        });

        btnNoiDung4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                btnNoiDung4.setBackgroundColor(Color.parseColor("#ff7f50"));
                LuuGiaTri(btnNoiDung4.getText().toString(),"btn4");
                listButton.add("4");
                SoSanh();
            }
        });

        btnNoiDung5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                btnNoiDung5.setBackgroundColor(Color.parseColor("#ff7f50"));
                LuuGiaTri(btnNoiDung5.getText().toString(),"btn5");
                listButton.add("5");
                SoSanh();
            }
        });

        btnNoiDung6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                btnNoiDung6.setBackgroundColor(Color.parseColor("#ff7f50"));
                LuuGiaTri(btnNoiDung6.getText().toString(),"btn6");
                listButton.add("6");
                SoSanh();
            }
        });
    }

    private void GanNoiDung() {
        arrNoiDung = new String[100];
        String[] tachChuoi = NoiDung.split("\\*");
        for(int i=0; i<tachChuoi.length;i++)
        {
            arrNoiDung[i] = String.valueOf(tachChuoi[i]);
        }
        List list = new ArrayList();
        for (int i=0; i<6 ; i++)
        {
            list.add(arrNoiDung[i]);

        }
        Collections.shuffle(list);
       btnNoiDung1.setText(list.get(0).toString());
        btnNoiDung2.setText(list.get(1).toString());
        btnNoiDung3.setText(list.get(2).toString());
        btnNoiDung4.setText(list.get(3).toString());
        btnNoiDung5.setText(list.get(4).toString());
        btnNoiDung6.setText(list.get(5).toString());

    }

    private  void LuuGiaTri(String noidung,String button)
    {
       for (int i=0; i<6;i++)
       {
           if(noidung.equals(arrNoiDung[i]))
           {
               if(i == 0 || i == 1)
               {
                   listTrangThai.add("1");
                   listTrangThai.add(button);
               }
               if(i==2||i==3)
               {
                   listTrangThai.add("2");
                   listTrangThai.add(button);
               }
               if(i==4||i==5)
               {
                   listTrangThai.add("3");
                   listTrangThai.add(button);
               }
           }
       }
    }

    private void SoSanh()
    {
        if(listTrangThai.size() == 4)
        {
            String so1 = listTrangThai.get(0).toString();
            String so2 = listTrangThai.get(2).toString();

            String btn1= listTrangThai.get(1).toString();
            String btn2= listTrangThai.get(3).toString();
            if(so1.equals(so2) && !btn1.equals(btn2))
            {
                for(int i = 0;i<2;i++)
                {
                    String ketqua = listButton.get(i).toString();
                    switch (ketqua)
                    {
                        case "1":
                        {
                            btnNoiDung1.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case "2":
                        {
                            btnNoiDung2.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case "3":
                        {
                            btnNoiDung3.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case "4":
                        {
                            btnNoiDung4.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case "5":
                        {
                            btnNoiDung5.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case "6":
                        {
                            btnNoiDung6.setVisibility(View.INVISIBLE);
                            break;
                        }

                    }
                }
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.chondung);
                player.start();
                SoLanDung ++;
                listTrangThai.clear();
                listButton.clear();
            }
            else
            {
                btnNoiDung1.setBackgroundColor(Color.parseColor("#8BC34A"));
                btnNoiDung2.setBackgroundColor(Color.parseColor("#8BC34A"));
                btnNoiDung3.setBackgroundColor(Color.parseColor("#8BC34A"));
                btnNoiDung4.setBackgroundColor(Color.parseColor("#8BC34A"));
                btnNoiDung5.setBackgroundColor(Color.parseColor("#8BC34A"));
                btnNoiDung6.setBackgroundColor(Color.parseColor("#8BC34A"));

                if(!btn1.equals(btn2))
                {
                    MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.chatlat);
                    player.start();
                    SoLanSai ++;
                }
                listTrangThai.clear();
                listButton.clear();
            }
        }

        if(SoLanDung == 3)
        {
            MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.traloidung);
            player.start();
            pauseTimer();
            DiaLogWin();

            if(!trangthai.equals("xong"))
            {
             //   Toast.makeText(this, ""+trangthai, Toast.LENGTH_SHORT).show();
                NhanVang();
            }
//            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
//            int id =  sharedPreferences.getInt("idbaihoc",-1);
//            String tt = sharedPreferences.getString("trangthai",null);
//            if(!tt.equals(trangthai))
//            {
//                NhanVang();
//            }

        }

        switch (SoLanSai)
        {
            case 1:
            {
                imageViewNgoiSaoSS3.setImageResource(R.drawable.ngoisaotrangden);
                break;
            }
            case 2:
            {
                imageViewNgoiSaoSS3.setImageResource(R.drawable.ngoisaotrangden);
                imageViewNgoiSaoSS2.setImageResource(R.drawable.ngoisaotrangden);
                break;
            }
            case 3:
            {
                imageViewNgoiSaoSS1.setImageResource(R.drawable.ngoisaotrangden);
                imageViewNgoiSaoSS2.setImageResource(R.drawable.ngoisaotrangden);
                imageViewNgoiSaoSS3.setImageResource(R.drawable.ngoisaotrangden);
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.traloisai);
                player.start();
                pauseTimer();
                DiaLogLoser();
                break;
            }
        }
    }

    private  void  startTimer()
    {
        downTimer = new CountDownTimer(mTimeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis = millisUntilFinished;
                if(mTimeLeftMillis <= 32000) // 30000 - 30s
                {
                    playerDH = MediaPlayer.create(SoSanhActivity.this, R.raw.amthanh30s);
                    playerDH.start();
                }
                updateCountText();
                if(mTimeLeftMillis <= 2000)
                {
                    pauseTimer();
                    MediaPlayer playerTwo = MediaPlayer.create(SoSanhActivity.this, R.raw.traloisai);
                    playerTwo.start();
                    try {
                        txtClock.setText("00:01");
                        Thread.sleep(1000);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    txtClock.setText("00:00");
                    imageViewNgoiSaoSS1.setImageResource(R.drawable.ngoisaotrangden);
                    imageViewNgoiSaoSS2.setImageResource(R.drawable.ngoisaotrangden);
                    imageViewNgoiSaoSS3.setImageResource(R.drawable.ngoisaotrangden);
                    MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.traloisai);
                    player.start();
                   DiaLogLoser();
                }
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }
    private  void pauseTimer()
    {
        downTimer.cancel();
        mTimerRunning = false;
    }

    private void  updateCountText()
    {
        int minues = (int) (mTimeLeftMillis / 1000 ) / 60;
        int seconds = (int) (mTimeLeftMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minues,seconds);
        txtClock.setText(timeLeftFormatted);

    }

    public  void DiaLogLoser()
    {
        if(mTimeLeftMillis <= 32000) // 30000 - 30s
        {
            playerDH.release();
        }
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dia_log_loser);
        dialog.setCancelable(false);
        pauseTimer();
        final ImageButton btnBack = (ImageButton) dialog.findViewById(R.id.btnLoser);
        final ImageButton btnReset = (ImageButton) dialog.findViewById(R.id.btnReset);
        final ImageButton btnNext = (ImageButton)dialog.findViewById(R.id.btnCountieBoQua);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                vitri++;
                TruVang(vitri,dialog,"next");

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(SoSanhActivity.this, BaiHocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("lopHoc", IDLopHoc);
                bundle.putInt("baiHoc", IDBaiHoc);
                bundle.putInt("theLoai", IDTheLoai);
                bundle.putString("trangthai","YES");
                intent.putExtra("dulieu",bundle);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // tải lại chính nó, reset restart
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                TruVang(vitri,dialog,"reset");
            }
        });
        dialog.show();

    }
    public  void DiaLogWin() {
        LuuTrangThaiHoanThanh();
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen); // trong suốt
        dialog.setContentView(R.layout.dia_log_chienthang);
        pauseTimer();
        dialog.setCancelable(false);
        final ImageButton btn2Win = (ImageButton) dialog.findViewById(R.id.btn2win);
        final ImageButton btnReset = (ImageButton) dialog.findViewById(R.id.btnResetLai);
        final ImageButton btnNext = (ImageButton)dialog.findViewById(R.id.btnCountie);
        //chọn trở về:
        btn2Win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(SoSanhActivity.this, BaiHocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("lopHoc", IDLopHoc);
                bundle.putInt("baiHoc", IDBaiHoc);
                bundle.putInt("theLoai", IDTheLoai);
                bundle.putString("trangthai","YES");
                intent.putExtra("dulieu",bundle);
                dialog.dismiss();
                startActivity(intent);
                finish();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // tải lại chính nó, reset restart
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                TruVang(vitri,dialog,"reset");
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                vitri++;
                TruVang(vitri,dialog,"next");

            }
        });
        dialog.show();

    }

    private void TruVang(final int position, final Dialog dialog, final String yeucau)
    {

        final Dialog dialogTon = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialogTon.setContentView(R.layout.dialog_tonvang);
        ImageButton btnNo = (ImageButton) dialogTon.findViewById(R.id.btnNoo);
        ImageButton btnYes = (ImageButton) dialogTon.findViewById(R.id.btnYess);
        RelativeLayout background = dialogTon.findViewById(R.id.giaoDienTonVang);
        background.setBackgroundResource(R.drawable.hinhnentim);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialogTon.dismiss();
            }
        });
        final String ip = BaiHocActivity.ip;
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (yeucau)
                {
                    case "reset":
                    {
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0 );
                        break;
                    }
                    case "next":
                    {
                        if(position > getBaiHoc().size()-1 || position < 0 )
                        {
                            dialog.dismiss();
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(SoSanhActivity.this, SoSanhActivity.class);
                            Bundle bundle = new Bundle();
                            BaiHoc baiHoc = getBaiHoc().get(position);
                            bundle.putInt("vitri", position);
                            bundle.putSerializable("dataBaiHoc",baiHoc);
                            intent.putExtra("dulieu", bundle);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                        break;
                    }
                }


                String url="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=updateVang&idnguoichoi="+idnguoichoi+"&cv=0";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ArrayList<Vang> vang = new ArrayList<>();
                                for(int i = 0 ; i< response.length(); i++)
                                {
                                    JSONObject object = null;
                                    try {
                                        object = response.getJSONObject(i);
                                        vang.add(new Vang(
                                                object.getInt("vang")
                                        ));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    int  tien = vang.get(i).getVang();
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove("vang");
                                    editor.putInt("vang",tien);
                                    editor.putInt("vangMoi",tien);
                                    editor.apply();
                                    BaiHocActivity.User(tien);

                                }

                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue.add(arrayRequest);
                dialogTon.dismiss();
            }
        });
        dialogTon.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimeLeftMillis < 32000) // 30000 - 30s
        {
            playerDH.release();
        }
        pauseTimer();
    }
    @Override
    public void onBackPressed() {
        DiaLogBack();
    }

    private void BackHome() {
        imageButtonBackSSDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                pauseTimer();
                DiaLogBack();
            }
        });
    }

    private void DiaLogBack() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialogback);
        ImageButton btnNo = (ImageButton) dialog.findViewById(R.id.btnNo);
        ImageButton btnYes = (ImageButton) dialog.findViewById(R.id.btnYes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                if(mTimeLeftMillis <=32000) // 30000 - 30s
                {
                    playerDH.release();
                }
                pauseTimer();
                Intent intent = new Intent(SoSanhActivity.this, BaiHocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("lopHoc", IDLopHoc);
                bundle.putInt("baiHoc", IDBaiHoc);
                bundle.putInt("theLoai", IDTheLoai);
                bundle.putString("trangthai","YES");
                intent.putExtra("dulieu",bundle);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SoSanhActivity.this, R.raw.click_tul);
                player.start();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void LuuTrangThaiHoanThanh() {
        final long time = 120000 - mTimeLeftMillis;
        diem = SoLanDung - SoLanSai;
        String urls = "http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=thamgia";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
     //   final int finalDiem = diem;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Thất Bại", Toast.LENGTH_LONG).show();
                        Log.d("AAA","Lỗi!!");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idbaihoc", String.valueOf(IDBaiHoc));
                params.put("diem", String.valueOf(diem));
                params.put("thoigian", String.valueOf(time));
                params.put("idnguoichoi", String.valueOf(idnguoichoi));
                if(trangthai.equals("xong"))
                {
                    params.put("trangthai","choilai");
                }
                else
                {
                    params.put("trangthai","xong");
                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("trangthai","xong");
                    editor.putInt("idbaihoc",IDBaiHoc);
                    editor.apply();

                }

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void NhanVang()
    {
        final Dialog dialogNhan = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialogNhan.setContentView(R.layout.dialog_nhanvang);
        TextView textViewVangThuong = dialogNhan.findViewById(R.id.textViewVangThuong);
        Button btnThoat   = dialogNhan.findViewById(R.id.buttonThoatRa);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        int vangNhan = sharedPreferences.getInt("vang",0);
        textViewVangThuong.setText(String.valueOf(vangNhan));
        dialogNhan.setCancelable(false);
        dialogNhan.show();
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNhan.dismiss();
            }
        });
        int vangNew = vangNhan + 30;
        textViewVangThuong.setText(String.valueOf(vangNew));
        String url="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=updateVang&idnguoichoi="+idnguoichoi+"&cv=1";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Vang> vang = new ArrayList<>();
                        for(int i = 0 ; i< response.length(); i++)
                        {
                            JSONObject object = null;
                            try {
                                object = response.getJSONObject(i);
                                vang.add(new Vang(
                                        object.getInt("vang")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int  tien = vang.get(i).getVang();
                            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("vang");
                            editor.putInt("vang",tien);
                            editor.putInt("vangMoi",tien);
                            editor.apply();
                            BaiHocActivity.User(tien);

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(arrayRequest);
    }
}
