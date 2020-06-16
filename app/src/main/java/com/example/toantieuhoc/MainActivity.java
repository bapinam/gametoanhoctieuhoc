package com.example.toantieuhoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.Map;

import static android.view.WindowManager.*;

public class MainActivity extends AppCompatActivity {

    Spinner  spinnerLop;
    ImageButton imgPhongHoc, imgXepHang, imgHoatDong;
    ImageView imgTenU, imgTien;
    EditText nhapDk, nhapDN;
    EditText nhapPassdk, nhapPass, edtTen ;
    int dk = 0;
    Dialog dialog;
    TextView txtTenUser, txtVang;
    ArrayList<DataUser> dataUsers = new ArrayList<>();
    String ip ="192.168.56.1";
    String urlDK ="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=add";//thêm tài khoản
    String urlDN="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=kiemtraTK";//kiểm tra tài khoản
    MediaPlayer playerNhacNen;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        AnhXa();
        KiemTraDangNhap();
        Click_View();
        Sound();
        SetShareVang();
    }

    private void KiemTraDangNhap() {
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        int id = sharedPreferences.getInt("idnguoichoi",-1);
        if(id<0)
        {
            DiaLogDangNhap();
            An();
        }
        int idLOP = sharedPreferences.getInt("idlop",-1);
        switch (idLOP)
        {
            case 1:
            {
                imgTenU.setImageResource(R.drawable.nenlop1);
                break;
            }
            case 2:
            {
                imgTenU.setImageResource(R.drawable.nenlop2);
                break;
            }
            case 3:
            {
                imgTenU.setImageResource(R.drawable.nenlop3);
                break;
            }
            case 4:
            {
                imgTenU.setImageResource(R.drawable.nenlop4);
                break;
            }
            case 5:
            {
                imgTenU.setImageResource(R.drawable.nenlop5);
                break;
            }
            case 6:
            {
                DiaLogChonLop(id);
                break;
            }

        }

        String tenuser = sharedPreferences.getString("tenuser","");
        txtTenUser.setText(tenuser);

    }

    private void An() {

       imgTenU.setVisibility(View.INVISIBLE);
        txtTenUser.setVisibility(View.INVISIBLE);

        imgTien.setVisibility(View.INVISIBLE);

        txtVang.setVisibility(View.INVISIBLE);
    }

    private void SetShareVang() {
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        int vang = sharedPreferences.getInt("vang",0);
       txtVang.setText(String.valueOf(vang));
    }
    private void SetDuLieu(String urls) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urls, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0 ; i<response.length(); i++) {
                           JSONObject object = null;
                            try {
                                object = response.getJSONObject(i);
                                dataUsers.add(new DataUser(
                                   object.getInt("idnguoichoi"),
                                   object.getString("tenuser"),
                                   object.getInt("vang"),
                                   object.getInt("idlop")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("BBB", String.valueOf(e));
                            }

                             int idnguoichoi = dataUsers.get(i).getIdnguoichoi();
                             int vang = dataUsers.get(i).getVang();
                             int idlop=dataUsers.get(i).getIdlop();


                            String tenuser = dataUsers.get(i).getTenuser();
                            imgTenU.setVisibility(View.VISIBLE);
                            txtTenUser.setVisibility(View.VISIBLE);
                            txtTenUser.setText(tenuser);

                            LuuTaiKhoan(idnguoichoi, vang,idlop,tenuser);

                            imgTien.setVisibility(View.VISIBLE);
                            txtVang.setVisibility(View.VISIBLE);
                            txtVang.setText(String.valueOf(vang));
                            switch (idlop)
                            {
                                case 1:
                                {
                                    imgTenU.setImageResource(R.drawable.nenlop1);
                                    break;
                                }
                                case 2:
                                {
                                    imgTenU.setImageResource(R.drawable.nenlop2);
                                    break;
                                }
                                case 3:
                                {
                                    imgTenU.setImageResource(R.drawable.nenlop3);
                                    break;
                                }
                                case 4:
                                {
                                    imgTenU.setImageResource(R.drawable.nenlop4);
                                    break;
                                }
                                case 5:
                                {
                                    imgTenU.setImageResource(R.drawable.nenlop5);
                                    break;
                                }
                                case 6:
                                {
                                    imgTenU.setVisibility(View.INVISIBLE);
                                    txtTenUser.setVisibility(View.INVISIBLE);

                                    imgTien.setVisibility(View.INVISIBLE);
                                    txtVang.setVisibility(View.INVISIBLE);

                                    DiaLogChonLop(idnguoichoi);
                                    break;
                                }

                            }
                      //  Log.d("AAAA", String.valueOf(response));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Lỗi!! Không Nhận Được Dữ Liệu Người Chơi!", Toast.LENGTH_LONG).show();
                        Log.d("AAA", String.valueOf(error));
                       // Log.d("AAAA", response);
                    }
                }
        )
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String , String> params = new HashMap<>();
//                params.put("taikhoan",nhapDN.getText().toString().trim());
//                params.put("matkhau",nhapPass.getText().toString().trim());
//                return params;
//            }
//        }
        ;
        requestQueue.add(jsonArrayRequest);
    }

    private  void LuuTaiKhoan(int idnguoichoi, int vang,int idlop, String tenuser)
    {
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idnguoichoi",idnguoichoi);
        editor.putInt("vang",vang);
        editor.putInt("idlop",idlop);
        editor.putString("tenuser",tenuser);
        editor.apply();
    }
    private void DiaLogChonLop(final int id) {
            final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_chonlop);
            edtTen = dialog.findViewById(R.id.editTextChonTen);
            spinnerLop = dialog.findViewById(R.id.spinnerLop);
            Button luu = dialog.findViewById(R.id.buttonLuuLai);
            dialog.setCancelable(false);
            final String[] lop = {"1","2","3","4","5"};
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, lop);
            spinnerLop.setAdapter(adapter);
            luu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                    player.start();
                    final int i = Integer.parseInt(lop[spinnerLop.getSelectedItemPosition()]);

                    if(edtTen.getText().toString().trim().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Vui Lòng Nhập Tên", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tenuser",edtTen.getText().toString().trim());
                        editor.putInt("idlop",i);
                        editor.apply();
                        String urls="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=updateNguoiChoi";
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.trim().equals("thanhcong"))
                                {
                                    Toast.makeText(getApplicationContext(),"Thành Công", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    txtTenUser.setText(edtTen.getText().toString().trim());
                                    imgTenU.setVisibility(View.VISIBLE);
                                    txtTenUser.setVisibility(View.VISIBLE);

                                    imgTien.setVisibility(View.VISIBLE);
                                    txtVang.setVisibility(View.VISIBLE);
                                    switch (i)
                                    {
                                        case 1:
                                        {
                                            imgTenU.setImageResource(R.drawable.nenlop1);
                                            break;
                                        }
                                        case 2:
                                        {
                                            imgTenU.setImageResource(R.drawable.nenlop2);
                                            break;
                                        }
                                        case 3:
                                        {
                                            imgTenU.setImageResource(R.drawable.nenlop3);
                                            break;
                                        }
                                        case 4:
                                        {
                                            imgTenU.setImageResource(R.drawable.nenlop4);
                                            break;
                                        }
                                        case 5:
                                        {
                                            imgTenU.setImageResource(R.drawable.nenlop5);
                                            break;
                                        }

                                    }

                                }
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
                                params.put("tenuser",edtTen.getText().toString().trim());
                                params.put("idlop", String.valueOf(i));
                                params.put("idnguoichoi",String.valueOf(id));
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            });
        dialog.show();
    }


    private void DiaLogDangNhap() {
     dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_login);
        dialog.setCancelable(false);
         nhapDN = dialog.findViewById(R.id.editTextNhapDN);
         nhapPass = dialog.findViewById(R.id.editTextPassDN);
        Button dangnhap = dialog.findViewById(R.id.buttonDANGnhap);
        Button dangky = dialog.findViewById(R.id.buttonDangKy);

        if(dk == 1)
        {
            nhapDN.setText(nhapDk.getText().toString().trim());
            nhapPass.setText(nhapPassdk.getText().toString().trim());
        }
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                player.start();
                DiaLogDangKy();
                dialog.dismiss();

            }
        });

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                player.start();
                if(nhapDN.getText().toString().trim().isEmpty() || nhapPass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin!!", Toast.LENGTH_LONG).show();
                }
                else {
                   //  Toast.makeText(getApplicationContext(),"Đăng Ký Thành Công"+, Toast.LENGTH_LONG).show();
                    KiemTraTaiKhoan(urlDN);
                }

            }
        });
        dialog.show();
    }

    private void KiemTraTaiKhoan(String urldn) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("thanhcong"))
                {
                    String taikhoan = nhapDN.getText().toString().trim();
                    String matkhau = nhapPass.getText().toString().trim();
                    String urlGet = "http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=duLieuNguoiChoi&taikhoan="+taikhoan+"&matkhau="+matkhau+"";
                     SetDuLieu(urlGet);
                  //  Toast.makeText(getApplicationContext(),"Đăng Nhập Thành Công", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    startActivity(getIntent());
                    overridePendingTransition(0, 0 );
                }
                if(response.trim().equals("thatbai"))
                {
                    Toast.makeText(getApplicationContext(),"Đăng Nhập Thất Bại", Toast.LENGTH_LONG).show();
                    nhapDN.setText("");
                    nhapPass.setText("");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Đăng Nhập Thất Bại !!", Toast.LENGTH_LONG).show();
                Log.d("AAA","Lỗi!!");
            }
      })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("taikhoan",nhapDN.getText().toString().trim());
                params.put("matkhau",nhapPass.getText().toString().trim());
                return params;
            }
        }
       ;
        requestQueue.add(stringRequest);
    }


    private void DiaLogDangKy() {
        final Dialog diaDK = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        diaDK.setContentView(R.layout.dialog_dangky);
        diaDK.setCancelable(false);
         nhapDk = diaDK.findViewById(R.id.editTextNhapDN);
         nhapPassdk = diaDK.findViewById(R.id.editTextPassDN);
        Button dangKy = diaDK.findViewById(R.id.buttonDANGnhap);
        final Button dangNhap = diaDK.findViewById(R.id.buttonDangKy);
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                player.start();
                DiaLogDangNhap();
                diaDK.dismiss();
            }
        });

        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                player.start();
                if(nhapDk.getText().toString().trim().isEmpty() || nhapPassdk.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin!!", Toast.LENGTH_LONG).show();
                }
                else {
                  // Toast.makeText(getApplicationContext(),"Đăng Ký Thành Công"+url, Toast.LENGTH_LONG).show();
                    ThemTaiKhoanDK(urlDK);
                    dk = 1;
                    diaDK.dismiss();
                    DiaLogDangNhap();
                }

            }
        });
        diaDK.show();
    }

    private void ThemTaiKhoanDK(String urls) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("thanhcong"))
                {
                    Toast.makeText(getApplicationContext(),"Đăng Ký Thành Công", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Đăng Ký Thất Bại", Toast.LENGTH_LONG).show();
                        Log.d("AAA","Lỗi!!");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tenuser",nhapDk.getText().toString().trim());
                params.put("taikhoan",nhapDk.getText().toString().trim());
                params.put("matkhau",nhapPassdk.getText().toString().trim());
                params.put("vang","100");
                params.put("idlop","6");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Sound() {
        playerNhacNen = MediaPlayer.create(MainActivity.this, R.raw.nhacnen);
        playerNhacNen.start();
    }

    private void AnhXa() {
        imgPhongHoc = (ImageButton)findViewById(R.id.imageButtonPhongHoc);
        imgXepHang = findViewById(R.id.imageButtonXepHang);
        imgHoatDong= findViewById(R.id.imageButtonHoatDong);
        txtTenUser = findViewById(R.id.textViewTenUser);
        txtVang = findViewById(R.id.textViewVangBH);
        imgTenU = findViewById(R.id.imageTenUser);
        imgTien = findViewById(R.id.imageViewTien);


    }

    private void Click_View() {
            imgPhongHoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                    player.start();
                    sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    int idnguoichoi = sharedPreferences.getInt("idnguoichoi",-1);
                    int vang = sharedPreferences.getInt("vang",0);
                    int idlop = sharedPreferences.getInt("idlop",0);
                    String tenuser = sharedPreferences.getString("tenuser","");
                    Intent intent = new Intent(MainActivity.this,LopHocActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idnguoichoi", idnguoichoi);
                    bundle.putInt("vang", vang);
                    bundle.putInt("idlop",idlop);
                    bundle.putString("tenuser",tenuser);
                    intent.putExtra("dulieuNC",bundle);
                    startActivity(intent);
                    finish();

                }
            });

            imgXepHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                    player.start();
                    sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    int idnguoichoi = sharedPreferences.getInt("idnguoichoi",-1);
                    int vang = sharedPreferences.getInt("vang",0);
                    int idlop = sharedPreferences.getInt("idlop",0);
                    String tenuser = sharedPreferences.getString("tenuser","");
                    Intent intent = new Intent(MainActivity.this,XepHangActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idnguoichoi", idnguoichoi);
                    bundle.putInt("vang", vang);
                    bundle.putInt("idlop",idlop);
                    bundle.putString("tenuser",tenuser);
                    intent.putExtra("dulieuNC",bundle);
                    startActivity(intent);
                    finish();
                }
            });

            imgHoatDong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                    player.start();
                    sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    int idnguoichoi = sharedPreferences.getInt("idnguoichoi", -1);
                    int vang = sharedPreferences.getInt("vang", 0);
                    int idlop = sharedPreferences.getInt("idlop", 0);
                    String tenuser = sharedPreferences.getString("tenuser", "");
                    Intent intent = new Intent(MainActivity.this, TroChuyenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idnguoichoi", idnguoichoi);
                    bundle.putInt("vang", vang);
                    bundle.putInt("idlop", idlop);
                    bundle.putString("tenuser", tenuser);
                    intent.putExtra("dulieuNC", bundle);
                    startActivity(intent);
                    finish();
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
                MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                player.start();

                dialog.dismiss();
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.click_tul);
                player.start();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerNhacNen.release();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        DiaLogBack();
    }
}
