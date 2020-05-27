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

import com.example.goldy.yukkerja.adapter.LamaranKandidatAdapter;
import com.example.goldy.yukkerja.adapter.NotifikasiKandidatAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Notifikasi;
import com.example.goldy.yukkerja.model.Pendaftar;
import com.example.goldy.yukkerja.model.ResponseModelPendaftar;
import com.example.goldy.yukkerja.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LamaranKandidat extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Pendaftar> mItems = new ArrayList<>();
    private SwipeRefreshLayout srLowongan;
    Session session;

    public LamaranKandidat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lamaran_kandidat, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbarLamaranKandidat);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Status Lamaran");

        recyclerView = v.findViewById(R.id.rvLamaran);
        srLowongan = v.findViewById(R.id.srStatusLamaran);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
        session = new Session(getContext());

        getLamaran();

        srLowongan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLamaran();
                srLowongan.setRefreshing(false);
            }
        });



        return v;
    }

    public void getLamaran(){
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPendaftar> getPendaftarById = apiRequest.getPendaftarById(session.getId());
        getPendaftarById.enqueue(new Callback<ResponseModelPendaftar>() {
            @Override
            public void onResponse(Call<ResponseModelPendaftar> call, Response<ResponseModelPendaftar> response) {
                mItems = response.body().getData();

                if (mItems.size() > 0){
                    mAdapter = new LamaranKandidatAdapter(mItems , getContext());
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"Anda belum pernah melamar pekerjaan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelPendaftar> call, Throwable t) {
                Toast.makeText(getContext(),"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
