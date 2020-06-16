package com.example.toantieuhoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.toantieuhoc.R.drawable.top;

public class XepHangAdapter extends RecyclerView.Adapter<XepHangAdapter.XepHangHolder>{

    ArrayList<XepHang> xepHangArrayList;
    Context context;

    public XepHangAdapter(ArrayList<XepHang> xepHangArrayList, Context context) {
        this.xepHangArrayList = xepHangArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public XepHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_xephang, parent, false);
        return new XepHangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XepHangHolder holder, int position) {
            holder.imgTop.setBackgroundResource(xepHangArrayList.get(position).getHinh());
            holder.imgTop.setText(xepHangArrayList.get(position).getHang());
            holder.tennc.setText(xepHangArrayList.get(position).getTennguoichoi());
            holder.sodiem.setText(xepHangArrayList.get(position).getTongdiem());
            holder.sothoigian.setText(xepHangArrayList.get(position).getTongthoigian());

    }

    @Override
    public int getItemCount() {
        return xepHangArrayList.size();
    }

    public class XepHangHolder extends RecyclerView.ViewHolder
    {

        TextView imgTop, tennc,sodiem, sothoigian;
        public XepHangHolder(@NonNull View itemView) {
            super(itemView);
            imgTop = itemView.findViewById(R.id.imageViewTOP);
            tennc  = itemView.findViewById(R.id.txtUserTOP);
            sodiem = itemView.findViewById(R.id.textViewDiemTOP);
            sothoigian = itemView.findViewById(R.id.textViewThoiGianTOP);
        }
    }
}
