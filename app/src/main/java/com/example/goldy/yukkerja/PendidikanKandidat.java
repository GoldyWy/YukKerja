package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.goldy.yukkerja.adapter.PendaftarLowonganAdapter;
import com.example.goldy.yukkerja.adapter.PendidikanKandidatAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pendaftar;
import com.example.goldy.yukkerja.model.Pendidikan;
import com.example.goldy.yukkerja.model.ResponseModelPendidikan;
import com.example.goldy.yukkerja.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendidikanKandidat extends AppCompatActivity {
    private ImageView iTambah;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Pendidikan> mItems = new ArrayList<>();
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendidikan_kandidat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPendidikanKandidat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Pendidikan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iTambah = (ImageView)findViewById(R.id.ivTambahPendidikan);
        session = new Session(PendidikanKandidat.this);

        recyclerView = findViewById(R.id.rvPendidikanKandidat);
        mManager = new LinearLayoutManager(PendidikanKandidat.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
//        Toast.makeText(PendidikanKandidat.this,"id= "+ session.getId(),Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PendidikanKandidat.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPendidikan> getPendidikanById = apiRequest.getPendidikanByIdPekerja(session.getId());
        getPendidikanById.enqueue(new Callback<ResponseModelPendidikan>() {
            @Override
            public void onResponse(Call<ResponseModelPendidikan> call, Response<ResponseModelPendidikan> response) {
                progressDialog.dismiss();
//                Toast.makeText(PendidikanKandidat.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                mItems = response.body().getData();

                if (mItems.size() > 0){
                    mAdapter = new PendidikanKandidatAdapter(mItems , PendidikanKandidat.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ResponseModelPendidikan> call, Throwable t) {
                Toast.makeText(PendidikanKandidat.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



        iTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PendidikanKandidat.this,TambahPendidikan.class));
            }
        });

    }

}
