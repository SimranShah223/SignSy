package com.signsy.signsymain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UInfoAdapter extends RecyclerView.Adapter<UInfoAdapter.UIHolder> {

    Context context;
    ArrayList<UInfoCard> uItemArrayList;
    TextView totalCount;
    FirebaseFirestore FStore;
    FirebaseAuth auth;
    String userID;

    public UInfoAdapter(Context context, ArrayList<UInfoCard> uItemArrayList) {
        this.context = context;
        this.uItemArrayList = uItemArrayList;
    }

    @NonNull
    @Override
    public UInfoAdapter.UIHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userinfocard, parent, false);
        return new UIHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UInfoAdapter.UIHolder holder, int position) {
        UInfoCard uItem = uItemArrayList.get(position);
        holder.useraType.setText(uItem.UType);
        holder.useraName.setText(uItem.FirstName);
        holder.useraEmail.setText(uItem.Email);
        holder.useraPhone.setText(uItem.PhoneNumber);
    }

    @Override
    public int getItemCount() {
        return uItemArrayList.size();
    }

    public class UIHolder extends RecyclerView.ViewHolder {
        TextView useraType, useraName, useraEmail, useraPhone;

        public UIHolder(@NonNull View itemView) {
            super(itemView);
            useraType = itemView.findViewById(R.id.useraType);
            useraName = itemView.findViewById(R.id.useraName);
            useraEmail = itemView.findViewById(R.id.useraEmail);
            useraPhone = itemView.findViewById(R.id.useraPhone);
        }
    }
}
