package com.example.toantieuhoc;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SapXepAdapter extends RecyclerView.Adapter<SapXepAdapter.HolderSapXep>{
    public ArrayList<SapXep> sapXeps;
    Context context;

    public SapXepAdapter(ArrayList<SapXep> sapXeps, Context context) {
        this.sapXeps = sapXeps;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderSapXep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sapxep_view, parent, false);
        return new HolderSapXep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSapXep holder, final int position) {
        holder.viewSapxep.setText(sapXeps.get(position).getTextSapXep());

    }

    @Override
    public int getItemCount() {
        return sapXeps.size();
    }

    public class HolderSapXep extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView viewSapxep;
        public HolderSapXep(@NonNull final View itemView) {
            super(itemView);
            viewSapxep = itemView.findViewById(R.id.textViewSapXep);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
