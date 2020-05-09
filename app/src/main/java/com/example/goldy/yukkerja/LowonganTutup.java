package com.example.goldy.yukkerja;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goldy.yukkerja.adapter.PerusahaanLowonganAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;
import com.example.goldy.yukkerja.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LowonganTutup extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Lowongan> mItems = new ArrayList<>();
    Session session;



    public LowonganTutup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_lowongan_tutup, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Lowongan Tutup");
        recyclerView = v.findViewById(R.id.rvLowonganPerusahaanTutup);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);

        session = new Session(getContext());

//        Daftar Lowongan
        if (session.getId()!= null){
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Mohon tunggu....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDialog.show();

            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
            retrofit2.Call<ResponseModelLowongan> lowonganPunyaPerusahaan = apiRequest.lowonganPunyaPerusahaan(session.getId(),"0");
            lowonganPunyaPerusahaan.enqueue(new Callback<ResponseModelLowongan>() {
                @Override
                public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                    Log.d("RETRO", "response : " + response.body().toString());
                    mItems = response.body().getData();

                    if (mItems.size() > 0){
                        mAdapter = new PerusahaanLowonganAdapter(mItems , getContext());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {
                    Log.e("Retro", " Response Error :" + t.getMessage());
                }
            });
        }



        return v;

    }

}
