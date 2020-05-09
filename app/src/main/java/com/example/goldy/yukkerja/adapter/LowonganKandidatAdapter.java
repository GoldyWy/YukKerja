package com.example.goldy.yukkerja.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.DetailFoto;
import com.example.goldy.yukkerja.DetailLowonganKandidat;
import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.UtamaPerusahaan;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Perusahaan;
import com.example.goldy.yukkerja.util.Session;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class LowonganKandidatAdapter extends RecyclerView.Adapter<LowonganKandidatAdapter.HolderData> {
    private List<Lowongan> mList;
    private Context ctx;
    RetroServer retroServer;

    public LowonganKandidatAdapter(List<Lowongan> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public LowonganKandidatAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lowongan_kandidat,viewGroup,false);
        LowonganKandidatAdapter.HolderData holderData = new LowonganKandidatAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull LowonganKandidatAdapter.HolderData holderData, int i) {
        Lowongan lowongan = mList.get(i);
        Perusahaan perusahaan = lowongan.getPerusahaan();
        retroServer = new RetroServer();
        if (perusahaan.getFoto()!=null){
            Glide.with(ctx).load(retroServer.url()+perusahaan.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holderData.iFoto);
        }
        holderData.tJudul.setText(lowongan.getJudul());
        holderData.tPerusahaan.setText(perusahaan.getNama());
        holderData.tLokasi.setText(lowongan.getLokasi());
        holderData.tGaji.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji1()))+" - "+Rupiah(Double.parseDouble(lowongan.getRange_gaji2())));



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private TextView tJudul, tPerusahaan, tLokasi, tGaji;
        private ImageView iFoto;

        public HolderData(@NonNull final View itemView) {
            super(itemView);

            tJudul = (TextView)itemView.findViewById(R.id.tvItemJudul);
            tPerusahaan = (TextView)itemView.findViewById(R.id.tvItemPerusahaan);
            tLokasi = (TextView)itemView.findViewById(R.id.tvItemLokasi);
            tGaji = (TextView)itemView.findViewById(R.id.tvItemGaji);
            iFoto = (ImageView) itemView.findViewById(R.id.ivItemFotoPerusahaan);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ctx, DetailLowonganKandidat.class);
                    i.putExtra("id", mList.get(getAdapterPosition()).getId());
                    ctx.startActivity(i);
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
