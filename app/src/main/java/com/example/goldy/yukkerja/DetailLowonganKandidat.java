package com.example.goldy.yukkerja;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Keahlian;
import com.example.goldy.yukkerja.model.Lkeahlian;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Perusahaan;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;
import com.example.goldy.yukkerja.model.ResponseModelPendaftar;
import com.example.goldy.yukkerja.util.Session;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLowonganKandidat extends AppCompatActivity {
    private TextView tJudul, tPerusahaan, tLokasi, tDesk, tPersyaratan, tKeahlian, tWaktu, tGaji, tInformasi;
    private ImageView iFoto;
    private Button bLamar;
    private String id;
    Session session;
    RetroServer retroServer;
    Lowongan lowongan;
    Perusahaan perusahaan ;
    List<Lkeahlian> lkeahlians ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan_kandidat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetailLowonganKandidat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        id = i.getStringExtra("id");

        tJudul = (TextView)findViewById(R.id.tvJudulLowonganKandidat);
        tPerusahaan = (TextView)findViewById(R.id.tvPerusahaanKandidat);
        tLokasi = (TextView)findViewById(R.id.tvLokasiLowonganKandidat);
        tDesk = (TextView)findViewById(R.id.tvDeskPekerjaanKandidat);
        tPersyaratan = (TextView)findViewById(R.id.tvPersyaratanLowonganKandidat);
        tKeahlian = (TextView)findViewById(R.id.tvKeahlianLowonganKandidat);
        tWaktu = (TextView)findViewById(R.id.tvWaktuKerjaLowonganKandidat);
        tGaji = (TextView)findViewById(R.id.tvGajiLowonganKandidat);
        tInformasi = (TextView)findViewById(R.id.tvInformasiPerusahaan);
        iFoto = (ImageView)findViewById(R.id.ivFotoPerusahaan);
        bLamar = (Button)findViewById(R.id.btnLamar);

        session = new Session(DetailLowonganKandidat.this);
        retroServer = new RetroServer();

        final ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelLowongan> getDetailLowongan = apiRequest.getDetailLowongan(id,session.getId());
        getDetailLowongan.enqueue(new Callback<ResponseModelLowongan>() {
            @Override
            public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                lowongan = response.body().getDetail();
                perusahaan = lowongan.getPerusahaan();
                lkeahlians = lowongan.getLkeahlian();
                String daftar = response.body().getDaftar();

                if (perusahaan.getFoto()!=null){
                    Glide.with(DetailLowonganKandidat.this).load(retroServer.url()+perusahaan.getFoto())
                            .apply(RequestOptions.circleCropTransform())
                            .into(iFoto);
                }
                tJudul.setText(lowongan.getJudul());
                getSupportActionBar().setTitle(lowongan.getJudul());
                tPerusahaan.setText(perusahaan.getNama());
                tLokasi.setText(lowongan.getLokasi());
                tDesk.setText(lowongan.getDeskripsi());
                tPersyaratan.setText(lowongan.getRequirement());
                String keahlian = "";
                for (int i = 0; i < lkeahlians.size();i++)
                {
                    keahlian += "- "+lkeahlians.get(i).getKeahlian_nama()+"\n";
                }
                tKeahlian.setText(keahlian);
                tWaktu.setText(lowongan.getWaktu_kerja());
                tGaji.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji1()))+" - "+Rupiah(Double.parseDouble(lowongan.getRange_gaji2())));
                tInformasi.setText(perusahaan.getInformasi());
                if (daftar.equals("1")){
                    bLamar.setEnabled(false);
                    bLamar.setText("Anda sudah melamar");
                    bLamar.setBackgroundColor(getResources().getColor(R.color.colorAbuBackground));
                }

            }

            @Override
            public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {

            }
        });

        bLamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailLowonganKandidat.this);
                builder.setTitle("Melamar sebagai "+ lowongan.getJudul()+" ?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelPendaftar> daftarLowongan = apiRequest1.daftarLowongan(session.getId(),perusahaan.getId(),lowongan.getId());
                        daftarLowongan.enqueue(new Callback<ResponseModelPendaftar>() {
                            @Override
                            public void onResponse(Call<ResponseModelPendaftar> call, Response<ResponseModelPendaftar> response) {
                                Toast.makeText(DetailLowonganKandidat.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DetailLowonganKandidat.this,DetailLowonganKandidat.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("id",lowongan.getId());
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<ResponseModelPendaftar> call, Throwable t) {
                                Toast.makeText(DetailLowonganKandidat.this, "Oops ada kesalahan...", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();


            }
        });


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
