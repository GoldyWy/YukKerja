package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPerusahaan extends AppCompatActivity {
    private EditText eEmail,eNama,eTelepon,ePassword;
    private Button bDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_perusahaan);

        eEmail = (EditText)findViewById(R.id.etEmailPerusahaan);
        eNama = (EditText)findViewById(R.id.etNamaPerusahaan);
        eTelepon = (EditText)findViewById(R.id.etTeleponPerusahaan);
        ePassword = (EditText)findViewById(R.id.etPasswordPerusahaan);
        bDaftar = (Button)findViewById(R.id.btnDaftarPerusahaan);

        bDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                String email = eEmail.getText().toString();
                String nama = eNama.getText().toString();
                String password = ePassword.getText().toString();
                String telepon = eTelepon.getText().toString();

                if (!email.matches(emailPattern)){
                    Toast.makeText(DaftarPerusahaan.this,"Email tidak sesuai format...",Toast.LENGTH_LONG).show();
                }else if (email.equals("")){
                    Toast.makeText(DaftarPerusahaan.this,"Email tidak boleh kosong...",Toast.LENGTH_LONG).show();
                } else if (nama.equals("")){
                    Toast.makeText(DaftarPerusahaan.this,"Nama depan tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if (password.equals("")){
                    Toast.makeText(DaftarPerusahaan.this,"Password tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else if(password.length() < 6){
                    Toast.makeText(DaftarPerusahaan.this,"Password minimal 6 karakter...",Toast.LENGTH_LONG).show();
                }else if(telepon.equals("")){
                    Toast.makeText(DaftarPerusahaan.this,"Telepon tidak boleh kosong...",Toast.LENGTH_LONG).show();
                }else{
                    ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModel> daftarPerusahaan = api.daftarPerusahaan(email,nama,password,telepon);
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(DaftarPerusahaan.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    // show it
                    progressDialog.show();


                    daftarPerusahaan.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            progressDialog.dismiss();
                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            if (status.equals("1")){
                                Toast.makeText(DaftarPerusahaan.this,message,Toast.LENGTH_LONG).show();
                                Intent i = new Intent(DaftarPerusahaan.this, LoginPage.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(DaftarPerusahaan.this,message,Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Log.e("Retro"," Response Error :"+t.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(DaftarPerusahaan.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });






    }
}
