package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Keahlian;
import com.example.goldy.yukkerja.model.Lkeahlian;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.ResponseModelKeahlian;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;
import com.example.goldy.yukkerja.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahLowongan extends AppCompatActivity {
    private Button bPilih,bUbah;
    private EditText etKriteria, eJudul, eDesk, eRequirement, eGaji1, eGaji2  ;
    private String id;
    String [] listItem;
    boolean[] checkedItems;

    private Keahlian mKeahlian;
    private String  size;

    private int sizeInteger;
    ArrayList<String> mKriteriaItems = new ArrayList<>();
    Lowongan lowongan;
    Session session ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_lowongan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUbahLowongan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Ubah lowongan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UbahLowongan.this,UtamaPerusahaan.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        Intent i = getIntent();
        id = i.getStringExtra("id");
        size = i.getStringExtra("size");
        sizeInteger = Integer.parseInt(size);




        bPilih = (Button)findViewById(R.id.btnUbahKriteria);
        bUbah = (Button)findViewById(R.id.btnUbahLowongan);
        etKriteria = (EditText) findViewById(R.id.etUbahKriteria);
        eDesk = (EditText) findViewById(R.id.etUbahDesk);
        eRequirement = (EditText) findViewById(R.id.etUbahRequirement);
        eGaji1 = (EditText) findViewById(R.id.etUbahGaji1);
        eGaji2 = (EditText) findViewById(R.id.etUbahGaji2);

        listItem = new String[sizeInteger];
        checkedItems = new boolean[listItem.length];

        final ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelKeahlian> getKeahlian = api.getKeahlian();
        getKeahlian.enqueue(new Callback<ResponseModelKeahlian>() {
            @Override
            public void onResponse(Call<ResponseModelKeahlian> call, Response<ResponseModelKeahlian> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                List<Keahlian> detail = response.body().getData();
                for (int i = 0; i < detail.size(); i++) {
                    mKeahlian = detail.get(i);
                    listItem[i] = mKeahlian.getNama();
                }

            }

            @Override
            public void onFailure(Call<ResponseModelKeahlian> call, Throwable t) {
                Log.e("Retro"," Response Error :"+t.getMessage());
            }
        });




        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(UbahLowongan.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        final Spinner dropdown = findViewById(R.id.spinner1ubah);
        final String[] items = new String[]{"Hari biasa, Senin - Jumat", "Paruh waktu", "Bekerja shift", "Bekerja remote"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        final Spinner sLokasi = (Spinner)findViewById(R.id.sUbahLokasiLowongan);
        final String[] arrayLokasi = new String[]{"Aceh", "Sumatra Utara", "Sumatra Barat", "Riau","Jambi","Sumatra Selatan","Bengkulu",
                "Lampung","Bangka Belitung","Kepulauan Riau","Jakarta","Jawa Barat","Jawa Tengah","Yogyakarta","Jawa Timur","Banten",
                "Bali","Nusa Tenggara Barat","Nusa Tenggara Timur","Kalimantan Barat","Kalimantan Tengah","Kalimantan Selatan","Kalimantan Timur",
                "Kalimantan Utara","Sulawesi Utara","Sulawesi Tengah","Sulawesi Selatan","Sulawesi Tenggara","Sulawesi Barat","Maluku","Maluku Utara",
                "Gorontalo","Papua","Papua Barat"};
        ArrayAdapter<String> adapterLokasi = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayLokasi);
        sLokasi.setAdapter(adapterLokasi);


        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelLowongan> detailLowongan = apiRequest.detailLowongan(id);
        detailLowongan.enqueue(new Callback<ResponseModelLowongan>() {
            @Override
            public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                progressDialog.dismiss();
                String status = response.body().getStatus();
                String message = response.body().getMessage();
                List<Lowongan> lowongans = response.body().getData();
                lowongan = lowongans.get(0);

                List<Lkeahlian> lkeahlians = lowongan.getLkeahlian();
                String keahlian = "";

                for (int i = 0;i <lkeahlians.size();i++){
                    keahlian += lkeahlians.get(i).getKeahlian_nama();
                    if (i != lkeahlians.size()-1){
                        keahlian += ",";
                    }

                }
                for (int j = 0; j < items.length;j++){
                    if (items[j].equals(lowongan.getWaktu_kerja())){
                        dropdown.setSelection(j);
                    }
                }
                for (int k = 0; k < arrayLokasi.length;k++){
                    if (arrayLokasi[k].equals(lowongan.getLokasi())){
                        sLokasi.setSelection(k);
                    }
                }

                getSupportActionBar().setTitle(lowongan.getJudul());
                eDesk.setText(lowongan.getDeskripsi());
                eRequirement.setText(lowongan.getRequirement());
                etKriteria.setText(keahlian);
                eGaji1.setText(lowongan.getRange_gaji1());
                eGaji2.setText(lowongan.getRange_gaji2());

            }

            @Override
            public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                Log.e("Retro", " Response Error :" + t.getMessage());
                Toast.makeText(UbahLowongan.this, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });



        bPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UbahLowongan.this);
                builder.setTitle("Pilih kriteria");
                builder.setMultiChoiceItems(listItem, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if (isChecked){
                            if (!mKriteriaItems.contains(position)) {
                                mKriteriaItems.add(String.valueOf(position));
                                Log.e("Retro"," Tambah, size sekarang :"+mKriteriaItems.size());
                            }
                        }else{
                            mKriteriaItems.remove(String.valueOf(position));
                            Log.e("Retro"," Else Hapus, size sekarang :"+mKriteriaItems.size());
                        }

                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i=0; i<mKriteriaItems.size();i++){
                            item = item + listItem[Integer.parseInt(mKriteriaItems.get(i)) ];
                            //Cek list terakhir supaya tidak dikasih koma
                            if (i != mKriteriaItems.size() -1){
                                item = item+",";
                            }
                        }
                        etKriteria.setText(item);
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Hapus semua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i=0; i < checkedItems.length;i++){
                            checkedItems[i] = false;
                            mKriteriaItems.clear();
                            etKriteria.setText("");
                        }
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });

        bUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desk = eDesk.getText().toString();
                String requirement = eRequirement.getText().toString();
                String waktu = dropdown.getSelectedItem().toString();
                String keahlian = etKriteria.getText().toString();
                String gaji1 = eGaji1.getText().toString();
                String gaji2 = eGaji2.getText().toString();
                String lokasi = sLokasi.getSelectedItem().toString();
                int iGaji1 = Integer.parseInt(gaji1);
                int iGaji2 = Integer.parseInt(gaji2);


                if (desk.equals("")){
                    Toast.makeText(UbahLowongan.this,"Deskripsi tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (requirement.equals("")){
                    Toast.makeText(UbahLowongan.this,"Persyaratan tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (waktu.equals("")){
                    Toast.makeText(UbahLowongan.this,"Waktu kerja tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (gaji1.equals("")){
                    Toast.makeText(UbahLowongan.this,"Kisaran gaji tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (gaji2.equals("")){
                    Toast.makeText(UbahLowongan.this,"Kisaran gaji tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (lokasi.equals("")){
                    Toast.makeText(UbahLowongan.this,"Lokasi kerja tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (keahlian.equals("")){
                    Toast.makeText(UbahLowongan.this,"Keahlian tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if(iGaji1 > iGaji2){
                    Toast.makeText(UbahLowongan.this,"Gaji harus lebih besar dari pada...",Toast.LENGTH_LONG).show();
                }else{

                    final ProgressDialog progressDialog2;
                    progressDialog2 = new ProgressDialog(UbahLowongan.this);
                    progressDialog2.setMessage("Mohon tunggu....");
                    progressDialog2.setCancelable(false);
                    progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    // show it
                    progressDialog2.show();

                    ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModelLowongan> updateLowonganPerusahaan = apiRequest1.updateLowonganPerusahaan(id,desk,requirement,waktu,gaji1,gaji2,keahlian,lokasi);
                    updateLowonganPerusahaan.enqueue(new Callback<ResponseModelLowongan>() {
                        @Override
                        public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                            Log.d("Response message :", response.message());
                            Log.d("Response raw : ", String.valueOf(response.raw()));

                            if (response.body() != null){
                                String status = response.body().getStatus();
                                String message = response.body().getMessage();
                                progressDialog2.dismiss();
                                if (status.equals("1")){
                                    Toast.makeText(UbahLowongan.this,message,Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(UbahLowongan.this, DetailLowonganPerusahaan.class);
                                    i.putExtra("id", id);
                                    i.putExtra("judul", lowongan.getJudul());
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    onBackPressed();
                                }else{
                                    Toast.makeText(UbahLowongan.this,message,Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(UbahLowongan.this,"Tidak ada response",Toast.LENGTH_SHORT).show();
                                progressDialog2.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                            Log.e("Retro"," Response Error :"+t.getMessage());

                            progressDialog2.dismiss();
                            Toast.makeText(UbahLowongan.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                        }
                    });


                }

            }
        });




    }
}
