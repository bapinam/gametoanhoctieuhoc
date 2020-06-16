package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.toantieuhoc.BaiHocActivity.ip;
import static com.example.toantieuhoc.BaiHocAdapter.getBaiHoc;

public class DemSoActivity extends AppCompatActivity {

   RecyclerView recyclerMot, recyclerHai;
   Button buttonDemSoMot, buttonDemSoHai, buttonDemSoBa, buttonPhepToanDemSo;
   TextView textClock;
    String NoiDung ="" ;
    String DapAn ="";
    int IDTheLoai = 0;
    int IDBaiHoc = 0;
    int IDLopHoc = 0;
    int diem = 0;
    int idnguoichoi = 0;
    String trangthai;
    private static final  long START_TIME_IN_MILLIS = 90000; // 5 số 0 -> 1 phút 60 giay, 3 phút, lấy 3 x 60 = 180 x thêm 1000 . 1s = 1000
    private CountDownTimer downTimer;
    private boolean mTimerRunning;
    private  long mTimeLeftMillis = START_TIME_IN_MILLIS;
    MediaPlayer playerDH;
    int vitri = -1;
    ImageButton imgBtnBackDS;
    ImageView ngoisao1,ngoisao2,ngoisao3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dem_so);
        AnhXa();
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
        GanHinhAdapter();
        startTimer();
        DapAnBH();
        BackHome();


    }
    private void BackHome() {
        imgBtnBackDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
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
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                if(mTimeLeftMillis <=32000) // 30000 - 30s
                {
                    playerDH.release();
                    //Toast.makeText(DemSoActivity.this, "vị trí "+mTimeLeftMillis, Toast.LENGTH_LONG).show();
                }
                pauseTimer();
                Intent intent = new Intent(DemSoActivity.this, BaiHocActivity.class);
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
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private  void  startTimer()
    {
        downTimer = new CountDownTimer(mTimeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                  mTimeLeftMillis = millisUntilFinished;
                  if(mTimeLeftMillis <= 32000) // 30000 - 30s
                  {
                      playerDH = MediaPlayer.create(DemSoActivity.this, R.raw.amthanh30s);
                      playerDH.start();
                  }
                updateCountText();
                if(mTimeLeftMillis <= 2000)
                {
                    pauseTimer();
                    MediaPlayer playerTwo = MediaPlayer.create(DemSoActivity.this, R.raw.traloisai);
                    playerTwo.start();
                    try {
                        textClock.setText("00:01");
                        Thread.sleep(1000);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    textClock.setText("00:00");
                    ngoisao1.setImageResource(R.drawable.ngoisaotrangden);
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
        textClock.setText(timeLeftFormatted);
        if(mTimeLeftMillis >= 60000)
        {
            diem = 3;
            ngoisao3.setImageResource(R.drawable.ngoisaovang);
        }
        if (mTimeLeftMillis < 60000 && mTimeLeftMillis >= 30000) {
            diem = 2;
            ngoisao3.setImageResource(R.drawable.ngoisaotrangden);
        }
        if(mTimeLeftMillis < 30000 )
        {
            diem = 1;
            ngoisao2.setImageResource(R.drawable.ngoisaotrangden);
            ngoisao3.setImageResource(R.drawable.ngoisaotrangden);
        }
    }


    private void DapAnBH() {
        int dapan = Integer.parseInt(DapAn);
        int so1 = dapan + 2;
        int so2 = dapan - 1;
        Random random = new Random();
        int value = random.nextInt((2-0) + 1) + 0;

           switch (value)
           {
               case 0:
                   buttonDemSoMot.setText(DapAn+"");
                   buttonDemSoHai.setText(so1+"");
                   buttonDemSoBa.setText(so2+"");
                   clickChonDapAn(1);
                   break;
               case 1:
                   buttonDemSoMot.setText(so2+"");
                   buttonDemSoHai.setText(DapAn+"");
                   buttonDemSoBa.setText(so1+"");
                   clickChonDapAn(2);
                   break;
               case 2:
                   buttonDemSoMot.setText(so2+"");
                   buttonDemSoHai.setText(so1+"");
                   buttonDemSoBa.setText(DapAn+"");
                   clickChonDapAn(3);
                   break;
           }
    }

    private void clickChonDapAn(final int ketqua)
    {
        buttonDemSoMot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ketqua == 1)
                {
                    MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.traloidung);
                    player.start();
                    if(mTimeLeftMillis <= 32000) // 30000 - 30s
                    {
                        playerDH.release();
                    }
                    DiaLogWin();
                    if(!trangthai.equals("xong"))
                    {
                        NhanVang();
                    }

                }
                else
                {
                    MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.traloisai);
                    player.start();
                    pauseTimer();
                    DiaLogLoser();
                }

            }
        });

        buttonDemSoHai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ketqua == 2)
                {
                    MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.traloidung);
                    player.start();

                    if(mTimeLeftMillis <= 32000) // 30000 - 30s
                    {
                        playerDH.release();
                    }
                    DiaLogWin();
                    if(!trangthai.equals("xong"))
                    {
                        NhanVang();
                    }


                }
                else
                {
                    MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.traloisai);
                    player.start();
                    pauseTimer();
                    DiaLogLoser();
                }
            }
        });

        buttonDemSoBa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ketqua == 3)
                {
                    MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.traloidung);
                    player.start();

                    if(mTimeLeftMillis <= 32000) // 30000 - 30s
                    {
                        playerDH.release();
                    }
                    DiaLogWin();
                    if(!trangthai.equals("xong"))
                    {
                        NhanVang();
                    }

                }
                else
                {
                    MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.traloisai);
                    player.start();
                    pauseTimer();
                    DiaLogLoser();
                }
            }
        });
    }


    private void GanHinhAdapter() {
        recyclerMot.setHasFixedSize(true);
        recyclerHai.setHasFixedSize(true);
        String[] tachChuoi = NoiDung.split("\\*");
        String sothunhat = null, sothuhai = null , phep;
        String pheptoan = null;
       for(int i=0; i<tachChuoi.length;i++)
       {
            sothunhat = tachChuoi[0];
            pheptoan = tachChuoi[1];
            sothuhai  = tachChuoi[2];
       }
        switch (pheptoan)
        {
            case "Cộng":
                phep = "+";
                break;
            case "Trừ":
                phep = "-";
                break;
            case "Nhân":
                phep = "x";
                break;
            case "Chia":
                phep = ":";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pheptoan);
        }
       buttonPhepToanDemSo.setText(phep+"");
        int thunhat = Integer.parseInt(sothunhat);
        int thuhai = Integer.parseInt(sothuhai);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerMot.setLayoutManager(layoutManager);
        ArrayList<DemSoMot> demSoMots = new ArrayList<>();
        for (int i = 1; i<=thunhat; i++)
        {
            demSoMots.add(new DemSoMot(R.drawable.apple));
        }

        DemSoMotAdapter adapter = new DemSoMotAdapter(demSoMots, getApplicationContext());
        recyclerMot.setAdapter(adapter);

        LinearLayoutManager layoutManagerTwo = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
       recyclerHai.setLayoutManager(layoutManagerTwo);
       ArrayList<DemSoMot> demSoHais = new ArrayList<>() ;
        for (int i = 1; i<=thuhai; i++)
        {
            demSoHais.add(new DemSoMot(R.drawable.duahau));
        }

       DemSoMotAdapter adapterHai = new DemSoMotAdapter(demSoHais,getApplicationContext());
       recyclerHai.setAdapter(adapterHai);
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
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                    Intent intent = new Intent(DemSoActivity.this, BaiHocActivity.class);
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
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();

                TruVang(vitri,dialog,"reset");
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                vitri++;
                TruVang(vitri,dialog,"next");

            }
        });
        dialog.show();

    }

    private void LuuTrangThaiHoanThanh() {
        final long time = 90000 - mTimeLeftMillis;
        if(mTimeLeftMillis >= 60000)
        {
            diem = 3;
        }
        if (mTimeLeftMillis < 60000 && mTimeLeftMillis >= 30000) {
            diem = 2;
        }
        if(mTimeLeftMillis < 30000 )
        {
            diem = 1;
        }

        String urls = "http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=thamgia";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
       // final int finalDiem = diem;
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

    public  void DiaLogLoser()
    {
        if(mTimeLeftMillis <= 32000) // 30000 - 30s
        {
            playerDH.release();
        }
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dia_log_loser);
        dialog.setCancelable(false);
        final ImageButton btnBack = (ImageButton) dialog.findViewById(R.id.btnLoser);
        final ImageButton btnReset = (ImageButton) dialog.findViewById(R.id.btnReset);
        final ImageButton btnNext = (ImageButton)dialog.findViewById(R.id.btnCountieBoQua);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                vitri++;
                TruVang(vitri,dialog,"next");

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(DemSoActivity.this, BaiHocActivity.class);
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
                MediaPlayer player = MediaPlayer.create(DemSoActivity.this, R.raw.click_tul);
                player.start();
                TruVang(vitri,dialog,"reset");
            }
        });
        dialog.show();

    }
    private void AnhXa() {
        recyclerMot = findViewById(R.id.recyclerMot);
        recyclerHai = findViewById(R.id.recyclerHai);
        buttonDemSoMot = findViewById(R.id.buttonDemSo1);
        buttonDemSoHai = findViewById(R.id.buttonDemSo2);
        buttonDemSoBa  = findViewById(R.id.buttonDemSo3);
        buttonPhepToanDemSo = findViewById(R.id.buttonPhepToanDemSo);
        textClock = findViewById(R.id.textViewClock);
        imgBtnBackDS = findViewById(R.id.imageButtonBackDS);
         ngoisao1 = findViewById(R.id.imageViewNgoiSao1);
         ngoisao2 = findViewById(R.id.imageViewNgoiSao2);
        ngoisao3= findViewById(R.id.imageViewNgoiSao3);

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
                            Intent intent = new Intent(DemSoActivity.this, DemSoActivity.class);
                            Bundle bundle = new Bundle();
                            BaiHoc baiHoc = getBaiHoc().get(position);
                            bundle.putInt("vitri", position);
                            bundle.putSerializable("dataBaiHoc",baiHoc);
                            intent.putExtra("dulieu", bundle);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                            // Toast.makeText(DemSoActivity.this, "Kết quả:"+vitri, Toast.LENGTH_LONG).show();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimeLeftMillis < 32000) // 30000 - 30s
        {
//            playerDH.stop();
            playerDH.release();
          //  playerDH=null;
        }
        pauseTimer();
    }
    @Override
    public void onBackPressed() {
         DiaLogBack();
    }
}
