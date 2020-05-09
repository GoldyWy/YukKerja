package com.example.goldy.yukkerja;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModelAdmin;
import com.example.goldy.yukkerja.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtamaAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    private TextView tNama,tEmail;
    Session session;
    private TextView tTotalKandidat, tKandidatAktif, tKandidatNon, tTotalPerusahaan, tTotalLowongan, tLowonganAktif, tLowonganTutup, tLowonganSejarah;

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
        setContentView(R.layout.layout_drawer_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Dashboard Admin");

        //inisialisasi Drawer
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        session = new Session(UtamaAdmin.this);

        //Button sidebar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view_admin);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }

        //inisialisasi sidebar header
        View viewHeader = navigationView.getHeaderView(0);
        tNama = (TextView)viewHeader.findViewById(R.id.tvNamaAdmin);
        tEmail = (TextView)viewHeader.findViewById(R.id.tvEmailAdmin);

        tTotalKandidat = (TextView)findViewById(R.id.tvTotalKandidat);
        tKandidatAktif = (TextView)findViewById(R.id.tvAktifKandidat);
        tKandidatNon = (TextView)findViewById(R.id.tvNonAktifKandidat);
        tTotalPerusahaan = (TextView)findViewById(R.id.tvTotalPerusahaan);
        tTotalLowongan = (TextView)findViewById(R.id.tvTotalLowongan);
        tLowonganAktif = (TextView)findViewById(R.id.tvLowonganAktif);
        tLowonganTutup = (TextView)findViewById(R.id.tvLowonganTutup);
        tLowonganSejarah = (TextView)findViewById(R.id.tvLowonganSejarah);


        if (session.getRole()!=null){
            tNama.setText(session.getNama());
            tEmail.setText(session.getEmail());
        }

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelAdmin> getHalamanUtama = apiRequest.getHalamanUtama(session.getId());
        getHalamanUtama.enqueue(new Callback<ResponseModelAdmin>() {
            @Override
            public void onResponse(Call<ResponseModelAdmin> call, Response<ResponseModelAdmin> response) {
                tTotalKandidat.setText(response.body().getPekerja());
                tKandidatAktif.setText(response.body().getPekerjaaktif());
                tKandidatNon.setText(response.body().getPekerjanonaktif());
                tTotalPerusahaan.setText(response.body().getPerusahaan());
                tTotalLowongan.setText(response.body().getLowongan());
                tLowonganAktif.setText(response.body().getLowonganaktif());
                tLowonganTutup.setText(response.body().getLowongantutup());
                tLowonganSejarah.setText(response.body().getLowongansejarah());
            }

            @Override
            public void onFailure(Call<ResponseModelAdmin> call, Throwable t) {
                Toast.makeText(UtamaAdmin.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_logout_admin){
            session.logout();
            Intent i = new Intent(UtamaAdmin.this, LoginPage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        if(id == R.id.nav_keahlian_admin){
            Intent i = new Intent(UtamaAdmin.this, KeahlianAdmin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        return false;
    }
}
