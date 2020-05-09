package com.example.goldy.yukkerja;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pekerja;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.util.Session;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeKandidat extends AppCompatActivity {
    private TextView tUnggah, tResumeLama;
    private ImageView iDone;
    private final  int FILE_REQUEST = 1;
    Session session;
    Pekerja pekerja;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_kandidat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarResumeKandidat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Resume");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ActivityCompat.requestPermissions(ResumeKandidat.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        iDone = (ImageView)findViewById(R.id.ivDone) ;
        tUnggah = (TextView)findViewById(R.id.btnUnggahResume);
        tResumeLama = (TextView)findViewById(R.id.tvResumeLama);
        session = new Session(ResumeKandidat.this);

        iDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();
            }
        });

        ApiRequest apiRequest  = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPekerja> detailPekerja = apiRequest.detailPekerja(session.getId());
        detailPekerja.enqueue(new Callback<ResponseModelPekerja>() {
            @Override
            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                pekerja = response.body().getDetail();
                String filelama = pekerja.getResume();
                if (filelama!= null){
                    String foto = filelama.substring(filelama.lastIndexOf("/"));
                    tResumeLama.setText(foto.replace("/",""));
                }


            }

            @Override
            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {

            }
        });




    }

    private void selectFile()
    {
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, FILE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==FILE_REQUEST && resultCode==RESULT_OK && data!=null)
        {

            Uri path = data.getData();

            String filePath = path.getPath();
            String ext = filePath.substring(filePath.lastIndexOf("."));
//            Toast.makeText(ResumeKandidat.this,"Ekstensi File : " + ext, Toast.LENGTH_SHORT).show();

            if (ext.equals(".doc") || ext.equals(".docx") || ext.equals(".pdf") || ext.equals(".txt") || ext.equals(".rtf")){
//                tPilihan.setText(FileUtil.getPath(path,this));
                File fileGetPath = new File(path.getPath());
                File file = new File(FileUtil.getPath(path,this));


                int file_size = Integer.parseInt(String.valueOf(fileGetPath.length()/1024));
//                Toast.makeText(ResumeKandidat.this,"File size "+file_size, Toast.LENGTH_SHORT).show();
                if (file_size < 10) {

                    insertFile(file);

                }else{
                    Toast.makeText(ResumeKandidat.this,"File terlalu besar" + file_size, Toast.LENGTH_SHORT).show();
                }




            }


        }


    }

    public void insertFile(File file){
        String filelama = pekerja.getResume();
        String id = pekerja.getId();


        RequestBody rFileLama;
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-file"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody rId = RequestBody.create(MediaType.parse("multipart/form-file"),id);
        if (filelama!=null) {
             rFileLama = RequestBody.create(MediaType.parse("multipart/form-file"), filelama);
        }else{
             rFileLama = RequestBody.create(MediaType.parse("multipart/form-file"), "");
        }

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ResumeKandidat.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPekerja> updateResumePekejra = apiRequest.updateResumePekerja(body,rId,rFileLama);
        updateResumePekejra.enqueue(new Callback<ResponseModelPekerja>() {
            @Override
            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                progressDialog.dismiss();
                pekerja = response.body().getDetail();
                Toast.makeText(ResumeKandidat.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ResumeKandidat.this,ResumeKandidat.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }

            @Override
            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ResumeKandidat.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                Log.d("Error :", t.getMessage());

            }
        });

//        Toast.makeText(ResumeKandidat.this,file.getName(), Toast.LENGTH_SHORT).show();





    }

}
