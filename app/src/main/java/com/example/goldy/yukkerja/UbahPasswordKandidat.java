package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordKandidat extends AppCompatActivity {
    private EditText ePassLama, ePassBaru, eKonfPassBaru;
    private ImageView iCheck;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password_kandidat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUbahPasswordKandidat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Ganti Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ePassLama = (EditText)findViewById(R.id.etPassLamaKandidat);
        ePassBaru = (EditText)findViewById(R.id.etPassBaruKandidat);
        eKonfPassBaru = (EditText)findViewById(R.id.etKonfirmasiPassBaruKandidat);
        iCheck = (ImageView) findViewById(R.id.ivCheckUbahPassword);

        session = new Session(UbahPasswordKandidat.this);

        iCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passlama = ePassLama.getText().toString();
                String passbaru = ePassBaru.getText().toString();
                String konfpass = eKonfPassBaru.getText().toString();

                if (passlama.equals(passbaru)){
                    Toast.makeText(UbahPasswordKandidat.this,"Password lama dan baru tidak boleh sama...",Toast.LENGTH_LONG).show();
                }else if(!passbaru.equals(konfpass)){
                    Toast.makeText(UbahPasswordKandidat.this,"Password tidak sama...",Toast.LENGTH_LONG).show();
                }else if(passbaru.length() < 6){
                    Toast.makeText(UbahPasswordKandidat.this,"Password minimal 6 karakter...",Toast.LENGTH_LONG).show();
                }else{
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(UbahPasswordKandidat.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModelPekerja> ubahPassword = apiRequest.ubahPassword(session.getId(),passlama,passbaru);
                    ubahPassword.enqueue(new Callback<ResponseModelPekerja>() {
                        @Override
                        public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                            progressDialog.dismiss();
                            String status = response.body().getStatus();
                            if (status.equals("1")){
                                Toast.makeText(UbahPasswordKandidat.this, response.body().getMessage(), Toast.LENGTH_SHORT  ).show();
                                Log.d("RETRO", "response : " + response.body().toString());
                                Intent i = new Intent(UbahPasswordKandidat.this,UtamaKandidat.class);
                                i.putExtra("UbahPassword","1");
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }else{
                                Toast.makeText(UbahPasswordKandidat.this, response.body().getMessage(), Toast.LENGTH_SHORT  ).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(UbahPasswordKandidat.this, "Oops ada kesalahan...", Toast.LENGTH_SHORT  ).show();
                            Log.d("RETRO", "response : " + t.getMessage());
                        }
                    });

                }

            }
        });



    }
}
