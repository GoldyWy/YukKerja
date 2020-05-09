package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lkeahlian;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.ResponseModelKeahlian;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLowonganPerusahaan extends AppCompatActivity {
    private Lowongan lowongan;
    private TextView tJudul, tDesk, tPersyaratan, tKeahlian, tWaktu, tGaji1, tGaji2, tLokasi ;
    private Button bUbah, bPendaftar;
    private String sizeKeahlian;
    private String idIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan_perusahaan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetailLowongan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        tJudul = (TextView)findViewById(R.id.tvJudulLowongan);
        tDesk = (TextView)findViewById(R.id.tvDeskLowongan);
        tPersyaratan = (TextView)findViewById(R.id.tvRequirementLowongan);
        tKeahlian = (TextView)findViewById(R.id.tvKeahlianLowongan);
        tWaktu = (TextView)findViewById(R.id.tvWaktuLowongan);
        tGaji1 = (TextView)findViewById(R.id.tvGaji1Lowongan);
        tGaji2 = (TextView)findViewById(R.id.tvGaji2Lowongan);
        tLokasi = (TextView)findViewById(R.id.tvLokasiLowongan);
        bUbah = (Button)findViewById(R.id.btnUbahLowongan);
        bPendaftar = (Button)findViewById(R.id.btnLihatPendaftar);





        ApiRequest apiSize = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelKeahlian> getCountKeahlian = apiSize.getCountKeahlian();
        getCountKeahlian.enqueue(new Callback<ResponseModelKeahlian>() {
            @Override
            public void onResponse(Call<ResponseModelKeahlian> call, Response<ResponseModelKeahlian> response) {
                sizeKeahlian = response.body().getSize().toString();
            }

            @Override
            public void onFailure(Call<ResponseModelKeahlian> call, Throwable t) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(DetailLowonganPerusahaan.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();


        Intent i = getIntent();
        final String id = i.getStringExtra("id");
        String judul = i.getStringExtra("judul");
//        Log.d("RETRO", "response : Sebelum IF");
        if (!id.equals("")){
            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
            retrofit2.Call<ResponseModelLowongan> detailLowongan = apiRequest.detailLowongan(id);
            detailLowongan.enqueue(new Callback<ResponseModelLowongan>() {
                @Override
                public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                    Log.d("RETRO", "response : " + response.body().toString());
                    progressDialog.dismiss();
//                    Log.d("RETRO", "response : Sesudah IF");
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    List<Lowongan> lowongans = response.body().getData();
                    lowongan = lowongans.get(0);

                    idIntent = lowongan.getId();

                    List<Lkeahlian> lkeahlians = lowongan.getLkeahlian();
                    String keahlian = "";
                    for (int i = 0;i <lkeahlians.size();i++){
                        keahlian += "- " + lkeahlians.get(i).getKeahlian_nama()+"\n";
                    }
                    getSupportActionBar().setTitle(lowongan.getJudul());
                    tJudul.setText(lowongan.getJudul());
                    tDesk.setText(lowongan.getDeskripsi());
                    tPersyaratan.setText(lowongan.getRequirement());
                    tWaktu.setText(lowongan.getWaktu_kerja());
                    tKeahlian.setText(keahlian);
                    tGaji1.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji1())));
                    tGaji2.setText(Rupiah(Double.parseDouble(lowongan.getRange_gaji2())));
                    tLokasi.setText(lowongan.getLokasi());

                }

                @Override
                public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                    Log.e("Retro", " Response Error :" + t.getMessage());
                    Toast.makeText(DetailLowonganPerusahaan.this, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }


        bUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailLowonganPerusahaan.this,UbahLowongan.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("id",lowongan.getId());
                i.putExtra("size",sizeKeahlian);
                startActivity(i);
            }
        });

        bPendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailLowonganPerusahaan.this,PendaftarLowongan.class);
                i.putExtra("id",idIntent);
                i.putExtra("judul", lowongan.getJudul());
                startActivity(i);

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
