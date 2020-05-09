package com.example.goldy.yukkerja;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.goldy.yukkerja.api.ApiRequest;
import com.example.goldy.yukkerja.api.RetroServer;
import com.example.goldy.yukkerja.model.Pekerja;
import com.example.goldy.yukkerja.model.Pkeahlian;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPendaftar extends AppCompatActivity {
    private ImageView iFoto;
    private TextView tNama, tJk, tStatus, tEmail, tTelp, tAlamat, tDesk, tKeahlian, tGaji, tLokasi;
    private Button bTelp, bResume;
    String ekstensi = "";
    RetroServer retroServer;
    Pekerja pekerja;
    public static final int progress_bar_type = 0;
    ProgressDialog progressDialog2;
    private List<Pkeahlian> pkeahlians;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pendaftar);

        iFoto = (ImageView)findViewById(R.id.ivPendaftar);
        tNama = (TextView)findViewById(R.id.tvNamaPendaftar);
        tJk = (TextView)findViewById(R.id.tvJkPendaftar);
        tStatus = (TextView)findViewById(R.id.tvStatusPendaftar);
        tEmail = (TextView)findViewById(R.id.tvEmailPendaftar);
        tTelp = (TextView)findViewById(R.id.tvTelpPendaftar);
        tAlamat = (TextView)findViewById(R.id.tvAlamatPendaftar);
        tDesk = (TextView)findViewById(R.id.tvDeskPendaftar);
        tKeahlian = (TextView)findViewById(R.id.tvKeahlianPendaftar);
        tGaji = (TextView)findViewById(R.id.tvGajiPendaftar);
        tLokasi = (TextView)findViewById(R.id.tvLokasiPendaftar);
        bTelp = (Button)findViewById(R.id.btnTeleponPendaftar);
        bResume = (Button)findViewById(R.id.btnResumePendaftar);

        retroServer = new RetroServer();
        progressDialog2 = new ProgressDialog(DetailPendaftar.this);
        progressDialog2.setMessage("Sedang mengunduh...");
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setCancelable(false);

        verifyStoragePermissions(DetailPendaftar.this);

        Intent i = getIntent();
        String id = i.getStringExtra("id");

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(DetailPendaftar.this);
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelPekerja> getDetailPendaftar = apiRequest.getDetailPendaftar(id);
        getDetailPendaftar.enqueue(new Callback<ResponseModelPekerja>() {
            @Override
            public void onResponse(Call<ResponseModelPekerja> call, Response<ResponseModelPekerja> response) {
                progressDialog.dismiss();
                if (response.body().getDetail() != null){
                    pekerja = response.body().getDetail();
                    pkeahlians = pekerja.getPkeahlian();
                    String keahlian = "";

                    if (pkeahlians.size()>0){
                        for (int i = 0; i < pkeahlians.size(); i++){
                            keahlian += "- "+pkeahlians.get(i).getKeahlian_nama() +"("+pkeahlians.get(i).getTingkat()+")";
                            if (i != pkeahlians.size()-1){
                                keahlian += "\n";
                            }

                        }
                    }




                    if (pekerja.getFoto() != null){
                        Glide.with(DetailPendaftar.this).load(retroServer.url()+pekerja.getFoto())
                                .apply(RequestOptions.circleCropTransform())
                                .into(iFoto);
                    }

                    tNama.setText(pekerja.getNama_depan()+" "+pekerja.getNama_belakang());
                    tJk.setText(pekerja.getJk());

                    if (pekerja.getStatus().equals("1")){
                        tStatus.setText("Sedang Mencari");
                    }else{
                        tStatus.setText("Tidak Mencari");
                    }

                    tEmail.setText(pekerja.getEmail());
                    tTelp.setText(pekerja.getNomor_telp());

                    if (pekerja.getAlamat()!=null){
                        tAlamat.setText(pekerja.getAlamat());
                    }else {
                        tAlamat.setText("Pendaftar belum mengisi alamat...");
                    }

                    if (pekerja.getDeskripsi()!=null){
                        tDesk.setText(pekerja.getDeskripsi());
                    }else {
                        tDesk.setText("Pendaftar belum mengisi deskripsi diri...");
                    }

                    if (!keahlian.equals("")){
                        tKeahlian.setText(keahlian);
                    }else{
                        tKeahlian.setText("Pendaftar belum menambahkan keahlian...");
                    }


                    if (pekerja.getGaji_harapan()!=null){
                        tGaji.setText(Rupiah(Double.parseDouble(pekerja.getGaji_harapan())));
                    }else {
                        tGaji.setText("Pendaftar belum mengisi harapan gaji...");
                    }

                    if (pekerja.getLokasi_kerja()!=null){
                        tLokasi.setText(pekerja.getLokasi_kerja());
                    }else {
                        tLokasi.setText("Pendaftar belum mengisi harapan lokasi kerja...");
                    }

                    if (pekerja.getResume()!=null){
                        String resume = pekerja.getResume();
                        String[] pecah = resume.split("\\.");
                        ekstensi =  pecah[pecah.length-1];
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseModelPekerja> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

        bTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pekerja.getNomor_telp(), null));
                startActivity(intent);
            }
        });

        bResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pekerja.getResume()!= null) {
                    File dir = new File("/sdcard/download/yukkerja");
                    try{
                        if(dir.mkdir()) {
                            System.out.println("Directory created");
                        } else {
                            System.out.println("Directory is not created");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    new DownloadFileFromURL().execute(retroServer.url() + pekerja.getResume());
                }else{
                    Toast.makeText(DetailPendaftar.this,"Kandidat belum memperbarui resume...",Toast.LENGTH_LONG).show();
                }
            }
        });






    }

    public String Rupiah(double rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        String rp = kursIndonesia.format(rupiah);

        return rp;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                OutputStream output = new FileOutputStream("/sdcard/download/yukkerja/"+pekerja.getNama_depan()+pekerja.getNama_belakang()+"Resume."+ekstensi);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
            progressDialog2.show();

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
//            dismissDialog(progress_bar_type);
            progressDialog2.dismiss();
            Toast.makeText(DetailPendaftar.this,"Berhasil tersimpan di sdcard/download/yukkerja",Toast.LENGTH_LONG).show();
        }

    }
}

