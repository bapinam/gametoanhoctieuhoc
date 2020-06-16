package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.toantieuhoc.BaiHocActivity.ip;

public class TroChuyenActivity extends AppCompatActivity {

    ImageButton ibtnBack;
    int idnguoichoi, vang, idlop;
    String tenuser;

    RecyclerView recyclerNoiDungTN;
    EditText noidungGui;
    Button gui;
    TroChuyenApdater troChuyenApdater;
    public static ArrayList<TroChuyen> troChuyens  = new ArrayList<>();
    ArrayList<TroChuyenPhu> troChuyenPhus = new ArrayList<>();
    String url ="http://"+ip+"/gametoantieuhoc/tinnhan.php";
    String urlNhan="http://"+ip+"/gametoantieuhoc/tinnhanjson.php";
    MyTask myTask = new MyTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tro_chuyen);
        Init();
        Action();
        MoBundle();
        ChatGui();
        Reset();
    }

    private void Init()
    {
        ibtnBack = findViewById(R.id.imageButtonBackTT);
        recyclerNoiDungTN = findViewById(R.id.recyclerViewTinNhan);
        noidungGui = findViewById(R.id.editTextNhapTinNhan);
        gui = findViewById(R.id.buttonGuiTinNhan);
    }
    private void Action() {
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(TroChuyenActivity.this, R.raw.click_tul);
                player.start();
                Intent intent = new Intent(TroChuyenActivity.this, MainActivity.class);
                startActivity(intent);
                MoBundle();
                myTask.cancel();
                finish();
            }
        });

        recyclerNoiDungTN.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
      //  linearLayoutManager.setReverseLayout(true);
        recyclerNoiDungTN.setLayoutManager(linearLayoutManager);
        troChuyenApdater = new TroChuyenApdater(troChuyens, getApplicationContext());
        recyclerNoiDungTN.setAdapter(troChuyenApdater);
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TroChuyenActivity.this, MainActivity.class);
        startActivity(intent);
        MoBundle();
        myTask.cancel();
        finish();
    }



    private void ChatGui() {
        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("thanhcong"))
                        {
                            noidungGui.setText("");
                            noidungGui.getText().clear();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String , String> params = new HashMap<>();
                        params.put("noidung",noidungGui.getText().toString());
                        params.put("tenuser",tenuser);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });



    }

    private void ChatNhan() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, urlNhan, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i< response.length(); i++)
                        {
                            JSONObject object = null;
                            try {
                                object = response.getJSONObject(i);
                                troChuyenPhus.add(new TroChuyenPhu(
                                        object.getString("noidung"),
                                        object.getString("tenuser")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String noidung9 = troChuyenPhus.get(i).getNoidung();
                            String noidung8 = TroChuyenApdater.noidungcuoi;
                            String tenuserND= troChuyenPhus.get(i).getTenuser();
                            if(!noidung9.equals(noidung8)) {
                                troChuyens.add(new TroChuyen(noidung9, tenuserND));
                            }
                                troChuyenPhus.clear();
                        }
                        //Collections.reverse(troChuyens);
                        troChuyenApdater.notifyDataSetChanged();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(arrayRequest);
    }


    private void Reset() {
        Timer timer = new Timer();
        timer.schedule(myTask, 0, 500);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel();
    }
    public class MyTask extends TimerTask {
        @Override
        public void run() {
            ChatNhan();
        }
    }
}
