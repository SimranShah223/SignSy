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

public class SAdapter extends RecyclerView.Adapter<SAdapter.SAHolder>{

    Context context;
    ArrayList<SItem> SItemArraylist;

    public SAdapter(Context context, ArrayList<SItem> SItemArraylist) {
        this.context = context;
        this.SItemArraylist = SItemArraylist;
    }

    @NonNull
    @Override
    public SAdapter.SAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sitem,parent,false);
        return new SAdapter.SAHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SAdapter.SAHolder holder, int position) {
        SItem sItem = SItemArraylist.get(position);
        holder.word.setText(sItem.word);
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignDescription.class);
                intent.putExtra("word",holder.word.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SItemArraylist.size();
    }

    public class SAHolder extends RecyclerView.ViewHolder {
        TextView word;
        ImageView link;
        public SAHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.signNameDict2);
            link = itemView.findViewById(R.id.signThumbnail2);
        }
    }
}
