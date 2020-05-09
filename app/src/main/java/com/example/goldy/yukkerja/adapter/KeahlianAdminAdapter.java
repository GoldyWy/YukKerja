package com.example.goldy.yukkerja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.model.Keahlian;
import com.example.goldy.yukkerja.model.Pkeahlian;

import java.util.List;

public class KeahlianAdminAdapter extends RecyclerView.Adapter<KeahlianAdminAdapter.HolderData>  {
    private List<Keahlian> mList;
    private Context ctx;

    public KeahlianAdminAdapter(List<Keahlian> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public KeahlianAdminAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_keahlian_admin,viewGroup,false);
        KeahlianAdminAdapter.HolderData holderData = new KeahlianAdminAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull KeahlianAdminAdapter.HolderData holderData, int i) {
        int no = i+1;
        holderData.tKeahlian.setText(no+". "+mList.get(i).getNama());
        holderData.tIdKeahlian.setText(mList.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private TextView tKeahlian, tIdKeahlian;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tKeahlian = (TextView)itemView.findViewById(R.id.tvItemKeahlianAdmin);
            tIdKeahlian = (TextView)itemView.findViewById(R.id.tvIdKeahlian);


        }
    }
}
