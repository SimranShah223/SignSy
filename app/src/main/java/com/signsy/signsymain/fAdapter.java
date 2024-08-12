package com.signsy.signsymain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fAdapter extends RecyclerView.Adapter<fAdapter.fHolder>{

    Context context;
    ArrayList<fItem> fItemArraylist;

    public fAdapter(Context context, ArrayList<fItem> fItemArraylist) {
        this.context = context;
        this.fItemArraylist = fItemArraylist;
    }

    @NonNull
    @Override
    public fAdapter.fHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fcard,parent,false);
        return new fAdapter.fHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull fAdapter.fHolder holder, int position) {
        fItem fItem = fItemArraylist.get(position);
        holder.Star.setText(fItem.Star);
        holder.description.setText(fItem.description);
    }

    @Override
    public int getItemCount() {
        return fItemArraylist.size();
    }

    public class fHolder extends RecyclerView.ViewHolder {
        TextView Star, description;
        public fHolder(@NonNull View itemView) {
            super(itemView);
            Star = itemView.findViewById(R.id.starRatingPlaceholder);
            description = itemView.findViewById(R.id.feedbackPlaceholder);
        }
    }
}
