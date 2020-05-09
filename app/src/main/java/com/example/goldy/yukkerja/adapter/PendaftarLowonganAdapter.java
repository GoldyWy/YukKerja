package com.example.goldy.yukkerja.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.DetailLowonganPerusahaan;
import com.example.goldy.yukkerja.DetailPendaftar;
import com.example.goldy.yukkerja.LoginPage;
import com.example.goldy.yukkerja.PendaftarLowongan;
import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.UtamaPerusahaan;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Pekerja;
import com.example.goldy.yukkerja.model.Pendaftar;
import com.example.goldy.yukkerja.model.Perusahaan;
import com.example.goldy.yukkerja.model.ResponseModelPendaftar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendaftarLowonganAdapter extends RecyclerView.Adapter<PendaftarLowonganAdapter.HolderData> {
    private List<Pendaftar> mList;
    private Context ctx;
    private Pendaftar pendaftar;

    public PendaftarLowonganAdapter(List<Pendaftar> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PendaftarLowonganAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pendaftar_lowongan,viewGroup,false);
        PendaftarLowonganAdapter.HolderData holderData = new PendaftarLowonganAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull PendaftarLowonganAdapter.HolderData holderData, int i) {
        pendaftar = mList.get(i);

        Pekerja pekerja = pendaftar.getPekerja();


        holderData.tNama.setText(pekerja.getNama_depan()+" "+pekerja.getNama_belakang());

        if (pekerja.getGaji_harapan() != null){

            holderData.tGaji.setText(Rupiah(Double.parseDouble(pekerja.getGaji_harapan())));
        }else {
            holderData.tGaji.setText(Rupiah(Double.parseDouble("0")));
        }

        if (!pendaftar.getStatus().equals("2")){
            holderData.bTolak.setVisibility(View.GONE);
            holderData.bTerima.setVisibility(View.GONE);
        }
        if (pendaftar.getStatus().equals("1")){
            holderData.tTerima.setVisibility(View.VISIBLE);
        }
        if (pendaftar.getStatus().equals("0")){
            holderData.tTolak.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private TextView tNama, tGaji, tTerima, tTolak;
        private Button bTerima, bTolak;


        public HolderData(@NonNull final View itemView) {
            super(itemView);

            tNama = (TextView)itemView.findViewById(R.id.tvNamaPendaftar);
            tGaji = (TextView)itemView.findViewById(R.id.tvGajiHarapan);
            tTerima = (TextView)itemView.findViewById(R.id.tvTerima);
            tTolak = (TextView)itemView.findViewById(R.id.tvTolak);
            bTerima = (Button)itemView.findViewById(R.id.btnTerima);
            bTolak = (Button)itemView.findViewById(R.id.btnTolak);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pendaftar pendaftar = mList.get(getAdapterPosition());
                    Intent i = new Intent(ctx, DetailPendaftar.class);
                    i.putExtra("id", pendaftar.getPekerja_id());
                    ctx.startActivity(i);
                }
            });

            bTerima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Pendaftar pendaftar = mList.get(getAdapterPosition());
                    Pekerja pekerja = pendaftar.getPekerja();
                    final Lowongan lowongan = pendaftar.getLowongan();
                    AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                    alert.setTitle("Terima "+pekerja.getNama_depan()+" untuk lanjut ke tahap berikutnya ?");
                    alert.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            retrofit2.Call<ResponseModelPendaftar> updateStatusPendaftar = apiRequest.updateStatusPendaftar(pendaftar.getId(),"1");
                            updateStatusPendaftar.enqueue(new Callback<ResponseModelPendaftar>() {
                                @Override
                                public void onResponse(Call<ResponseModelPendaftar> call, Response<ResponseModelPendaftar> response) {
                                    if (response.body().getStatus()!= null)
                                    {
                                        Toast.makeText(ctx,response.body().getMessage(),Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(ctx, PendaftarLowongan.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("id",pendaftar.getLowongan_id());
                                        i.putExtra("judul",lowongan.getJudul());
                                        ctx.startActivity(i);

                                    }else {
                                        Toast.makeText(ctx,"Oops ada kesalahan",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(ctx, PendaftarLowongan.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("id",pendaftar.getLowongan_id());
                                        i.putExtra("judul",lowongan.getJudul());
                                        ctx.startActivity(i);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModelPendaftar> call, Throwable t) {
                                    Toast.makeText(ctx,"Oops ada kesalahan",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ctx, PendaftarLowongan.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.putExtra("id",pendaftar.getLowongan_id());
                                    i.putExtra("judul",lowongan.getJudul());
                                    ctx.startActivity(i);
                                }
                            });
                        }
                    });
                    alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();

                }
            });

            bTolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Pendaftar pendaftar = mList.get(getAdapterPosition());
                    Pekerja pekerja = pendaftar.getPekerja();
                    final Lowongan lowongan = pendaftar.getLowongan();
                    AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                    alert.setTitle("Apakah "+pekerja.getNama_depan()+" tidak sesuai kriteria anda ?");
                    alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            retrofit2.Call<ResponseModelPendaftar> updateStatusPendaftar = apiRequest.updateStatusPendaftar(pendaftar.getId(),"0");
                            updateStatusPendaftar.enqueue(new Callback<ResponseModelPendaftar>() {
                                @Override
                                public void onResponse(Call<ResponseModelPendaftar> call, Response<ResponseModelPendaftar> response) {
                                    if (response.body().getStatus()!= null)
                                    {
                                        Toast.makeText(ctx,response.body().getMessage(),Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(ctx, PendaftarLowongan.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("id",pendaftar.getLowongan_id());
                                        i.putExtra("judul",lowongan.getJudul());
                                        ctx.startActivity(i);

                                    }else {
                                        Toast.makeText(ctx,"Oops ada kesalahan",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(ctx, PendaftarLowongan.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("id",pendaftar.getLowongan_id());
                                        i.putExtra("judul",lowongan.getJudul());
                                        ctx.startActivity(i);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModelPendaftar> call, Throwable t) {
                                    Toast.makeText(ctx,"Oops ada kesalahan",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ctx, PendaftarLowongan.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.putExtra("id",pendaftar.getLowongan_id());
                                    i.putExtra("judul",lowongan.getJudul());
                                    ctx.startActivity(i);
                                }
                            });
                        }
                    });
                    alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();

                }
            });


        }
    }

    public String Rupiah(double rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        String rp = kursIndonesia.format(rupiah);

        return rp;
    }
}
