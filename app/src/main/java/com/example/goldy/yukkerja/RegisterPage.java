package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModel;

import java.util.regex.Pattern;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterPage extends AppCompatActivity {
    private TextView tMasuk;
    private Button bDaftar;
    private EditText eEmail, eNamaDepan, eNamaBelakang, ePassword, eTelepon;
    private RadioButton rPria, rWanita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        tMasuk = (TextView)findViewById(R.id.tvMasuk);
        rPria = (RadioButton)findViewById(R.id.rbPria);
        rWanita = (RadioButton)findViewById(R.id.rbWanita);
        bDaftar = (Button)findViewById(R.id.btnDaftar);

        eEmail = (EditText)findViewById(R.id.etEmail);
        eNamaDepan = (EditText)findViewById(R.id.etNamaDepan);
        eNamaBelakang = (EditText)findViewById(R.id.etNamaBelakang);
        ePassword = (EditText)findViewById(R.id.etPassword);
        eTelepon = (EditText)findViewById(R.id.etTelepon);



        bDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String jk = "";
                if (rPria.isChecked()){
                    jk = "Pria";
                }
                if (rWanita.isChecked()){
                    jk = "Wanita";
                }
                String email = eEmail.getText().toString();
                String namadepan = eNamaDepan.getText().toString();
                String namabelakang = eNamaBelakang.getText().toString();
                String password = ePassword.getText().toString();
                String telepon = eTelepon.getText().toString();

                if (jk.equals("")){
                    Toast.makeText(RegisterPage.this,"Pilih jenis kelamin anda...",Toast.LENGTH_LONG).show();
                }else if (!email.matches(emailPattern)){
                    Toast.makeText(RegisterPage.this,"Email tidak sesuai format...",Toast.LENGTH_LONG).show();
                }else if (email.equals("")){
                    Toast.makeText(RegisterPage.this,"Email tidak boleh kosong...",Toast.LENGTH_LONG).show();
                } else if (namadepan.equals("")){
                    Toast.makeText(RegisterPage.this,"Nama depan tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (namabelakang.equals("")){
                    Toast.makeText(RegisterPage.this,"Nama belakang tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (password.equals("")){
                    Toast.makeText(RegisterPage.this,"Password tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if(password.length() < 6){
                    Toast.makeText(RegisterPage.this,"Password minimal 6 karakter...",Toast.LENGTH_LONG).show();
                }else if(telepon.equals("")){
                    Toast.makeText(RegisterPage.this,"Telepon tidak boleh kosong...",Toast.LENGTH_LONG).show();
                } else {

                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModel> daftarPekerja = api.daftarPekerja(email,namadepan,namabelakang,password,jk,telepon);
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(RegisterPage.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    // show it
                    progressDialog.show();

                    daftarPekerja.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            progressDialog.dismiss();
                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            if (status.equals("1")){
                                Toast.makeText(RegisterPage.this,message,Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisterPage.this, LoginPage.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(RegisterPage.this,message,Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Log.e("Retro"," Response Error :"+t.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(RegisterPage.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                        }
                    });


                }







            }
        });

        rPria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rPria.setChecked(true);
                rWanita.setChecked(false);
            }
        });

        rWanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rPria.setChecked(false);
                rWanita.setChecked(true);
            }
        });


        tMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this, LoginPage.class));
            }
        });


    }

}
