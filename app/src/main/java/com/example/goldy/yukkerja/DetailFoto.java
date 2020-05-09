package com.example.goldy.yukkerja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.util.Session;

public class DetailFoto extends AppCompatActivity {
    private ImageView iFoto;
    Session session;
    RetroServer retroServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_foto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetailFoto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iFoto = (ImageView)findViewById(R.id.ivFotoDetail);

        Intent i = getIntent();
        String foto = "";
        foto = i.getStringExtra("foto");

        session = new Session(DetailFoto.this);
        retroServer = new RetroServer();

        if (!foto.equals("")){
            Glide.with(DetailFoto.this).load(retroServer.url()+foto)
                    .into(iFoto);
        }else{
            if (session.getFoto() != null){
                Glide.with(DetailFoto.this).load(retroServer.url()+session.getFoto())
                        .into(iFoto);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
