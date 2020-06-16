package com.example.toantieuhoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.backup.BackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BaiHocActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBaiHoc;
    private ImageButton imgbtnBackHome;
    public static String  ip ="192.168.56.1";
    String urlGetData;
    ArrayList<NoiDungBaiHoc> arrayNoiDungBaiHocLopMot = new ArrayList<>();
    public ArrayList<BaiHoc> arrayListLop1 = new ArrayList<>();
    BaiHocAdapter baiHocAdapter;
    int REQUEST_CODE_EDIT = 123;
    int dem = 0, dodai=0;
    int lopHoc = 0;
    int theLoai = 0;
    int idnguoichoi;
    int Tongketqua = 0;
    int ketquaDiem = 0;
    ArrayList<DataUser> dataUsers = new ArrayList<>();
    static SharedPreferences sharedPreferences ;
    TextView txtSoVanChoi, textViewTongSao;
    static TextView textViewVangBH;
    Dialog dialogTon, dialogNhan;
    ImageView imageViewSoVan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bai_hoc);
        AnhXa();
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("dulieu");
        if(bundle !=null)
        {
            lopHoc = bundle.getInt("lopHoc",0);
            theLoai= bundle.getInt("theLoai", 0);
          //Toast.makeText(BaiHocActivity.this, "Thể Loại: "+theLoai, Toast.LENGTH_LONG).show();
        }
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        idnguoichoi = sharedPreferences.getInt("idnguoichoi",-1);
        if(idnguoichoi == -1)
        {
            Toast.makeText(getApplicationContext(),"Không Tồn Tại ID Người Chơi", Toast.LENGTH_LONG).show();
        }
        switch (lopHoc)
        {
            case 1:
                urlGetData ="http://"+ip+"/gametoantieuhoc/jsonBHLop1.php?idnguoichoi="+idnguoichoi+"";
                break;
            case 2:
                urlGetData ="http://"+ip+"/gametoantieuhoc/jsonBHLop2.php?idnguoichoi="+idnguoichoi+"";
                break;
            case 3:
                urlGetData ="http://"+ip+"/gametoantieuhoc/jsonBHLop3.php?idnguoichoi="+idnguoichoi+"";
                break;
            case 4:
                urlGetData ="http://"+ip+"/gametoantieuhoc/jsonBHLop4.php?idnguoichoi="+idnguoichoi+"";
                break;
            case 5:
                urlGetData ="http://"+ip+"/gametoantieuhoc/jsonBHLop5.php?idnguoichoi="+idnguoichoi+"";
                break;
        }
        Click_HomeBack();
        GetData(urlGetData);
        GanGiaTri(arrayListLop1);
        SetShareVang();


    }

    private void SetShareVang() {
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        int vang = sharedPreferences.getInt("vang",0);
        User(vang);
    }
    public static void User(int vang) {
        textViewVangBH.setText(String.valueOf(vang));
    }


    private void GanGiaTri(ArrayList<BaiHoc> arrayList) {
        recyclerViewBaiHoc.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewBaiHoc.setLayoutManager(linearLayoutManager);
        baiHocAdapter = new BaiHocAdapter(arrayList,getApplicationContext());
        recyclerViewBaiHoc.setAdapter(baiHocAdapter);

    }

    private void Click_HomeBack() {
        imgbtnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(BaiHocActivity.this, R.raw.click_tul);
                player.start();
                finish();
            }
        });
    }

    private  void AnhXa()
    {
        imgbtnBackHome = findViewById(R.id.imageButtonBackHomeLop);
        recyclerViewBaiHoc = findViewById(R.id.recyclerViewBaiHoc);
        txtSoVanChoi=findViewById(R.id.txtSoVanChoi);
        textViewVangBH=findViewById(R.id.textViewVangBH);
        imageViewSoVan = findViewById(R.id.imageViewSoVan);
        textViewTongSao = findViewById(R.id.textViewTongSao);

    }

    public void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i< response.length(); i++)
                        {
                            JSONObject object = null;
                            try {
                                object = response.getJSONObject(i);
                                arrayNoiDungBaiHocLopMot.add(new NoiDungBaiHoc(
                                       object.getInt("IDBaiHoc"),
                                       object.getString("TenBaiHoc"),
                                       object.getString("NoiDung"),
                                       object.getString("DapAn"),
                                       object.getInt("IDTheLoai"),
                                       object.getInt("IDLopHoc"),
                                       object.getString("trangthai"),
                                        object.getString("diem"),
                                        object.getString("thoigian")
                               ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            //  String noidung =  arrayNoiDungBaiHocLopMot.get(3);
                            int IDBaiHoc = arrayNoiDungBaiHocLopMot.get(i).getIdBaiHoc();
                            int IDTheLoai= arrayNoiDungBaiHocLopMot.get(i).getIdTheLoai();
                            String tenBaiHoc = arrayNoiDungBaiHocLopMot.get(i).getTenBaiHoc();
                            String NoiDung = arrayNoiDungBaiHocLopMot.get(i).getNoiDung();
                            String DapAn = arrayNoiDungBaiHocLopMot.get(i).getDapAn();
                            int IDLopHoc = arrayNoiDungBaiHocLopMot.get(i).getIDLopHoc();
                            String trangthai = arrayNoiDungBaiHocLopMot.get(i).getTrangthai();
                            String diem = arrayNoiDungBaiHocLopMot.get(i).getDiem();
                            String thoigian = arrayNoiDungBaiHocLopMot.get(i).getThoigian();

                            if(!diem.equals("null"))
                            {
                              ketquaDiem = Integer.parseInt(diem);
                            }
                            else
                            {
                                ketquaDiem = 0;
                            }

                            switch (theLoai)
                            {
                                case 1:
                                    if(IDTheLoai == 1 )
                                    {
                                        if(trangthai.equals("xong"))
                                        {
                                            arrayListLop1.add(new BaiHoc(R.drawable.sosanhht,tenBaiHoc, IDBaiHoc, IDTheLoai, NoiDung, DapAn,IDLopHoc,idnguoichoi,trangthai,diem,thoigian));
                                            dem++;
                                        }
                                        else
                                        {
                                            arrayListLop1.add(new BaiHoc(R.drawable.sosanh,tenBaiHoc, IDBaiHoc, IDTheLoai, NoiDung, DapAn,IDLopHoc,idnguoichoi,trangthai,diem,thoigian));
                                        }
                                        dodai++;
                                        Tongketqua = Tongketqua + ketquaDiem;

                                    }
                                    break;
                                case 2:
                                    if(IDTheLoai == 2 )
                                    {
                                        if(trangthai.equals("xong"))
                                        {
                                            arrayListLop1.add(new BaiHoc(R.drawable.demsohoanthanh,tenBaiHoc, IDBaiHoc, IDTheLoai, NoiDung, DapAn,IDLopHoc,idnguoichoi,trangthai,diem,thoigian));
                                            dem++;
                                        }
                                        else
                                        {
                                            arrayListLop1.add(new BaiHoc(R.drawable.demso,tenBaiHoc, IDBaiHoc, IDTheLoai, NoiDung, DapAn,IDLopHoc,idnguoichoi,trangthai,diem,thoigian));
                                        }
                                       dodai++;
                                        Tongketqua = Tongketqua + ketquaDiem;

                                    }
                                    break;
                                case 3:


                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + theLoai);
                            }

                            txtSoVanChoi.setText(dem+"/"+dodai);
                            textViewTongSao.setText(Tongketqua+"/"+(dodai*3));
                       }
                        baiHocAdapter.notifyDataSetChanged();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(arrayRequest);
    }

    public  void DiaLogNhanVang()
    {
        final Dialog dialogNV = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialogNV.setContentView(R.layout.dialog_nhanvang);
        ImageView nhanvang = dialogNV.findViewById(R.id.imageNhanVang);
        nhanvang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNV.dismiss();
            }
        });
        dialogNV.show();
    }


}
