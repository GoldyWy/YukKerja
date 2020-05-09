package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.adapter.PerusahaanLowonganAdapter;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Lowongan;
import com.example.goldy.yukkerja.model.Perusahaan;
import com.example.goldy.yukkerja.model.ResponseModelKeahlian;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;
import com.example.goldy.yukkerja.model.ResponseModelPerusahaan;
import com.example.goldy.yukkerja.util.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtamaPerusahaan extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String email,role,sizeKeahlian;
    private ImageView iFoto;
    private TextView tNama,tEmail,tAktif,tTutup,tSejarah;
    Session session;
    boolean doubleBackToExitPressedOnce = false;
    LowonganAktif la;
    LowonganTutup lt;
    LowonganSejarah ls;
    RetroServer retroServer;



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ketuk sekali lagi untuk keluar... ", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer_perusahaan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        //Nerima email sesudah login
        Intent iGet = getIntent();
        Bundle bundle = iGet.getExtras();
        if (bundle != null) {
            email = (String) bundle.get("email");
            role = (String) bundle.get("role");
        }

        //Set Fragment
        lowonganAktif();


        //inisialisasi Drawer
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Button sidebar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(UtamaPerusahaan.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        // show it
        progressDialog.show();


        //get size checkbox
        ApiRequest apiSize = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelKeahlian> getCountKeahlian = apiSize.getCountKeahlian();
        getCountKeahlian.enqueue(new Callback<ResponseModelKeahlian>() {
            @Override
            public void onResponse(Call<ResponseModelKeahlian> call, Response<ResponseModelKeahlian> response) {
                sizeKeahlian = response.body().getSize().toString();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModelKeahlian> call, Throwable t) {

            }
        });

        //button tambah lowongan
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Anjay", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            Intent i = new Intent(UtamaPerusahaan.this,BuatLowongan.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("size",sizeKeahlian);
            startActivity(i);


            }
        });

        //menu event listener
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }






        //inisialisasi sidebar header
        View viewHeader = navigationView.getHeaderView(0);
        tNama = (TextView)viewHeader.findViewById(R.id.tvNamaPerusahaan);
        tEmail = (TextView)viewHeader.findViewById(R.id.tvEmailPerusahaan);
        iFoto = (ImageView)viewHeader.findViewById(R.id.ivProfilePerusahaan);

        tAktif = (TextView)findViewById(R.id.tvLowonganAktif);
        tTutup = (TextView)findViewById(R.id.tvLowonganTutup);
        tSejarah = (TextView)findViewById(R.id.tvLowonganSejarah);

        session = new Session(UtamaPerusahaan.this);

        tAktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowonganAktif();
            }
        });

        tTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowonganTutup();
            }
        });

        tSejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowonganSejarah();
            }
        });


        retroServer = new RetroServer();
        if (session.getFoto() != null ){
            Glide.with(UtamaPerusahaan.this).load(retroServer.url()+session.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(iFoto);
            iFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(UtamaPerusahaan.this, DetailFoto.class);
                    startActivity(i);
                }
            });
        }



        //Jika role ada maka hanya set dari role, jika belum akan query data email dari login
        if (session.getRole()!=null){
            tNama.setText(session.getNama());
            tEmail.setText(session.getEmail());
        }else {
            Perusahaan perusahaan;
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            retrofit2.Call<ResponseModelPerusahaan> simpleDetail = api.simpleDetail(email);
            simpleDetail.enqueue(new Callback<ResponseModelPerusahaan>() {
                @Override
                public void onResponse(Call<ResponseModelPerusahaan> call, Response<ResponseModelPerusahaan> response) {
                    Log.d("RETRO", "response : " + response.body().toString());
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    List<Perusahaan> detail = response.body().getData();
                    Perusahaan perusahaan = detail.get(0);


                    session.setId(perusahaan.getId());
                    session.setEmail(perusahaan.getEmail());
                    session.setNama(perusahaan.getNama());
                    session.setFoto(perusahaan.getFoto());
                    session.setRole(role);

                    tNama.setText(session.getNama());
                    tEmail.setText(session.getEmail());
                    if (session.getFoto() != null){
                        Glide.with(UtamaPerusahaan.this).load(retroServer.url()+session.getFoto())
                                .apply(RequestOptions.circleCropTransform())
                                .into(iFoto);
                    }

                    Toast.makeText(UtamaPerusahaan.this, "Hallo " + session.getNama(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseModelPerusahaan> call, Throwable t) {
                    Log.e("Retro", " Response Error :" + t.getMessage());
                    Toast.makeText(UtamaPerusahaan.this, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    void lowonganAktif(){
        la = new LowonganAktif();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.fgLowonganPerusahaan, la);
        ft.commit();
    }

    void lowonganTutup(){
        lt = new LowonganTutup();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.fgLowonganPerusahaan, lt);
        ft.commit();
    }

    void lowonganSejarah(){
        ls = new LowonganSejarah();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.fgLowonganPerusahaan, ls);
        ft.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_profil){
            startActivity(new Intent(UtamaPerusahaan.this, ProfilPerusahaan.class));
        }

        if (id == R.id.nav_password){
            startActivity(new Intent(UtamaPerusahaan.this, GantiPasswordPerusahaan.class));
        }

        if(id == R.id.nav_logout){
            session.logout();
            Intent i = new Intent(UtamaPerusahaan.this, LoginPage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }


        return false;
    }
}
