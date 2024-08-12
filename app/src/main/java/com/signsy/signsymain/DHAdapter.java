package com.signsy.signsymain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DHAdapter extends RecyclerView.Adapter<DHAdapter.DHHolder>{

    Context context;
    ArrayList<DHitem> dHitemArrayList;

    public DHAdapter(Context context, ArrayList<DHitem> dItemArrayList) {
        this.context = context;
        this.dHitemArrayList = dItemArrayList;
    }

    @NonNull
    @Override
    public DHAdapter.DHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.donhisitem,parent,false);
        return new DHAdapter.DHHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DHAdapter.DHHolder holder, int position) {
        DHitem dHitem = dHitemArrayList.get(position);
        holder.FirstName.setText(dHitem.FirstName);
        holder.Description.setText(dHitem.Description);
        holder.StartDate.setText(dHitem.StartDate);
        holder.EndDate.setText(dHitem.EndDate);
    }

    @Override
    public int getItemCount() {
        return dHitemArrayList.size();
    }

    public static class DHHolder extends RecyclerView.ViewHolder{
        TextView FirstName,Description,StartDate,EndDate;
        public DHHolder(@NonNull View itemView) {
            super(itemView);
            FirstName = itemView.findViewById(R.id.nNgoNameHis1);
            Description = itemView.findViewById(R.id.nDonationForHis1);
            StartDate = itemView.findViewById(R.id.nStartHis1);
            EndDate = itemView.findViewById(R.id.nEndHis1);
        }
    }
}
