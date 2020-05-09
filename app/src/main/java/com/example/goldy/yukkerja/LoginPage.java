package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModel;
import com.example.goldy.yukkerja.util.Session;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private TextView tDaftar;
    private Button bMasuk;
    private EditText eEmail,ePassword;
    boolean doubleBackToExitPressedOnce = false;
    Session session;
    private String token;

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
        setContentView(R.layout.activity_login_page);

        tDaftar = (TextView)findViewById(R.id.tvDaftar);
        eEmail = (EditText) findViewById(R.id.etlEmail);
        ePassword = (EditText)findViewById(R.id.etlPassword);
        bMasuk = (Button)findViewById(R.id.btnMasuk);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginPage.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.d("RETRO", "Token : " + token);
            }
        });



        bMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String email = eEmail.getText().toString();
                String password = ePassword .getText().toString();

                if (email.equals("")){
                    Toast.makeText(LoginPage.this,"Email tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (!email.matches(emailPattern)){
                    Toast.makeText(LoginPage.this,"Email tidak sesuai format...",Toast.LENGTH_LONG).show();
                }else if (password.equals("")){
                    Toast.makeText(LoginPage.this,"Password tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if(password.length() < 6){
                    Toast.makeText(LoginPage.this,"Password minimal 6 karakter...",Toast.LENGTH_LONG).show();
                }else {
                    bMasuk.setEnabled(false);
                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModel> masukPekerja = api.masuk(email,password,token);

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(LoginPage.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    // show it
                    progressDialog.show();

                    masukPekerja.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            progressDialog.dismiss();
                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            session = new Session(LoginPage.this,"");
                            bMasuk.setEnabled(true);

                            if (status.equals("1")){
                                if(response.body().getRole().equals("pekerja") ){
                                    Intent i = new Intent(LoginPage.this,UtamaKandidat.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.putExtra("email",email);
                                    i.putExtra("role",response.body().getRole());
                                    startActivity(i);
                                    finish();
                                }else if(response.body().getRole().equals("perusahaan")){
                                    session.setId(response.body().getId());
                                    Intent i = new Intent(LoginPage.this,UtamaPerusahaan.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.putExtra("email",email);
                                    i.putExtra("role",response.body().getRole());
                                    startActivity(i);
                                    finish();
                                }else if (response.body().getRole().equals("admin")){
                                    String nama = "Admin";
                                    session.setId(response.body().getId());
                                    session.setEmail(email);
                                    session.setNama(nama);
                                    session.setRole(response.body().getRole());
                                    Intent i = new Intent(LoginPage.this,UtamaAdmin.class);
                                    startActivity(i);
                                    finish();
                                }

                            }else {
                                Toast.makeText(LoginPage.this,message,Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Log.e("Retro"," Response Error :"+t.getMessage());
                            bMasuk.setEnabled(true);
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });




        tDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, PilihDaftar.class));
            }
        });
    }


}
