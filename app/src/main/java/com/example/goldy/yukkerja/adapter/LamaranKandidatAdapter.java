package com.example.goldy.yukkerja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.R;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Pendaftar;
import com.example.goldy.yukkerja.model.Perusahaan;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LamaranKandidatAdapter extends RecyclerView.Adapter<LamaranKandidatAdapter.HolderData> {
    private List<Pendaftar> mList;
    private Context ctx;
    RetroServer retroServer;

    public LamaranKandidatAdapter(List<Pendaftar> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
        retroServer = new RetroServer();
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lamaran_kandidat,viewGroup,false);
        LamaranKandidatAdapter.HolderData holderData = new LamaranKandidatAdapter.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        Pendaftar pendaftar = mList.get(i);
        Perusahaan perusahaan = pendaftar.getPerusahaan();
        Lowongan lowongan = pendaftar.getLowongan();

        if (perusahaan.getFoto()!=null){
            Glide.with(ctx).load(retroServer.url()+perusahaan.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holderData.iFoto);
        }
        holderData.tJudul.setText(lowongan.getJudul());
        holderData.tGaji.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji1()))+" - "+Rupiah(Double.parseDouble(lowongan.getRange_gaji2())));

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pendaftar.getCreated_at());
            String tanggal = new SimpleDateFormat("dd MMMM yyyy").format(date);
            holderData.tTanggal.setText(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (pendaftar.getStatus().equals("2")){
            holderData.tStatus.setText("Menunggu");
            holderData.tStatus.setBackground(ctx.getDrawable(R.drawable.badge_menunggu));
        }
        if (pendaftar.getStatus().equals("1")){
            holderData.tStatus.setText("Diterima");
            holderData.tStatus.setBackground(ctx.getDrawable(R.drawable.badge_terima));
            holderData.tStatus.setTextColor(ctx.getResources().getColor(R.color.colorWhite));
        }
        if (pendaftar.getStatus().equals("0")){
            holderData.tStatus.setText("Tidak diterima");
            holderData.tStatus.setBackground(ctx.getDrawable(R.drawable.badge_tolak));
            holderData.tStatus.setTextColor(ctx.getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        private ImageView iFoto;
        private TextView tJudul, tGaji, tTanggal, tStatus;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            iFoto = (ImageView)itemView.findViewById(R.id.ivFotoPerusahaanLamaran);
            tJudul = (TextView)itemView.findViewById(R.id.tvJudulLamaran);
            tGaji = (TextView)itemView.findViewById(R.id.tvGajiLamaran);
            tTanggal = (TextView)itemView.findViewById(R.id.tvTanggalLamaran);
            tStatus = (TextView)itemView.findViewById(R.id.tvStatusLamaran);


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
