package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.goldy.yukkerja.adapter.KeahlianKandidatAdapter;
import com.example.goldy.yukkerja.adapter.PendidikanKandidatAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Keahlian;
import com.example.goldy.yukkerja.model.Pendidikan;
import com.example.goldy.yukkerja.model.Pkeahlian;
import com.example.goldy.yukkerja.model.ResponseModelKeahlian;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.model.ResponseModelPkeahlian;
import com.example.goldy.yukkerja.util.Session;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeahlianKandidat extends AppCompatActivity {
    private Spinner sKeahlian, sTingkat;
    private List<Keahlian> keahlians;
    private Button bTambah;
    private ImageView iCheck;
    Session session;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Pkeahlian> mItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keahlian_kandidat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarKeahlianKandidat);
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


        sTingkat = (Spinner)findViewById(R.id.sTingkat);
        sKeahlian = (Spinner)findViewById(R.id.sKeahlian);
        bTambah = (Button)findViewById(R.id.btnTambahKeahlian);
        iCheck = (ImageView) findViewById(R.id.ivDoneKeahlian);
        session = new Session(KeahlianKandidat.this);
        iCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.rvKeahlian);
        mManager = new LinearLayoutManager(KeahlianKandidat.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);

        final String[] items = new String[]{"Pilih Tingkatan","Pemula","Menengah","Ahli"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sTingkat.setAdapter(adapter);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(KeahlianKandidat.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelKeahlian> getKeahlian = apiRequest.getKeahlian();
        getKeahlian.enqueue(new Callback<ResponseModelKeahlian>() {
            @Override
            public void onResponse(Call<ResponseModelKeahlian> call, Response<ResponseModelKeahlian> response) {

                keahlians = response.body().getData();
                if (keahlians != null){
                    Keahlian keahlian ;
                    String[] keahlianArray = new String[keahlians.size()+1] ;
                    keahlianArray[0] = "Pilih Keahlian";
                    for (int i = 1 ; i < keahlians.size()+1;i++){
                        keahlian = keahlians.get(i-1);
                        keahlianArray[i] = keahlian.getNama();
                    }

                    ArrayAdapter<String> adapterKeahlian = new ArrayAdapter<String>(KeahlianKandidat.this, android.R.layout.simple_spinner_dropdown_item, keahlianArray);
                    sKeahlian.setAdapter(adapterKeahlian);
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                }



            }

            @Override
            public void onFailure(Call<ResponseModelKeahlian> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPkeahlian> getKeahlianPekerja = apiRequest.getKeahlianPekerja(session.getId());
        getKeahlianPekerja.enqueue(new Callback<ResponseModelPkeahlian>() {
            @Override
            public void onResponse(Call<ResponseModelPkeahlian> call, Response<ResponseModelPkeahlian> response) {
                mItems = response.body().getData();

                if (mItems.size() > 0){
                    mAdapter = new KeahlianKandidatAdapter(mItems , KeahlianKandidat.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelPkeahlian> call, Throwable t) {
                Toast.makeText(KeahlianKandidat.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
            }
        });



        bTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pkeahlian = sKeahlian.getSelectedItem().toString();
                String pTingkat = sTingkat.getSelectedItem().toString();

                if (pkeahlian.equals("Pilih Keahlian")){
                    Toast.makeText(KeahlianKandidat.this,"Silahkan pilih keahlian...",Toast.LENGTH_SHORT).show();
                }else if(pTingkat.equals("Pilih Tingkatan")){
                    Toast.makeText(KeahlianKandidat.this,"Silahkan pilih tingkatan...",Toast.LENGTH_SHORT).show();
                }else{

                    ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModelPkeahlian> insertKeahlianPekerja = apiRequest1.insertKeahlianPekerja(session.getId(),pkeahlian,pTingkat);
                    insertKeahlianPekerja.enqueue(new Callback<ResponseModelPkeahlian>() {
                        @Override
                        public void onResponse(Call<ResponseModelPkeahlian> call, Response<ResponseModelPkeahlian> response) {
                            String status = response.body().getStatus();
                            if (status.equals("1")){
//                                Toast.makeText(KeahlianKandidat.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                sKeahlian.setSelection(0);
                                sTingkat.setSelection(0);
                                mItems = response.body().getData();

                                if (mItems.size() > 0){
                                    mAdapter = new KeahlianKandidatAdapter(mItems , KeahlianKandidat.this);
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                }


                            }else {
                                Toast.makeText(KeahlianKandidat.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModelPkeahlian> call, Throwable t) {
                            Toast.makeText(KeahlianKandidat.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });




    }
}
