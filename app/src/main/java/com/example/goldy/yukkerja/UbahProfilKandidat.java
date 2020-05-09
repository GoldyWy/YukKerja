package com.example.goldy.yukkerja;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pekerja;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.util.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahProfilKandidat extends AppCompatActivity {
    private ImageView iFoto, iCheck;
    private EditText eNamaDepan, eNamaBelakang, eTelp, eAlamat, eDeskripsi, eGaji;
    private Spinner eLokasi;
    private TextView tUbah;
    Session session;
    RetroServer retroServer;
    private Bitmap bitmap;
    private final  int IMG_REQUEST = 1;
    private String url_img ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil_kandidat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUbahProfilKandidat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Ubah Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        ActivityCompat.requestPermissions(UbahProfilKandidat.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tUbah = (TextView)findViewById(R.id.tvUbahProfilKandidat);
        eNamaDepan = (EditText)findViewById(R.id.etUbahNamaDepan);
        eNamaBelakang = (EditText)findViewById(R.id.etUbahNamaBelakang);
        eTelp = (EditText)findViewById(R.id.etUbahTeleponKandidat);
        eAlamat = (EditText)findViewById(R.id.etUbahAlamatKandidat);
        eDeskripsi = (EditText)findViewById(R.id.etUbahDeskripsiDiri);
        eGaji = (EditText)findViewById(R.id.etUbahGaji);
        eLokasi = (Spinner)findViewById(R.id.etLokasiKerja);
        iFoto = (ImageView) findViewById(R.id.ivUbahProfilKandidat);
        iCheck = (ImageView)findViewById(R.id.ivCheckUbahProfil);

        final String[] items = new String[]{"Aceh", "Sumatra Utara", "Sumatra Barat", "Riau","Jambi","Sumatra Selatan","Bengkulu",
                "Lampung","Bangka Belitung","Kepulauan Riau","Jakarta","Jawa Barat","Jawa Tengah","Yogyakarta","Jawa Timur","Banten",
                "Bali","Nusa Tenggara Barat","Nusa Tenggara Timur","Kalimantan Barat","Kalimantan Tengah","Kalimantan Selatan","Kalimantan Timur",
                "Kalimantan Utara","Sulawesi Utara","Sulawesi Tengah","Sulawesi Selatan","Sulawesi Tenggara","Sulawesi Barat","Maluku","Maluku Utara",
                "Gorontalo","Papua","Papua Barat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        eLokasi.setAdapter(adapter);

        session = new Session(UbahProfilKandidat.this);
        retroServer = new RetroServer();
        String alamat = retroServer.url()+session.getFoto();
        if (session.getFoto() != null){
            Glide.with(UbahProfilKandidat.this).load(retroServer.url()+session.getFoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(iFoto);
            iFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(UbahProfilKandidat.this, DetailFoto.class);
                    startActivity(i);
                }
            });
        }

        final ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPekerja> detailPekerja = apiRequest.detailPekerja(session.getId());
        detailPekerja.enqueue(new Callback<ResponseModelPekerja>() {
            @Override
            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                Pekerja pekerja = response.body().getDetail();
                eNamaDepan.setText(pekerja.getNama_depan());
                eNamaBelakang.setText(pekerja.getNama_belakang());
                eTelp.setText(pekerja.getNomor_telp());
                eAlamat.setText(pekerja.getAlamat());
                eDeskripsi.setText(pekerja.getDeskripsi());
                eGaji.setText(pekerja.getGaji_harapan());

                if (pekerja.getLokasi_kerja()!=null) {
                    for (int i = 0; i < items.length; i++) {
                        if (pekerja.getLokasi_kerja().equals(items[i])) {
                            eLokasi.setSelection(i);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                Toast.makeText(UbahProfilKandidat.this,"Oops ada kesalahan...",Toast.LENGTH_LONG).show();
            }
        });

        tUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        iCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namadepan = "";
                String namabelakang ="";
                String telp = "";
                String alamat = "";
                String lokasi = "";
                String desk = "";
                String gaji = "";
                String fotolama = "";

                namadepan = eNamaDepan.getText().toString();
                namabelakang = eNamaBelakang.getText().toString();
                telp = eTelp.getText().toString();
                alamat = eAlamat.getText().toString();
                lokasi = eLokasi.getSelectedItem().toString();
                desk = eDeskripsi.getText().toString();
                gaji = eGaji.getText().toString();
                fotolama = session.getFoto();


                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(UbahProfilKandidat.this);
                progressDialog.setMessage("Mohon tunggu....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (url_img.equals("")){
                    if (namadepan.equals("")){
                        Toast.makeText(UbahProfilKandidat.this,"Nama depan tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if (namabelakang.equals("")){
                        Toast.makeText(UbahProfilKandidat.this,"Nama belakang tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if (telp.equals("")){
                        Toast.makeText(UbahProfilKandidat.this,"Nomor Telepon tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelPekerja> ubahProfilPekerja = apiRequest.updateProfilPekerja(session.getId(),namadepan,namabelakang,telp,alamat,lokasi,desk,gaji,fotolama);
                        ubahProfilPekerja.enqueue(new Callback<ResponseModelPekerja>() {
                            @Override
                            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                                progressDialog.dismiss();
                                Pekerja pekerja = response.body().getDetail();

                                session.setNama(pekerja.getNama_depan());
                                session.setFoto(pekerja.getFoto());

                                Toast.makeText(UbahProfilKandidat.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UbahProfilKandidat.this,UtamaKandidat.class);
                                i.putExtra("UbahProfil","1");
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);


                            }

                            @Override
                            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("Retro", " Response Error :" + t.getMessage());
                                Toast.makeText(UbahProfilKandidat.this, "Oops ada kesalahan...", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                }else {
                    if (namadepan.equals("")){
                        Toast.makeText(UbahProfilKandidat.this,"Nama depan tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if (namabelakang.equals("")){
                        Toast.makeText(UbahProfilKandidat.this,"Nama belakang tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if (telp.equals("")){
                        Toast.makeText(UbahProfilKandidat.this,"Nomor Telepon tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{

                        File file = createTempFile(bitmap);
                        final RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                        MultipartBody.Part foto = MultipartBody.Part.createFormData("foto", file.getName(), reqFile);
                        RequestBody rId = RequestBody.create(MediaType.parse("multipart/form-file"),session.getId());
                        RequestBody rNamaDepan = RequestBody.create(MediaType.parse("multipart/form-file"),namadepan);
                        RequestBody rNamaBelakang = RequestBody.create(MediaType.parse("multipart/form-file"),namabelakang);
                        RequestBody rTelepon = RequestBody.create(MediaType.parse("multipart/form-file"),telp);
                        RequestBody rAlamat = RequestBody.create(MediaType.parse("multipart/form-file"),alamat);
                        RequestBody rLokasi = RequestBody.create(MediaType.parse("multipart/form-file"),lokasi);
                        RequestBody rDeskripsi = RequestBody.create(MediaType.parse("multipart/form-file"),desk);
                        RequestBody rGaji = RequestBody.create(MediaType.parse("multipart/form-file"),gaji);
                        RequestBody rFotolama;

                        if (fotolama == null){
                            rFotolama = RequestBody.create(MediaType.parse("multipart/form-file"),"");
                        }else {
                            rFotolama = RequestBody.create(MediaType.parse("multipart/form-file"),fotolama);
                        }

                        ApiRequest apiRequest1 = RetroServer.getClient().create(ApiRequest.class);
                        retrofit2.Call<ResponseModelPekerja> ubahProfilPekerjaFoto = apiRequest.updateProfilPekerjaFoto(foto,rId,rNamaDepan,rNamaBelakang,rTelepon,rAlamat,rLokasi,rDeskripsi,rGaji,rFotolama);
                        ubahProfilPekerjaFoto.enqueue(new Callback<ResponseModelPekerja>() {
                            @Override
                            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                                progressDialog.dismiss();
                                Pekerja pekerja = response.body().getDetail();

                                session.setNama(pekerja.getNama_depan());
                                session.setFoto(pekerja.getFoto());

                                Toast.makeText(UbahProfilKandidat.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UbahProfilKandidat.this,UtamaKandidat.class);
                                i.putExtra("UbahProfil","1");
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("Retro", " Response Error :" + t.getMessage());
                                Toast.makeText(UbahProfilKandidat.this, "Oops ada kesalahan...", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //akses diterima
                } else {
                    onBackPressed();
                    Toast.makeText(UbahProfilKandidat.this, "Akses membaca penyimpanan ditolak...", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }


    }
}
