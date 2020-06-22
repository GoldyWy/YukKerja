package com.example.goldy.yukkerja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.adapter.KeahlianAdminAdapter;
import com.example.goldy.yukkerja.adapter.KeahlianKandidatAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Keahlian;
import com.example.goldy.yukkerja.model.Pkeahlian;
import com.example.goldy.yukkerja.model.ResponseModelAdmin;
import com.example.goldy.yukkerja.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeahlianAdmin extends AppCompatActivity {
    private EditText tKeahlian;
    private Button bTambah;
    Session session;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Keahlian> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keahlian_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarKeahlianAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Keahlian");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        session = new Session(KeahlianAdmin.this);
        tKeahlian = (EditText) findViewById(R.id.etKeahlian);
        bTambah = (Button) findViewById(R.id.btnTambahKeahlianAdmin);

        bTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keahlian = tKeahlian.getText().toString();

                if (!keahlian.equals("")){
                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModelAdmin> insertKeahlian = apiRequest.insertKeahlian(session.getId(),keahlian);
                    insertKeahlian.enqueue(new Callback<ResponseModelAdmin>() {
                        @Override
                        public void onResponse(Call<ResponseModelAdmin> call, Response<ResponseModelAdmin> response) {
                            String status = response.body().getStatus();

                            if (status.equals("1")){
                                Toast.makeText(KeahlianAdmin.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                mItems = response.body().getKeahlians();
                                tKeahlian.setText("");
                                if (mItems.size() > 0){
                                    mAdapter = new KeahlianAdminAdapter(mItems , KeahlianAdmin.this);
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }else{
                                Toast.makeText(KeahlianAdmin.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModelAdmin> call, Throwable t) {
                            Toast.makeText(KeahlianAdmin.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        recyclerView = findViewById(R.id.rvKeahlianAdmin);
        mManager = new LinearLayoutManager(KeahlianAdmin.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);


        getKeahlians();




    }

    public void getKeahlians(){
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelAdmin> getKeahlian = apiRequest.getKeahlian(session.getId());
        getKeahlian.enqueue(new Callback<ResponseModelAdmin>() {
            @Override
            public void onResponse(Call<ResponseModelAdmin> call, Response<ResponseModelAdmin> response) {
                mItems = response.body().getKeahlians();
                if (mItems.size() > 0){
                    mAdapter = new KeahlianAdminAdapter(mItems , KeahlianAdmin.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ResponseModelAdmin> call, Throwable t) {
                Toast.makeText(KeahlianAdmin.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
