package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pekerja;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.util.Session;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtamaKandidat extends AppCompatActivity {
    private BerandaKandidat berandaKandidat;
    private NotifikasiKandidat notifikasiKandidat;
    private LamaranKandidat lamaranKandidat;
    private ProfilKandidat profilKandidat;
    boolean doubleBackToExitPressedOnce = false;
    Session session;
    private String email, role;
    private Pekerja pekerja;
    RetroServer retroServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_kandidat);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        session = new Session(UtamaKandidat.this);
        retroServer = new RetroServer();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(UtamaKandidat.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Log.e("Token : ", newToken);

                    }
                });

                //Nerima email sesudah login
                Intent iGet = getIntent();
        Bundle bundle = iGet.getExtras();
        if (bundle != null) {
            email = (String) bundle.get("email");
            role = (String) bundle.get("role");
        }




        if(session.getRole()==null){
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(UtamaKandidat.this);
            progressDialog.setMessage("Mohon tunggu....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
            retrofit2.Call<ResponseModelPekerja> simpleDetail = apiRequest.simpleDetailPekerja(email);
            simpleDetail.enqueue(new Callback<ResponseModelPekerja>() {
                @Override
                public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                    progressDialog.dismiss();
                    pekerja = response.body().getDetail();
                    session.setId(pekerja.getId());
                    session.setEmail(pekerja.getEmail());
                    session.setNama(pekerja.getNama_depan());
                    session.setFoto(pekerja.getFoto());
                    session.setRole(role);
                    session.setStatus(pekerja.getStatus());
                    session.setJk(pekerja.getJk());
                    setBerandaKandidat();
                }

                @Override
                public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(UtamaKandidat.this,"Oops ada kesalahan...",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            setBerandaKandidat();
        }

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        setBerandaKandidat();
                        return true;
                    case R.id.navigation_notif:
                        setNotifikasiKandidat();
                        return true;
                    case R.id.navigation_lamaran:
                        setLamaranKandidat();
                        return true;
                    case R.id.navigation_profil:
                        setProfilKandidat();
                        return true;
                    default:
                        return false;
                }
            }
        });



        Intent checkFragment = getIntent();
        if (checkFragment.hasExtra("UbahProfil")){
            setProfilKandidat();
        }
        if (checkFragment.hasExtra("UbahPassword")){
            setProfilKandidat();
        }





    }

    void setBerandaKandidat(){
        berandaKandidat = new BerandaKandidat();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.containerContent, berandaKandidat);
        ft.commit();
    }
    void setNotifikasiKandidat(){
        notifikasiKandidat = new NotifikasiKandidat();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.containerContent, notifikasiKandidat);
        ft.commit();
    }
    void setLamaranKandidat(){
        lamaranKandidat = new LamaranKandidat();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.containerContent, lamaranKandidat);
        ft.commit();
    }
    void setProfilKandidat(){
        profilKandidat = new ProfilKandidat();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.containerContent, profilKandidat);
        ft.commit();
    }

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
}
