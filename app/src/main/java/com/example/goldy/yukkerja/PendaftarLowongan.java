package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.goldy.yukkerja.adapter.PendaftarLowonganAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pendaftar;
import com.example.goldy.yukkerja.model.ResponseModelPendaftar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendaftarLowongan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Pendaftar> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftar_lowongan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPendaftarLowongan);
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
        String id = i.getStringExtra("id");
        String judul = i.getStringExtra("judul");

        getSupportActionBar().setTitle("Pendaftar "+judul);

//        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
//        retrofit2.Call<ResponseModelPendaftar> getPendaftarByIdLowongan = apiRequest.getPendaftarByIdLowongan(id);
//        getPendaftarByIdLowongan.enqueue(new Callback<ResponseModelPendaftar>() {
//            @Override
//            public void onResponse(Call<ResponseModelPendaftar> call, Response<ResponseModelPendaftar> response) {
//                Toast.makeText(PendaftarLowongan.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModelPendaftar> call, Throwable t) {
//                Toast.makeText(PendaftarLowongan.this, t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });



        recyclerView = findViewById(R.id.rvPendaftarLowongan);
        mManager = new LinearLayoutManager(PendaftarLowongan.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PendaftarLowongan.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("Retrofit","Sebelum retrofit");
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPendaftar> getPendaftarByIdLowongan = apiRequest.getPendaftarByIdLowongan(id);
        getPendaftarByIdLowongan.enqueue(new Callback<ResponseModelPendaftar>() {
            @Override
            public void onResponse(Call<ResponseModelPendaftar> call, Response<ResponseModelPendaftar> response) {
                mItems = response.body().getData();

                if (mItems.size() > 0){
                    mAdapter = new PendaftarLowonganAdapter(mItems , PendaftarLowongan.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModelPendaftar> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Failure",t.getMessage());
                Log.d("Failure2",t.getCause().getMessage());
//                Log.d("Failure3",t.getStackTrace());
                Toast.makeText(PendaftarLowongan.this,"Oops ada kesalahan",Toast.LENGTH_SHORT).show();
//                Toast.makeText(PendaftarLowongan.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("Retrofit","Sesudah retrofit");
        progressDialog.dismiss();


    }
}
