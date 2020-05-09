package com.example.goldy.yukkerja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilKandidat extends Fragment {
    private TextView tNama, tEmail, tJk, tStatus;
    private RelativeLayout bLogout, bUbahProfil, bUbahPass, bPendidikan, bResume,bKeahlian;
    private ImageView iProfil;
    private CheckBox cStatus;
    Session session;
    RetroServer retroServer;

    public ProfilKandidat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profil_kandidat, container, false);

        session = new Session(getContext());
        retroServer = new RetroServer();

        bLogout = v.findViewById(R.id.btnLogout);
        bUbahProfil = v.findViewById(R.id.btnUbahProfilKandidat);
        bUbahPass = v.findViewById(R.id.btnUbahPasswordKandidat);
        bPendidikan = v.findViewById(R.id.btnPendidikan);
        bResume = v.findViewById(R.id.btnResume);
        bKeahlian = v.findViewById(R.id.btnKeahlian);
        tEmail = v.findViewById(R.id.tvEmailKandidat);
        tNama = v.findViewById(R.id.tvNamaKandidat);
        tJk = v.findViewById(R.id.tvJkKandidat);
        tStatus = v.findViewById(R.id.tvStatusKandidat);
        iProfil = v.findViewById(R.id.ivProfilKandidat);
        cStatus = v.findViewById(R.id.cbStatus);

        tJk.setText(session.getJk());

        if (session.getFoto() != null){
            Glide.with(ProfilKandidat.this).load(retroServer.url()+session.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(iProfil);
            iProfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailFoto.class);
                    startActivity(i);
                }
            });
        }

        tEmail.setText(session.getEmail());
        tNama.setText(session.getNama());

        if(session.getStatus()!=null){
            if(session.getStatus().equals("1")){
                cStatus.setChecked(true);
                tStatus.setText("Sedang Mencari");
            }

            if (session.getStatus().equals("0")){
                cStatus.setChecked(false);
                tStatus.setText("Tidak Mencari");
            }

            cStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);


                    if (cStatus.isChecked()){
                        retrofit2.Call<ResponseModelPekerja> updateStatusPekerja = apiRequest.updateStatusPekerja(session.getId(),"1");
                        updateStatusPekerja.enqueue(new Callback<ResponseModelPekerja>() {
                            @Override
                            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                                session.setStatus("1");
                                tStatus.setText("Sedang Mencari");
                            }

                            @Override
                            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                                Toast.makeText(getContext(),"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else{

                        retrofit2.Call<ResponseModelPekerja> updateStatusPekerja = apiRequest.updateStatusPekerja(session.getId(),"0");
                        updateStatusPekerja.enqueue(new Callback<ResponseModelPekerja>() {
                            @Override
                            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                                session.setStatus("0");
                                tStatus.setText("Tidak Mencari");
                            }

                            @Override
                            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                                Toast.makeText(getContext(),"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }



        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logout();
                Intent i = new Intent(getContext(), LoginPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }
        });

        bUbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),UbahProfilKandidat.class));
            }
        });

        bUbahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),UbahPasswordKandidat.class));
            }
        });

        bPendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PendidikanKandidat.class));
            }
        });

        bResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ResumeKandidat.class));
            }
        });

        bKeahlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), KeahlianKandidat.class));
            }
        });





        return v;

    }
}
