package com.example.toantieuhoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TroChuyenApdater extends RecyclerView.Adapter<TroChuyenApdater.MyHolder> {
    ArrayList<TroChuyen> troChuyenArrayList;
    Context context;
    public static  String noidungcuoi;
    public TroChuyenApdater(ArrayList<TroChuyen> troChuyenArrayList, Context context) {
        this.troChuyenArrayList = troChuyenArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_tinnhan,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String noidung = troChuyenArrayList.get(position).getNoidung();
        String nguoigui= troChuyenArrayList.get(position).getTenuser();
        holder.txtNoiDung.setText(nguoigui+" :  "+noidung);
        if(position!=0)
        {
            noidungcuoi = troChuyenArrayList.get(troChuyenArrayList.size()-1).toString();
            String n1 = troChuyenArrayList.get(position).getNoidung();
            String n2 = troChuyenArrayList.get(position-1).getNoidung();
            if(n1.equals(n2))
            {
                troChuyenArrayList.remove(position);
            }
        }
//        if(position>=8)
//        {
//            troChuyenArrayList.clear();
//            TroChuyenActivity.troChuyens.clear();
//        }
    }

    @Override
    public int getItemCount() {
        return troChuyenArrayList.size();
    }

    public  class  MyHolder extends RecyclerView.ViewHolder
    {

        TextView txtNoiDung;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtNoiDung = itemView.findViewById(R.id.textViewTinNhan);
        }
    }
}
