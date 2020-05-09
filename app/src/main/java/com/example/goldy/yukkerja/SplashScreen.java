package com.example.goldy.yukkerja;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.goldy.yukkerja.util.Session;


public class SplashScreen extends AppCompatActivity {
    private int waktu_loading=4000;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity

                session = new Session(SplashScreen.this);
                String role = session.getRole();
                if (role!=null){
                    if (role.equals("pekerja")){
                        Intent i=new Intent(SplashScreen.this, UtamaKandidat.class);
                        startActivity(i);
                        finish();
                    }else if (role.equals("perusahaan")){
                        Intent i=new Intent(SplashScreen.this, UtamaPerusahaan.class);
                        startActivity(i);
                        finish();
                    }else if (role.equals("admin")){
                        Intent i=new Intent(SplashScreen.this, UtamaAdmin.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Intent home=new Intent(SplashScreen.this, LoginPage.class);
                    startActivity(home);
                    finish();
                }




            }
        },waktu_loading);

    }
}
