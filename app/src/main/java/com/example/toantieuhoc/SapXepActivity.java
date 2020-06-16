package com.example.toantieuhoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class SapXepActivity extends AppCompatActivity {

    TextView txtthoigian;
    RecyclerView recyclerViewsapxep;
    Button btnSumbit;
    ArrayList<SapXep> list ;
    String NoiDung ="" ;
    String DapAn ="";
    int IDTheLoai = 0;
    int IDBaiHoc = 0;
    private static final  long START_TIME_IN_MILLIS = 180000; // 180000: 5 số 0 -> 1 phút 60 giay, 3 phút, lấy 3 x 60 = 180 x thêm 1000 . 1s = 1000
    private CountDownTimer downTimer;
    private boolean mTimerRunning;
    private  long mTimeLeftMillis = START_TIME_IN_MILLIS;
    String[] tachChuoi;
    MediaPlayer playerDH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sap_xep);

        AnhXa();
        MoGoiDuLieu();
        GanNoiDunRecyclerView();
        startTimer();
        click_Submit();
    }


    private void MoGoiDuLieu() {
        Intent intent = getIntent();
        BaiHoc baiHoc = (BaiHoc) intent.getSerializableExtra("dataBaiHoc");
        NoiDung = baiHoc.getNoiDung();
        DapAn   = baiHoc.getDapAn();
        IDBaiHoc= baiHoc.getIDBaiHoc();
        IDTheLoai= baiHoc.getIDTheLoai();
    }


    private void click_Submit() {
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(SapXepActivity.this, R.raw.click_tul);
                player.start();
//                for (int i=1; i<=4;i++)
//                {
//                    Toast.makeText(SapXepActivity.this, "Kết: "+i+" tại "+list.get(i).getTextSapXep(), Toast.LENGTH_SHORT).show();
//                }

                if(SoSanh())
                {
                    MediaPlayer playerOne = MediaPlayer.create(SapXepActivity.this, R.raw.traloidung);
                    playerOne.start();
                    pauseTimer();
                    DiaLogWin();
                }
                else
                {
                    MediaPlayer playerTwo = MediaPlayer.create(SapXepActivity.this, R.raw.traloisai);
                    playerTwo.start();
                    pauseTimer();
                    DiaLogLoser();
                }

            }
        });

    }
    private  boolean SoSanh()
    {
        for (int i=0; i<=4; i++)
        {
            if(list.get(i).getTextSapXep().equals(tachChuoi[i]))
            {
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    private  void AnhXa()
    {
        txtthoigian        = findViewById(R.id.textDemThoiGian);
        recyclerViewsapxep = findViewById(R.id.RecyclerSapXep);
        btnSumbit          = findViewById(R.id.buttonDongY);
    }
    private void GanNoiDunRecyclerView() {
        recyclerViewsapxep.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewsapxep.setLayoutManager(manager);
        list = new ArrayList<>();
       // Toast.makeText(SapXepActivity.this, "Ket Qua"+ NoiDung, Toast.LENGTH_LONG).show();
        tachChuoi = NoiDung.split("\\*");
       // Collections.shuffle(Arrays.asList(tachChuoi));
        for(String w: tachChuoi)
        {
            list.add(new SapXep(w +""));
        }
        Collections.shuffle(list);
        SapXepAdapter sapXepAdapter = new SapXepAdapter(list,getApplicationContext());
        recyclerViewsapxep.setAdapter(sapXepAdapter);

        // kéo thả
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewsapxep);

    }

    // kéo thả
    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN
            | ItemTouchHelper.START | ItemTouchHelper.END |ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT |ItemTouchHelper.ACTION_STATE_DRAG
            |ItemTouchHelper.ACTION_STATE_IDLE |ItemTouchHelper.ACTION_STATE_SWIPE|ItemTouchHelper.ANIMATION_TYPE_DRAG|ItemTouchHelper.ANIMATION_TYPE_SWIPE_CANCEL
            |ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(list, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    private  void  startTimer()
    {
        downTimer = new CountDownTimer(mTimeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis = millisUntilFinished;
                if(mTimeLeftMillis <= 32000) // 30000 - 30s
                {
                    playerDH = MediaPlayer.create(SapXepActivity.this, R.raw.amthanh30s);
                    playerDH.start();
                }
                updateCountText();
                if(mTimeLeftMillis <= 2000)
                {
                    MediaPlayer playerTwo = MediaPlayer.create(SapXepActivity.this, R.raw.traloisai);
                    playerTwo.start();
                    txtthoigian.setText("00:00");
                    pauseTimer();
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
        txtthoigian.setText(timeLeftFormatted);
    }

    public  void DiaLogWin() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen); // trong suốt
        dialog.setContentView(R.layout.dia_log_chienthang);
        pauseTimer();
        //
        ImageButton btn2Win = (ImageButton) dialog.findViewById(R.id.btn2win);
        //chọn trở về:
        btn2Win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SapXepActivity.this, BaiHocActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("lophoc", IDLopHoc);
//                bundle.putInt("idBaiHoc", IDBaiHoc);
//                bundle.putString("trangthai","YES");
//                intent.putExtra("dulieu",bundle);
//                startActivity(intent);
                if(mTimeLeftMillis <= 32000) // 30000 - 30s
                {
                    playerDH.stop();
                }
                onStop();
                finish();

            }
        });
        dialog.show();

    }

    public  void DiaLogLoser()
    {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dia_log_loser);
        ImageButton btnBack = (ImageButton) dialog.findViewById(R.id.btnLoser);
        ImageButton btnReset = (ImageButton) dialog.findViewById(R.id.btnReset);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimeLeftMillis <= 32000) // 30000 - 30s
                {
                    playerDH.stop();
                }
                onStop();
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // tải lại chính nó, reset restart
                if(mTimeLeftMillis <= 32000) // 30000 - 30s
                {
                    playerDH.stop();
                }
                onStop();
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0 );
            }
        });
        dialog.show();

    }

}
