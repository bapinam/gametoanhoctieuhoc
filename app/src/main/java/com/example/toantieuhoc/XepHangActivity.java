package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.toantieuhoc.BaiHocActivity.ip;

public class XepHangActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btnSoSanh, btnDemSo;
    ImageButton imgThoatXH;
    RecyclerView recyclerViewXH;
    ArrayList<XepHangDuLieu> xepHangDuLieus = new ArrayList<>();
    ArrayList<XepHang> xepHangArrayList = new ArrayList<>();;
    XepHangAdapter xepHangAdapter;
    int idnguoichoi, vang, idlop;
    String tenuser;
    List<String> listChonLop =  new ArrayList<>();
    List<String> listChonTheLoai = new ArrayList<>();
    TextView txtTop_You, txtTen_You, txtDiem_You, txtThoiGian_You;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_xep_hang);
        Init();
        MoBundle();
        Action();
        GanGiaoDienRecyclerView();
        listChonLop.clear();
        listChonTheLoai.clear();
        listChonLop.add("1");
        listChonTheLoai.add("demso");
        GetDuLieu();
    }


    private void Init() {
        btn1 = findViewById(R.id.buttonLopMot);
        btn2 = findViewById(R.id.buttonLopHai);
        btn3 = findViewById(R.id.buttonLopBa);
        btn4 = findViewById(R.id.buttonLopBon);
        btn5 = findViewById(R.id.buttonLopNam);

        btnDemSo = findViewById(R.id.buttonDemSo);
        btnSoSanh= findViewById(R.id.buttonSoSanh);
        imgThoatXH = findViewById(R.id.imageButtonThoatXH);
        recyclerViewXH = findViewById(R.id.recyclerViewXH);

        txtTop_You = findViewById(R.id.imageViewTOP_you);
        txtTen_You = findViewById(R.id.txtUserTOP_you);
        txtDiem_You = findViewById(R.id.textViewDiemTOP_you);
        txtThoiGian_You = findViewById(R.id.textViewThoiGianTOP_you);
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

    private void GanGiaoDienRecyclerView() {
        recyclerViewXH.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewXH.setLayoutManager(linearLayoutManager);
        xepHangAdapter = new XepHangAdapter(xepHangArrayList,getApplicationContext());
        recyclerViewXH.setAdapter(xepHangAdapter);
    }

    private void Action() {
        imgThoatXH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(XepHangActivity.this, MainActivity.class);
                startActivity(intent);
                MoBundle();
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();
                listChonLop.clear();
                listChonLop.add("1");
                LuuChonButtonLop();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();

                listChonLop.clear();
                listChonLop.add("2");
                LuuChonButtonLop();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();

                listChonLop.clear();
                listChonLop.add("3");
                LuuChonButtonLop();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();

                listChonLop.clear();
                listChonLop.add("4");
                LuuChonButtonLop();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();

                listChonLop.clear();
                listChonLop.add("5");
                LuuChonButtonLop();
            }
        });

        btnDemSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();
                listChonTheLoai.clear();
                listChonTheLoai.add("demso");
                LuuChonButtonTheLoai();
            }
        });

        btnSoSanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(XepHangActivity.this, R.raw.click_tul);
                player.start();

                listChonTheLoai.clear();
                listChonTheLoai.add("sosanh");
                LuuChonButtonTheLoai();
            }
        });
    }

    private  void LuuChonButtonLop()
    {
        String chonLop = listChonLop.get(0).toString();
        switch (chonLop)
        {
            case "1":
            {
                btn1.setBackgroundColor(Color.parseColor("#ff7f50"));
                btn2.setBackgroundColor(Color.parseColor("#FFC107"));
                btn3.setBackgroundColor(Color.parseColor("#FFC107"));
                btn4.setBackgroundColor(Color.parseColor("#FFC107"));
                btn5.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
            case "2":
            {
                btn2.setBackgroundColor(Color.parseColor("#ff7f50"));
                btn1.setBackgroundColor(Color.parseColor("#FFC107"));
                btn3.setBackgroundColor(Color.parseColor("#FFC107"));
                btn4.setBackgroundColor(Color.parseColor("#FFC107"));
                btn5.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
            case "3":
            {
                btn3.setBackgroundColor(Color.parseColor("#ff7f50"));
                btn2.setBackgroundColor(Color.parseColor("#FFC107"));
                btn1.setBackgroundColor(Color.parseColor("#FFC107"));
                btn4.setBackgroundColor(Color.parseColor("#FFC107"));
                btn5.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
            case "4":
            {
                btn4.setBackgroundColor(Color.parseColor("#ff7f50"));
                btn2.setBackgroundColor(Color.parseColor("#FFC107"));
                btn3.setBackgroundColor(Color.parseColor("#FFC107"));
                btn1.setBackgroundColor(Color.parseColor("#FFC107"));
                btn5.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
            case "5":
            {
                btn5.setBackgroundColor(Color.parseColor("#ff7f50"));
                btn2.setBackgroundColor(Color.parseColor("#FFC107"));
                btn3.setBackgroundColor(Color.parseColor("#FFC107"));
                btn4.setBackgroundColor(Color.parseColor("#FFC107"));
                btn1.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
        }
    }

    private  void LuuChonButtonTheLoai()
    {
        String chonTL = listChonTheLoai.get(0).toString();
        switch (chonTL)
        {
            case "demso":
            {
                btnDemSo.setBackgroundColor(Color.parseColor("#ff7f50"));
                btnSoSanh.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
            case "sosanh":
            {
                btnSoSanh.setBackgroundColor(Color.parseColor("#ff7f50"));
                btnDemSo.setBackgroundColor(Color.parseColor("#FFC107"));
                xepHangArrayList.clear();
                xepHangDuLieus.clear();
                GetDuLieu();
                break;
            }
        }
    }

    private void GetDuLieu()
    {
        String chonLop = listChonLop.get(0).toString();
        String chonTL  = listChonTheLoai.get(0).toString();
        txtTop_You.setText("");
        txtTop_You.setBackgroundResource(R.drawable.top);
        txtTen_You.setText("");
        txtDiem_You.setText("");
        txtThoiGian_You.setText("");
        int lopXH = Integer.parseInt(chonLop);
        //Toast.makeText(this, ""+lopXH, Toast.LENGTH_SHORT).show();
        String url ="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=xephang&idlop="+lopXH+"&theloai="+chonTL+"";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0 ; i< response.length(); i++)
                        {
                            JSONObject object = null;
                            try {
                                object = response.getJSONObject(i);
                                xepHangDuLieus.add(new XepHangDuLieu(
                                        object.getInt("idnguoichoi"),
                                        object.getString("tenuser"),
                                        object.getString("tongdiem"),
                                        object.getString("tongthoigian")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("BBB",String.valueOf(e));
                            }
                            int idnc = xepHangDuLieus.get(i).getIdnguoichoi();
                            String tennguoichoi = xepHangDuLieus.get(i).getTenuser();
                            String tongdiem = xepHangDuLieus.get(i).getTongdiem();
                            String tongthoigian = xepHangDuLieus.get(i).getTongthoigian();
                            switch (i)
                            {
                                case 0:
                                {
                                    if(idnc == idnguoichoi)
                                    {
                                        txtTop_You.setText("");
                                        txtTop_You.setBackgroundResource(R.drawable.top1);
                                        txtTen_You.setText(tennguoichoi);
                                        txtDiem_You.setText(tongdiem);
                                        txtThoiGian_You.setText(tongthoigian);
                                    }
                                    xepHangArrayList.add(new XepHang(R.drawable.top1," ",idnc,tennguoichoi,tongdiem,tongthoigian ));
                                    break;
                                }
                                case 1:
                                {
                                    if(idnc == idnguoichoi)
                                    {
                                        txtTop_You.setText("");
                                        txtTop_You.setBackgroundResource(R.drawable.top2);
                                        txtTen_You.setText(tennguoichoi);
                                        txtDiem_You.setText(tongdiem);
                                        txtThoiGian_You.setText(tongthoigian);
                                    }
                                    xepHangArrayList.add(new XepHang(R.drawable.top2," ",idnc,tennguoichoi,tongdiem,tongthoigian ));
                                    break;
                                }
                                case 2:
                                {
                                    if(idnc == idnguoichoi)
                                    {
                                        txtTop_You.setText("");
                                        txtTop_You.setBackgroundResource(R.drawable.top3);
                                        txtTen_You.setText(tennguoichoi);
                                        txtDiem_You.setText(tongdiem);
                                        txtThoiGian_You.setText(tongthoigian);
                                    }
                                    xepHangArrayList.add(new XepHang(R.drawable.top3," ",idnc,tennguoichoi,tongdiem,tongthoigian ));
                                    break;
                                }
                                default:
                                {
                                    int diem = i + 1;
                                    if(idnc == idnguoichoi)
                                    {
                                        txtTop_You.setText(String.valueOf(diem));
                                        txtTop_You.setBackgroundResource(R.drawable.top);
                                        txtTen_You.setText(tennguoichoi);
                                        txtDiem_You.setText(tongdiem);
                                        txtThoiGian_You.setText(tongthoigian);
                                    }
                                    xepHangArrayList.add(new XepHang(R.drawable.top,String.valueOf(diem),idnc,tennguoichoi,tongdiem,tongthoigian ));
                                    break;
                                }
                            }
                        }
                        xepHangAdapter.notifyDataSetChanged();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AAAA",String.valueOf(error));
                    }
                });
        requestQueue.add(arrayRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(XepHangActivity.this, MainActivity.class);
        startActivity(intent);
        MoBundle();
        finish();
    }
}
