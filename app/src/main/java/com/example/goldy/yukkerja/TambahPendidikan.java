package com.example.goldy.yukkerja;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.util.Session;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPendidikan extends AppCompatActivity {
    private ImageView iSimpan;
    private EditText eBulan, eTahun, eNama, eJurusan, eNilai;
    private Spinner eKualifikasi;
    private Button bDate;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pendidikan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTambahPendidikanKandidat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Tambah Pendidikan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        eNama = (EditText)findViewById(R.id.etNamaInstitusi);
        eNilai = (EditText)findViewById(R.id.etNilaiAkhir);
        eJurusan = (EditText)findViewById(R.id.etJurusan);
        eKualifikasi = (Spinner)findViewById(R.id.sKualifikasi);
        eBulan = (EditText)findViewById(R.id.etBulanWisuda);
        eTahun = (EditText)findViewById(R.id.etTahunWisuda);
        bDate = (Button) findViewById(R.id.btnDate);
        iSimpan = (ImageView)findViewById(R.id.ivSimpanPendidikan);
        session = new Session(TambahPendidikan.this);


        final String[] items = new String[]{"Pilih Kualifikasi","SMU/SMK/STM","Diploma (D3)","Sarjana (S1)","Magister (S2)","Doktor (S3)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        eKualifikasi.setAdapter(adapter);

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TambahPendidikan.this);
                // Get the layout inflater
                LayoutInflater inflater = TambahPendidikan.this.getLayoutInflater();

                Calendar cal = Calendar.getInstance();

                View dialog = inflater.inflate(R.layout.monthyear_dialog, null);
                final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
                final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setValue(cal.get(Calendar.MONTH));

                final int year = cal.get(Calendar.YEAR);
                yearPicker.setMinValue(1960);
                yearPicker.setMaxValue(year);
                yearPicker.setValue(year);
                builder.setTitle("Pilih Tanggal Wisuda");
                builder.setView(dialog).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String bulan = ""+getMonth(monthPicker.getValue()) ;
                        String tahun = ""+yearPicker.getValue();
                        eBulan.setText(bulan);
                        eTahun.setText(tahun);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

        iSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = session.getId();
                String nama = eNama.getText().toString();
                String bulan = eBulan.getText().toString();
                String tahun = eTahun.getText().toString();
                String kualifikasi = eKualifikasi.getSelectedItem().toString();
                String jurusan = eJurusan.getText().toString();
                String nilai = eNilai.getText().toString();

                if (nama.equals("")){
                    Toast.makeText(TambahPendidikan.this,"Nama institusi tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                }else if (bulan.equals("") && tahun.equals("")){
                    Toast.makeText(TambahPendidikan.this,"Tanggal wisuda tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                }else if (kualifikasi.equals("Pilih Kualifikasi")){
                    Toast.makeText(TambahPendidikan.this,"Kualifikasi tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                }else if (jurusan.equals("")){
                    Toast.makeText(TambahPendidikan.this,"Jurusan tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                }else if (nilai.equals("")){
                    Toast.makeText(TambahPendidikan.this,"Nilai tidak boleh kosong...",Toast.LENGTH_SHORT).show();
                }else{
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(TambahPendidikan.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                    retrofit2.Call<ResponseModelPekerja> insertPendidikan = apiRequest.insertPendidikan(id,nama,bulan,tahun,kualifikasi,jurusan,nilai);
                    insertPendidikan.enqueue(new Callback<ResponseModelPekerja>() {
                        @Override
                        public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                            progressDialog.dismiss();
                            Toast.makeText(TambahPendidikan.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(TambahPendidikan.this, PendidikanKandidat.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        }

                        @Override
                        public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(TambahPendidikan.this,"Oops ada kesalahan...",Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });







    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
