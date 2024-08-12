package com.signsy.signsymain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class DAdapter extends RecyclerView.Adapter<DAdapter.DCHolder> {


    Context context;
    ArrayList<DItem> dItemArrayList;
    TextView totalCount;
    FirebaseFirestore FStore;
    FirebaseAuth auth;
    public DAdapter(Context context, ArrayList<DItem> dItemArrayList) {
        this.context = context;
        this.dItemArrayList = dItemArrayList;
    }

    @NonNull
    @Override
    public DAdapter.DCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.donationcard,parent,false);
        return new DCHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DCHolder holder, int position) {
        DItem dItem = dItemArrayList.get(position);
        holder.FirstName.setText(dItem.FirstName);
        holder.Description.setText(dItem.Description);
        holder.StartDate.setText(dItem.StartDate);
        holder.EndDate.setText(dItem.EndDate);
        auth = FirebaseAuth.getInstance();
        String userid = auth.getCurrentUser().getUid();
        holder.applyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FStore = FirebaseFirestore.getInstance();
                String id = holder.FirstName + holder.StartDate.toString();
                DocumentReference dr = FStore.collection("Applied").document(id).collection("DescpritionofId").document();
                Map<String,Object> UserInfo = new HashMap<>();
                UserInfo.put("FirstName",holder.FirstName);
                holder.applyButton1.setText("Applied");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dItemArrayList.size();
    }

    public static class DCHolder extends RecyclerView.ViewHolder{
        TextView FirstName,Description,StartDate,EndDate;
        Button applyButton1;
        public DCHolder(@NonNull View itemView) {
            super(itemView);
            FirstName = itemView.findViewById(R.id.gNgoNameHis);
            Description = itemView.findViewById(R.id.gDonationForHis);
            StartDate = itemView.findViewById(R.id.gStartHis);
            EndDate = itemView.findViewById(R.id.gEndHis);
            applyButton1 = itemView.findViewById(R.id.applyButton1);
        }
    }
}
