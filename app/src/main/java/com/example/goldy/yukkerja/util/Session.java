package com.example.goldy.yukkerja.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Session(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public Session(Context context, String foto)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("foto", foto).apply();
    }

    public void setId(String id)
    {
        sharedPreferences.edit().putString("id",id).apply();
    }

    public void setNama(String nama)
    {
        sharedPreferences.edit().putString("nama",nama).apply();
    }

    public void setEmail(String email)
    {
        sharedPreferences.edit().putString("email",email).apply();
    }

    public void setFoto(String foto)
    {
        sharedPreferences.edit().putString("foto", foto).apply();
    }

    public void setStatus(String status)
    {
        sharedPreferences.edit().putString("status", status).apply();
    }

    public void setJk(String jk)
    {
        sharedPreferences.edit().putString("jk", jk).apply();
    }


    public void setRole(String role)
    {
        sharedPreferences.edit().putString("role",role).apply();
    }

    public String getId()
    {
        return sharedPreferences.getString("id",null);
    }

    public String getEmail()
    {
        return sharedPreferences.getString("email",null);
    }

    public String getNama()
    {
        return sharedPreferences.getString("nama",null);
    }

    public String getFoto()
    {
        return sharedPreferences.getString("foto",null);
    }

    public String getRole()
    {
        return sharedPreferences.getString("role",null);
    }

    public String getStatus()
    {
        return sharedPreferences.getString("status",null);
    }

    public String getJk()
    {
        return sharedPreferences.getString("jk",null);
    }

    public void logout()
    {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

}
