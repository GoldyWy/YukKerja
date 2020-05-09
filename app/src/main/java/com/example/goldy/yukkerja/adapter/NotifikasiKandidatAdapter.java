package com.example.goldy.yukkerja.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.DetailLowonganKandidat;
import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Notifikasi;
import com.example.goldy.yukkerja.model.Perusahaan;
import com.example.goldy.yukkerja.model.ResponseModelNotifikasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiKandidatAdapter extends RecyclerView.Adapter<NotifikasiKandidatAdapter.HolderData> {
    private List<Notifikasi> mList;
    private Context ctx;
    RetroServer retroServer;

    public NotifikasiKandidatAdapter(List<Notifikasi> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
        retroServer = new RetroServer();
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notifikasi_kandidat,viewGroup,false);
        NotifikasiKandidatAdapter.HolderData holderData = new NotifikasiKandidatAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        Notifikasi notifikasi = mList.get(i);
        Perusahaan perusahaan = notifikasi.getPerusahaan();
        Lowongan lowongan = notifikasi.getLowongan();
        if (notifikasi.getStatus().equals("0")){
            holderData.item.setBackgroundColor(ctx.getResources().getColor(R.color.colorNotifUnread));
        }

        if (perusahaan.getFoto()!=null){
            Glide.with(ctx).load(retroServer.url()+perusahaan.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holderData.iFoto);
        }

        holderData.tJudul.setText(lowongan.getJudul());
        holderData.tNama.setText(perusahaan.getNama());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private ImageView iFoto;
        private TextView tNama, tJudul;
        private LinearLayout item;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            iFoto = (ImageView)itemView.findViewById(R.id.ivFotoPerusahaanNotif);
            tNama = (TextView) itemView.findViewById(R.id.tvPerusahaanNotif);
            tJudul = (TextView) itemView.findViewById(R.id.tvJudulNotif);
            item = (LinearLayout) itemView.findViewById(R.id.itemNotifikasi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Notifikasi notifikasi = mList.get(getAdapterPosition());

                    if (notifikasi.getStatus().equals("0")){
                        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelNotifikasi> updateNotifkasi = apiRequest.updateNotifkasi(notifikasi.getId());
                        updateNotifkasi.enqueue(new Callback<ResponseModelNotifikasi>() {
                            @Override
                            public void onResponse(Call<ResponseModelNotifikasi> call, Response<ResponseModelNotifikasi> response) {
                                Notifikasi notifikasi = mList.get(getAdapterPosition());
                                item.setBackground(ctx.getDrawable(R.drawable.border));
                                Intent i = new Intent(ctx, DetailLowonganKandidat.class);
                                i.putExtra("id", notifikasi.getLowongan_id());
                                ctx.startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<ResponseModelNotifikasi> call, Throwable t) {

                            }
                        });
                    }else{
                        Intent i = new Intent(ctx, DetailLowonganKandidat.class);
                        i.putExtra("id", notifikasi.getLowongan_id());
                        ctx.startActivity(i);
                        notifyDataSetChanged();
                    }



                }
            });

        }
    }
}
