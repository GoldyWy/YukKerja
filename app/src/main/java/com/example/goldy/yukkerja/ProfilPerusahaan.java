package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Perusahaan;
import com.example.goldy.yukkerja.model.ResponseModelPerusahaan;
import com.example.goldy.yukkerja.util.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilPerusahaan extends AppCompatActivity {
    private ImageView iFoto, iCheck;
    private EditText eNama, eTelp, eAlamat, eInformasi;
    private TextView tUbah;
    Session session;
    RetroServer retroServer;
    private Bitmap bitmap;
    private final  int IMG_REQUEST = 1;
    private String url_img ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_perusahaan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfilPerusahaan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Ubah Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tUbah = (TextView)findViewById(R.id.tvGantiProfil);
        eNama = (EditText)findViewById(R.id.etGantiNamaPerusahaan);
        eTelp = (EditText)findViewById(R.id.etGantiTelpPerusahaan);
        eAlamat = (EditText)findViewById(R.id.etGantiAlamatPerusahaan);
        eInformasi = (EditText)findViewById(R.id.etGantiInformasiPerusahaan);
        iFoto = (ImageView)findViewById(R.id.ivEditProfil);
        iCheck = (ImageView)findViewById(R.id.ivCheck);


        session = new Session(ProfilPerusahaan.this);
        retroServer = new RetroServer();
        String alamat = retroServer.url()+session.getFoto();
        if (session.getFoto() != null){
            Glide.with(ProfilPerusahaan.this).load(retroServer.url()+session.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(iFoto);
            iFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProfilPerusahaan.this, DetailFoto.class);
                    startActivity(i);
                }
            });
        }


        tUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        iCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama ="";
                String telp="";
                String alamat ="";
                String informasi ="";
                String id = "";
                String fotolama ="";

                nama = eNama.getText().toString();
                telp = eTelp.getText().toString();
                alamat = eAlamat.getText().toString();
                informasi = eInformasi.getText().toString();
                id = session.getId().toString();
                fotolama = session.getFoto();

                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(ProfilPerusahaan.this);
                progressDialog.setMessage("Mohon tunggu....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();

                //Tanpa Foto
                if (url_img.equals("")){
                    if (nama.equals("")){
                        Toast.makeText(ProfilPerusahaan.this,"Nama tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if(telp.equals("")){
                        Toast.makeText(ProfilPerusahaan.this,"Telepon boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelPerusahaan> updateProfilePerusahaan = apiRequest.updateProfilPerusahaan(id,nama,telp,alamat,informasi,fotolama);
                        updateProfilePerusahaan.enqueue(new Callback<ResponseModelPerusahaan>() {
                            @Override
                            public void onResponse(Call<ResponseModelPerusahaan> call, Response<ResponseModelPerusahaan> response) {
                                progressDialog.dismiss();
                                List<Perusahaan> detail = response.body().getData();
                                Perusahaan perusahaan = detail.get(0);

                                session.setId(perusahaan.getId());
                                session.setEmail(perusahaan.getEmail());
                                session.setNama(perusahaan.getNama());
                                session.setFoto(perusahaan.getFoto());

                                Toast.makeText(ProfilPerusahaan.this, response.body().getMessage(), Toast.LENGTH_SHORT  ).show();
                                Log.d("RETRO", "response : " + response.body().toString());
                                Intent i = new Intent(ProfilPerusahaan.this,UtamaPerusahaan.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<ResponseModelPerusahaan> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("Retro", " Response Error :" + t.getMessage());
                                Toast.makeText(ProfilPerusahaan.this, "Oops ada kesalahan...", Toast.LENGTH_LONG).show();
                            }
                        });
                    }



                }else {
                    if (nama.equals("")){
                        Toast.makeText(ProfilPerusahaan.this,"Nama tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if(telp.equals("")){
                        Toast.makeText(ProfilPerusahaan.this,"Telepon boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        File file = createTempFile(bitmap);
                        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                        MultipartBody.Part foto = MultipartBody.Part.createFormData("foto", file.getName(), reqFile);
                        RequestBody rId = RequestBody.create(MediaType.parse("multipart/form-file"),id);
                        RequestBody rNama = RequestBody.create(MediaType.parse("multipart/form-file"),nama);
                        RequestBody rTelp = RequestBody.create(MediaType.parse("multipart/form-file"),telp);
                        RequestBody rAlamat = RequestBody.create(MediaType.parse("multipart/form-file"),alamat);
                        RequestBody rInformasi = RequestBody.create(MediaType.parse("multipart/form-file"),informasi);
                        RequestBody rFotolama;

                        if (fotolama == null){
                            rFotolama = RequestBody.create(MediaType.parse("multipart/form-file"),"");
                        }else {
                            rFotolama = RequestBody.create(MediaType.parse("multipart/form-file"),fotolama);
                        }

                        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelPerusahaan> updateProfilePerusahaanFoto = apiRequest.updateProfilPerusahaanFoto(foto,rId,rNama,rTelp,rAlamat,rInformasi,rFotolama);
                        updateProfilePerusahaanFoto.enqueue(new Callback<ResponseModelPerusahaan>() {
                            @Override
                            public void onResponse(Call<ResponseModelPerusahaan> call, Response<ResponseModelPerusahaan> response) {
                                progressDialog.dismiss();
                                List<Perusahaan> detail = response.body().getData();
                                Perusahaan perusahaan = detail.get(0);
                                session.setId(perusahaan.getId());
                                session.setEmail(perusahaan.getEmail());
                                session.setNama(perusahaan.getNama());
                                session.setFoto(perusahaan.getFoto());
                                Toast.makeText(ProfilPerusahaan.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ProfilPerusahaan.this,UtamaPerusahaan.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<ResponseModelPerusahaan> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("Retro", " Response Error :" + t.getMessage());
                                Toast.makeText(ProfilPerusahaan.this, "Oops ada kesalahan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPerusahaan> detailPerusahaan = apiRequest.detailPerusahaan(session.getEmail());
        detailPerusahaan.enqueue(new Callback<ResponseModelPerusahaan>() {
            @Override
            public void onResponse(Call<ResponseModelPerusahaan> call, Response<ResponseModelPerusahaan> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String status = response.body().getStatus();
                String message = response.body().getMessage();
                List<Perusahaan> detail = response.body().getData();
                Perusahaan perusahaan = detail.get(0);

                String nama = perusahaan.getNama();
                String telp = perusahaan.getNomor_telp();
                String alamat = perusahaan.getAlamat();
                String informasi = perusahaan.getInformasi();

                eNama.setText(nama);
                eTelp.setText(telp);
                eAlamat.setText(alamat);
                eInformasi.setText(informasi);
            }

            @Override
            public void onFailure(Call<ResponseModelPerusahaan> call, Throwable t) {
                Log.e("Retro", " Response Error :" + t.getMessage());
                Toast.makeText(ProfilPerusahaan.this, "Koneksi Gagal", Toast.LENGTH_LONG).show();
            }
        });










    }
    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.jpg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                url_img = path.toString();
                iFoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }
}
