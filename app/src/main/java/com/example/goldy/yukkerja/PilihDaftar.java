package com.example.goldy.yukkerja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PilihDaftar extends AppCompatActivity {
    private Button bKandidat, bPerusahaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_daftar);

        bKandidat = (Button)findViewById(R.id.btnKandidat);
        bPerusahaan = (Button)findViewById(R.id.btnPerusahaan);

        bKandidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PilihDaftar.this,RegisterPage.class));
            }
        });

        bPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PilihDaftar.this,DaftarPerusahaan.class));
            }
        });

    }
}
