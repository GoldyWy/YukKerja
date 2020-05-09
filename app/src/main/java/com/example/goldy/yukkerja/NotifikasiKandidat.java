package com.example.goldy.yukkerja;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.goldy.yukkerja.adapter.NotifikasiKandidatAdapter;
import com.example.goldy.yukkerja.adapter.PerusahaanLowonganAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Notifikasi;
import com.example.goldy.yukkerja.model.ResponseModelNotifikasi;
import com.example.goldy.yukkerja.util.Session;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifikasiKandidat extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Notifikasi> mItems = new ArrayList<>();
    private SwipeRefreshLayout srLowongan;
    Session session;


    public NotifikasiKandidat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notifikasi_kandidat, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbarNotifikasiKandidat);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notifikasi Pekerjaan");

        recyclerView = v.findViewById(R.id.rvNotifikasi);
        srLowongan = v.findViewById(R.id.srNotifikasiKandidat);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
        session = new Session(getContext());



        getNotifikasi();

        srLowongan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotifikasi();
                srLowongan.setRefreshing(false);
            }
        });





        return v;
    }
    public void getNotifikasi(){
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelNotifikasi> getNotifikasi = apiRequest.getNotifikasi(session.getId());
        getNotifikasi.enqueue(new Callback<ResponseModelNotifikasi>() {
            @Override
            public void onResponse(Call<ResponseModelNotifikasi> call, Response<ResponseModelNotifikasi> response) {
                mItems = response.body().getData();

                if (mItems.size() > 0){
                    mAdapter = new NotifikasiKandidatAdapter(mItems , getContext());
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ResponseModelNotifikasi> call, Throwable t) {
                Toast.makeText(getContext(),"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
