package com.example.goldy.yukkerja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.adapter.LowonganKandidatAdapter;
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
public class BerandaKandidat extends Fragment {

    private EditText eCari;
    private ImageView bCari;
    private ImageView iFoto;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Lowongan> mItems = new ArrayList<>();
    private SwipeRefreshLayout srLowongan;
    Session session;
    RetroServer retroServer;

    public BerandaKandidat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_beranda_kandidat, container, false);

        eCari = v.findViewById(R.id.etCariBeranda);
        bCari = v.findViewById(R.id.btnCariBeranda);
        iFoto = v.findViewById(R.id.ivFotoBeranda);
        srLowongan = v.findViewById(R.id.srLowonganKandidat);

        recyclerView = v.findViewById(R.id.rvLowonganKandidat);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mManager);
        retroServer = new RetroServer();
        session = new Session(getContext());

        if (session.getFoto() != null){
            Glide.with(getContext()).load(retroServer.url()+session.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(iFoto);

            iFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailFoto.class);
                    startActivity(i);
                }
            });
        }

        eCari.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String cari = eCari.getText().toString();

                    if (!cari.equals("")){
                        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelLowongan> getCariLowongan = apiRequest.getCariLowongan(cari);
                        getCariLowongan.enqueue(new Callback<ResponseModelLowongan>() {
                            @Override
                            public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                                mItems = response.body().getData();

                                if (mItems.size() > 0){
                                    mAdapter = new LowonganKandidatAdapter(mItems , getContext());
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                }else {
                                    Toast.makeText(getContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {

                            }
                        });

                    }



                    return true;
                }
                return false;
            }
        });

        getLowongan();

        srLowongan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLowongan();
                srLowongan.setRefreshing(false);
            }
        });


        return v;
    }

    public void getLowongan(){
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelLowongan> getLowongan = apiRequest.getLowongan();
        getLowongan.enqueue(new Callback<ResponseModelLowongan>() {
            @Override
            public void onResponse(Call<ResponseModelLowongan> call, Response<ResponseModelLowongan> response) {
                if (response.body().getData()!=null){
                    mItems = response.body().getData();

                    if (mItems.size() > 0){
                        mAdapter = new LowonganKandidatAdapter(mItems , getContext());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseModelLowongan> call, Throwable t) {

            }
        });
    }
}
