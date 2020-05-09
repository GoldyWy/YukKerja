package com.example.goldy.yukkerja.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.BuatLowongan;
import com.example.goldy.yukkerja.DetailLowonganPerusahaan;
import com.example.goldy.yukkerja.LowonganAktif;
import com.example.goldy.yukkerja.LowonganSejarah;
import com.example.goldy.yukkerja.LowonganTutup;
import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.UtamaPerusahaan;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerusahaanLowonganAdapter extends RecyclerView.Adapter<PerusahaanLowonganAdapter.HolderData> {

    private List<Lowongan> mList;
    private Context ctx;





    public PerusahaanLowonganAdapter(List<Lowongan> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lowongan_perusahaan,viewGroup,false);
        PerusahaanLowonganAdapter.HolderData holderData = new PerusahaanLowonganAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {

        Lowongan lowongan = mList.get(i);
        holderData.tJudul.setText(lowongan.getJudul());
//        holderData.tDesk.setText(lowongan.getDeskripsi());
        String desk = lowongan.getDeskripsi();
        String[] arrayDesk  = desk.split(" ");
        String gabung = "";

        int limit = 4;
        if (arrayDesk.length > 4) {
            for (int j = 0; j < limit; j++) {
                gabung += arrayDesk[j]+" ";
                if (j == limit-1) {
                    gabung += "...";
                }
            }
            holderData.tDesk.setText(gabung);
        }else{
            holderData.tDesk.setText(lowongan.getDeskripsi());
        }

        holderData.tGaji1.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji1())));
        holderData.tGaji2.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji2())));


    }



    @Override
    public int getItemCount() {

        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        private TextView tJudul,tDesk, tGaji1, tGaji2;
        private Button bTutup, bBuka, bSelesai;

        public HolderData(@NonNull View itemView) {
            super(itemView);


            tJudul = (TextView)itemView.findViewById(R.id.tvListJudul);
            tDesk = (TextView)itemView.findViewById(R.id.tvListDesk);
            tGaji1 = (TextView)itemView.findViewById(R.id.tvListGaji1);
            tGaji2 = (TextView)itemView.findViewById(R.id.tvListGaji2);

            bTutup = (Button)itemView.findViewById(R.id.btnTutup);
            bBuka = (Button)itemView.findViewById(R.id.btnBuka);
            bSelesai = (Button)itemView.findViewById(R.id.btnSelesai);
//            Toast.makeText(ctx, lowongan.getStatus(),Toast.LENGTH_SHORT).show();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Lowongan lowongan = mList.get(getAdapterPosition());
                    Intent i = new Intent(ctx, DetailLowonganPerusahaan.class);
//                    Bundle b = new Bundle();
//                    b.putParcelable("lowongan", (Parcelable) lowongan);
                    i.putExtra("id", lowongan.getId());
                    i.putExtra("judul", lowongan.getJudul());
                    ctx.startActivity(i);

//                    Toast.makeText(ctx,"Pindah Halaman",Toast.LENGTH_SHORT).show();
                }
            });

            if (mList.size()!=0){
                Lowongan lowongan = mList.get(0);
                if (lowongan.getStatus().equals("1")){
                    bBuka.setVisibility(View.GONE);
                    bSelesai.setVisibility(View.GONE);
                }

                if (lowongan.getStatus().equals("0")){
                    bTutup.setVisibility(View.GONE);
                }

                if (lowongan.getStatus().equals("2")){
                    bBuka.setVisibility(View.GONE);
                    bSelesai.setVisibility(View.GONE);
                    bTutup.setVisibility(View.GONE);
                }
            }

            bBuka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Lowongan lowongan = mList.get(getAdapterPosition());

                    final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                    alert.setTitle("Anda yakin membuka kembali pendaftaran "+ lowongan.getJudul()+ "?");
                    alert.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Lowongan lowongan = mList.get(getAdapterPosition());
                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu....");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            // show it
                            progressDialog.show();

                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            retrofit2.Call<ResponseModelLowongan> updateStatusLowongan = apiRequest.updateStatusLowongan(lowongan.getId(),"1");

                            updateStatusLowongan.enqueue(new Callback<ResponseModelLowongan>() {
                                @Override
                                public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                                    Log.d("RETRO", "response : " + response.body().toString());
                                    String status = response.body().getStatus();
                                    String message = response.body().getMessage();
                                    if (status.equals("1")){
                                        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                        mList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                    }
//                                    lt = new LowonganTutup();
//                                    FragmentTransaction ft = ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction().replace(R.id.fgLowonganPerusahaan, lt);
//                                    ft.commit();



                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                                    Log.e("Retro", " Response Error :" + t.getMessage());
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
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

            bSelesai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Lowongan lowongan = mList.get(getAdapterPosition());
                    final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                    alert.setTitle("Anda yakin untuk menyelesaikan "+ lowongan.getJudul()+ "?");
                    alert.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Lowongan lowongan = mList.get(getAdapterPosition());
                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu....");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            // show it
                            progressDialog.show();

                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            retrofit2.Call<ResponseModelLowongan> updateStatusLowongan = apiRequest.updateStatusLowongan(lowongan.getId(),"2");
                            updateStatusLowongan.enqueue(new Callback<ResponseModelLowongan>() {
                                @Override
                                public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                                    Log.d("RETRO", "response : " + response.body().toString());
                                    String status = response.body().getStatus();
                                    String message = response.body().getMessage();
                                    if (status.equals("1")){
                                        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                        mList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                    }
//                                    lt = new LowonganTutup();
//                                    FragmentTransaction ft = ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction().replace(R.id.fgLowonganPerusahaan, lt);
//                                    ft.commit();
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                                    Log.e("Retro", " Response Error :" + t.getMessage());
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
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

            bTutup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Lowongan lowongan = mList.get(getAdapterPosition());
                    final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                    alert.setTitle("Anda yakin untuk menutup pendaftaran "+ lowongan.getJudul()+ "?");
                    alert.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Lowongan lowongan = mList.get(getAdapterPosition());
                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu....");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            // show it
                            progressDialog.show();

                            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                            retrofit2.Call<ResponseModelLowongan> updateStatusLowongan = apiRequest.updateStatusLowongan(lowongan.getId(),"0");
                            updateStatusLowongan.enqueue(new Callback<ResponseModelLowongan>() {
                                @Override
                                public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                                    Log.d("RETRO", "response : " + response.body().toString());
                                    String status = response.body().getStatus();
                                    String message = response.body().getMessage();
                                    if (status.equals("1")){
                                        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                        mList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
                                    }
//                                    la = new LowonganAktif();
//                                    FragmentTransaction ft = ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction().replace(R.id.fgLowonganPerusahaan, la);
//                                    ft.commit();
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                                    Log.e("Retro", " Response Error :" + t.getMessage());
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
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
