package com.example.goldy.yukkerja.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.PendidikanKandidat;
import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.UbahPendidikan;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Pendidikan;
import com.example.goldy.yukkerja.model.ResponseModelPendidikan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendidikanKandidatAdapter extends RecyclerView.Adapter<PendidikanKandidatAdapter.HolderData> {
    private List<Pendidikan> mList;
    private Context ctx;

    public PendidikanKandidatAdapter(List<Pendidikan> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PendidikanKandidatAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pendidikan_kandidat,viewGroup,false);
        PendidikanKandidatAdapter.HolderData holderData = new PendidikanKandidatAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull PendidikanKandidatAdapter.HolderData holderData, int i) {
        Pendidikan pendidikan = mList.get(i);

        holderData.tInstitusi.setText(pendidikan.getInstitusi());
        holderData.tWisuda.setText(pendidikan.getTahun_wisuda());
        holderData.tTingkat.setText(pendidikan.getKualifikasi()+" jurusan "+ pendidikan.getJurusan());
        holderData.tNilai.setText(pendidikan.getNilai_akhir());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private TextView tInstitusi, tWisuda, tTingkat, tNilai;
        private ImageView iUbah, iHapus;

        public HolderData(@NonNull View itemView) {
            super(itemView);


            tInstitusi = (TextView)itemView.findViewById(R.id.tvInstitusiItem);
            tWisuda = (TextView)itemView.findViewById(R.id.tvWisudaItem);
            tTingkat = (TextView)itemView.findViewById(R.id.tvTingkatJurusanItem);
            tNilai = (TextView)itemView.findViewById(R.id.tvNilaiItem);
            iUbah = (ImageView)itemView.findViewById(R.id.ivUbahPendidikan);
            iHapus = (ImageView)itemView.findViewById(R.id.ivHapusPendidikan);

            iUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pendidikan pendidikan = mList.get(getAdapterPosition());

                    Intent i = new Intent(ctx, UbahPendidikan.class);
                    i.putExtra("id", pendidikan.getId());
                    i.putExtra("nama", pendidikan.getInstitusi());
                    i.putExtra("bulan", pendidikan.getBulan_wisuda());
                    i.putExtra("tahun", pendidikan.getTahun_wisuda());
                    i.putExtra("kualifikasi", pendidikan.getKualifikasi());
                    i.putExtra("jurusan", pendidikan.getJurusan());
                    i.putExtra("nilai", pendidikan.getNilai_akhir());
                    ctx.startActivity(i);
                }
            });


            iHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Pendidikan pendidikan = mList.get(getAdapterPosition());

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle("Hapus pendidikan di "+ pendidikan.getInstitusi()+" ?");
                    builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            retrofit2.Call<ResponseModelPendidikan> deletePendidikan = apiRequest.deletePendidikan(pendidikan.getId());
                            deletePendidikan.enqueue(new Callback<ResponseModelPendidikan>() {
                                @Override
                                public void onResponse(Call<ResponseModelPendidikan> call, Response<ResponseModelPendidikan> response) {
                                    String status = response.body().getStatus();
                                    String message = response.body().getMessage();
                                    Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ctx, PendidikanKandidat.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    ctx.startActivity(i);

                                }

                                @Override
                                public void onFailure(Call<ResponseModelPendidikan> call, Throwable t) {
                                    Toast.makeText(ctx,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();


                }
            });


        }
    }
}
