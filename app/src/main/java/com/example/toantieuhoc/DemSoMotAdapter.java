package com.example.toantieuhoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DemSoMotAdapter extends  RecyclerView.Adapter<DemSoMotAdapter.MyHolder>{
    ArrayList<DemSoMot> arrayList;
    Context context;

    public DemSoMotAdapter(ArrayList<DemSoMot> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
       View view = layoutInflater.inflate(R.layout.demso_mot,parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.apple.setImageResource(arrayList.get(position).getHinh());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView apple;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            apple = itemView.findViewById(R.id.imageViewDiemSoMot);
        }
    }
}
