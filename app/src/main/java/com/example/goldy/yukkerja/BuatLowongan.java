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
import com.example.goldy.yukkerja.model.ResponseModel;
import com.example.goldy.yukkerja.model.ResponseModelKeahlian;
import com.example.goldy.yukkerja.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuatLowongan extends AppCompatActivity {
    private Button bPilih,bSimpan;
    private EditText etKriteria, eJudul, eDesk, eRequirement, eGaji1, eGaji2  ;
    private String id,judul,desk,requirement,waktu,gaji1,gaji2,keahlian;
    String [] listItem;
    boolean[] checkedItems;
    private Keahlian mKeahlian;
    private String  size;
    private int sizeInteger;
    ArrayList<String> mKriteriaItems = new ArrayList<>();
    Session session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_lowongan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBuatLowongan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Buat lowongan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BuatLowongan.this,UtamaPerusahaan.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        bPilih = (Button)findViewById(R.id.btnKriteria);
        bSimpan = (Button)findViewById(R.id.btnSimpanLowongan);
        etKriteria = (EditText) findViewById(R.id.etKriteria);
        eJudul = (EditText) findViewById(R.id.etJudul);
        eDesk = (EditText) findViewById(R.id.etDesk);
        eRequirement = (EditText) findViewById(R.id.etRequirement);
        eGaji1 = (EditText) findViewById(R.id.etGaji1);
        eGaji2 = (EditText) findViewById(R.id.etGaji2);

        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Hari biasa, Senin - Jumat", "Paruh waktu", "Bekerja shift", "Bekerja remote"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        final Spinner sLokasi = (Spinner)findViewById(R.id.sLokasi);
        final String[] arrayLokasi = new String[]{"Aceh", "Sumatra Utara", "Sumatra Barat", "Riau","Jambi","Sumatra Selatan","Bengkulu",
                "Lampung","Bangka Belitung","Kepulauan Riau","Jakarta","Jawa Barat","Jawa Tengah","Yogyakarta","Jawa Timur","Banten",
                "Bali","Nusa Tenggara Barat","Nusa Tenggara Timur","Kalimantan Barat","Kalimantan Tengah","Kalimantan Selatan","Kalimantan Timur",
                "Kalimantan Utara","Sulawesi Utara","Sulawesi Tengah","Sulawesi Selatan","Sulawesi Tenggara","Sulawesi Barat","Maluku","Maluku Utara",
                "Gorontalo","Papua","Papua Barat"};
        ArrayAdapter<String> adapterLokasi = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayLokasi);
        sLokasi.setAdapter(adapterLokasi);

        Intent iGet = getIntent();
        Bundle bundle = iGet.getExtras();
        if (bundle != null) {
            size = (String) bundle.get("size");
            sizeInteger = Integer.parseInt(size);
        }

        listItem = new String[sizeInteger];

        final ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelKeahlian> getKeahlian = api.getKeahlian();
        getKeahlian.enqueue(new Callback<ResponseModelKeahlian>() {
            @Override
            public void onResponse(Call<ResponseModelKeahlian> call, Response<ResponseModelKeahlian> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                List<Keahlian> detail = response.body().getData();
                if (detail != null){
                    for (int i = 0; i < detail.size(); i++) {
                        mKeahlian = detail.get(i);
                        listItem[i] = mKeahlian.getNama();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseModelKeahlian> call, Throwable t) {
                Log.e("Retro"," Response Error :"+t.getMessage());
            }
        });
        checkedItems = new boolean[listItem.length];

        bPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BuatLowongan.this);
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

        bSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String waktu  = dropdown.getSelectedItem().toString();
//                Toast.makeText(BuatLowongan.this,waktu, Toast.LENGTH_SHORT).show();
                judul = eJudul.getText().toString();
                desk = eDesk.getText().toString();
                requirement = eRequirement.getText().toString();
                String waktu  = dropdown.getSelectedItem().toString();
                String lokasi = sLokasi.getSelectedItem().toString();
                gaji1 = eGaji1.getText().toString();
                gaji2 = eGaji2.getText().toString();
                keahlian = etKriteria.getText().toString();

                if (judul.equals("")){
                    Toast.makeText(BuatLowongan.this,"Judul tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (desk.equals("")){
                    Toast.makeText(BuatLowongan.this,"Deskripsi tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (requirement.equals("")){
                    Toast.makeText(BuatLowongan.this,"Persyaratan tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (waktu.equals("")){
                    Toast.makeText(BuatLowongan.this,"Waktu kerja tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (gaji1.equals("")){
                    Toast.makeText(BuatLowongan.this,"Kisaran gajij tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (gaji2.equals("")){
                    Toast.makeText(BuatLowongan.this,"Kisaran gajij tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (gaji1.equals("")){
                    Toast.makeText(BuatLowongan.this,"Kisaran gajij tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (keahlian.equals("")){
                    Toast.makeText(BuatLowongan.this,"Keahlian tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (lokasi.equals("")){
                    Toast.makeText(BuatLowongan.this,"Lokasi tidak boleh kosong...",Toast.LENGTH_LONG).show();
                } else {
                    session = new Session(BuatLowongan.this);
                    id = session.getId();

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModel> buatLowongan = apiRequest.buatLowongan(id,judul,desk,requirement,waktu,gaji1,gaji2,keahlian,lokasi);
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(BuatLowongan.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    // show it
                    progressDialog.show();
                    buatLowongan.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d("Response message :", response.message());
                            Log.d("Response raw : ", String.valueOf(response.raw()));

                            if (response.body() != null){
                                String status = response.body().getStatus();
                                String message = response.body().getMessage();
                                progressDialog.dismiss();
                                if (status.equals("1")){
                                    Toast.makeText(BuatLowongan.this,message,Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(BuatLowongan.this, UtamaPerusahaan.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(BuatLowongan.this,message,Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(BuatLowongan.this, UtamaPerusahaan.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(BuatLowongan.this,"Tidak ada response",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(BuatLowongan.this, UtamaPerusahaan.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Log.e("Retro"," Response Error :"+t.getMessage());

                            progressDialog.dismiss();
                            Toast.makeText(BuatLowongan.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                        }
                    });


                }

            }
        });

    }


}
