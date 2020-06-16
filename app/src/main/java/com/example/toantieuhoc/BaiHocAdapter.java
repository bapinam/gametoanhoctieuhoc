package com.example.toantieuhoc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.toantieuhoc.BaiHocActivity.User;

public class BaiHocAdapter extends  RecyclerView.Adapter<BaiHocAdapter.ViewHolder> {
    public  static  ArrayList<BaiHoc> baiHoc;
    Context context;
    public static ArrayList<BaiHoc> getBaiHoc() {
        return baiHoc;
    }

    public BaiHocAdapter(ArrayList<BaiHoc> baiHoc, Context context) {
        this.baiHoc = baiHoc;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         context = parent.getContext(); // khai bao này,ko cần làm hàm xây dựng
         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         View view = layoutInflater.inflate(R.layout.baihoc_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.txtName.setText(baiHoc.get(position).getTen());
            holder.imgHinh.setImageResource(baiHoc.get(position).getHinhAnh());
        final BaiHoc theLoai = baiHoc.get(position);
            // chạm vào chữ:
        String trangthai = theLoai.getTrangthai();
        if(trangthai.equals("xong"))
        {
            holder.txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        final Dialog dialogTH = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        dialogTH.setContentView(R.layout.dialog_lichsu);
                        dialogTH.setCanceledOnTouchOutside(false);
                        ImageView sao1 = dialogTH.findViewById(R.id.imageViewSao1);
                        ImageView sao2 = dialogTH.findViewById(R.id.imageViewSao2);
                        ImageView sao3 = dialogTH.findViewById(R.id.imageViewSao3);
                        TextView txtTen = dialogTH.findViewById(R.id.textViewBaiHocLS);
                        TextView txtThoigian = dialogTH.findViewById(R.id.textViewThoiGianChoi);
                        String ten = theLoai.getTen();
                        txtTen.setText(ten);
                        int diem = Integer.parseInt(theLoai.getDiem());
                        int thoigian = Integer.parseInt(theLoai.getThoigian());
                        if(diem == 1)
                        {
                            sao2.setImageResource(R.drawable.ngoisaotrangden);
                            sao3.setImageResource(R.drawable.ngoisaotrangden);
                        }
                        if(diem == 2 )
                        {
                            sao3.setImageResource(R.drawable.ngoisaotrangden);
                        }
                    int mTimeLeftMillis = thoigian;
                    int minues = (int) (mTimeLeftMillis / 1000 ) / 60;
                    int seconds = (int) (mTimeLeftMillis / 1000) % 60;
                    String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minues,seconds);
                    txtThoigian.setText(""+timeLeftFormatted);

                    Button thoat =dialogTH.findViewById(R.id.buttonDY);
                    thoat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogTH.dismiss();
                        }
                    });
                        dialogTH.show();
                }
            });
        }

            // chạm vào hình

            holder.imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(context, R.raw.click_tul);
                player.start();
                final Intent[] intent = new Intent[1];
               final Dialog dialogTon = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
               dialogTon.setContentView(R.layout.dialog_tonvang);
                ImageButton btnNo = (ImageButton) dialogTon.findViewById(R.id.btnNoo);
                ImageButton btnYes = (ImageButton) dialogTon.findViewById(R.id.btnYess);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogTon.dismiss();
                    }
                });
                final String ip = BaiHocActivity.ip;
                final  int idnguoichoi = theLoai.getIDNguoiChoi();
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url="http://"+ip+"/gametoantieuhoc/nguoichoi/actionNguoiChoi.php?yc=updateVang&idnguoichoi="+idnguoichoi+"&cv=0";
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                                            SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
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


                        ///////////////////////////////////////////
                        switch (theLoai.getIDTheLoai())
                        {
                            case 1:
                                intent[0] = new Intent(context, SoSanhActivity.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("vitri", position);
                                bundle1.putSerializable("dataBaiHoc",theLoai);
                                intent[0].putExtra("dulieu", bundle1);
                                intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // vì ngoại bên ngoài gì đó,nên bị lỗi,phải thêm cái này vào!
                                context.startActivity(intent[0]);
                                ((Activity)context).finish();

                                break;
                            case 2:
                                intent[0] = new Intent(context, DemSoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("vitri", position);
                                bundle.putSerializable("dataBaiHoc",theLoai);
                                intent[0].putExtra("dulieu", bundle);
                                intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // vì ngoại bên ngoài gì đó,nên bị lỗi,phải thêm cái này vào!
                                context.startActivity(intent[0]);
                                ((Activity)context).finish();


                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + theLoai.getIDTheLoai());
                        }
                        dialogTon.dismiss();
                    }
                });
               dialogTon.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return baiHoc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView imgHinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.textViewTenBai);
            imgHinh = (ImageView)itemView.findViewById(R.id.imageViewDiemSoMot);
        }

    }

}
