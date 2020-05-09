package com.example.goldy.yukkerja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pkeahlian;
import com.example.goldy.yukkerja.model.ResponseModelPkeahlian;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeahlianKandidatAdapter extends RecyclerView.Adapter<KeahlianKandidatAdapter.HolderData>  {
    private List<Pkeahlian> mList;
    private Context ctx;

    public KeahlianKandidatAdapter(List<Pkeahlian> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_keahlian_kandidat,viewGroup,false);
        KeahlianKandidatAdapter.HolderData holderData = new KeahlianKandidatAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        Pkeahlian pkeahlian = mList.get(i);
        holderData.tKeahlian.setText(pkeahlian.getKeahlian_nama());
        holderData.tTingkat.setText(pkeahlian.getTingkat());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private TextView tKeahlian, tTingkat;
        private ImageView iHapus;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tKeahlian = (TextView)itemView.findViewById(R.id.tvItemKeahlian);
            tTingkat = (TextView)itemView.findViewById(R.id.tvItemTingkat);
            iHapus = (ImageView)itemView.findViewById(R.id.ivHapusKeahlian);

            iHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pkeahlian pkeahlian = mList.get(getAdapterPosition());

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModelPkeahlian> deleteKeahlianPekerja = apiRequest.deleteKeahlianPekerja(pkeahlian.getPekerja_id(),pkeahlian.getId());
                    deleteKeahlianPekerja.enqueue(new Callback<ResponseModelPkeahlian>() {
                        @Override
                        public void onResponse(Call<ResponseModelPkeahlian> call, Response<ResponseModelPkeahlian> response) {
                            mList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<ResponseModelPkeahlian> call, Throwable t) {

                        }
                    });


                }
            });

        }
    }
}
