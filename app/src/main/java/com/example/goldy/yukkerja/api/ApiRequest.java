package com.example.goldy.yukkerja.api;

import com.example.goldy.yukkerja.model.ResponseModel;
import com.example.goldy.yukkerja.model.ResponseModelAdmin;
import com.example.goldy.yukkerja.model.ResponseModelKeahlian;
import com.example.goldy.yukkerja.model.ResponseModelLowongan;
import com.example.goldy.yukkerja.model.ResponseModelNotifikasi;
import com.example.goldy.yukkerja.model.ResponseModelPekerja;
import com.example.goldy.yukkerja.model.ResponseModelPendaftar;
import com.example.goldy.yukkerja.model.ResponseModelPendidikan;
import com.example.goldy.yukkerja.model.ResponseModelPerusahaan;
import com.example.goldy.yukkerja.model.ResponseModelPkeahlian;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequest {

    //Pekerja
    @FormUrlEncoded
    @POST("pekerja/updateStatusPekerja")
    Call<ResponseModelPekerja> updateStatusPekerja(@Field ("id") String id,
                                                   @Field("status") String status
                                                   );

    @FormUrlEncoded
    @POST("pekerja/getPendaftarById")
    Call<ResponseModelPendaftar> getPendaftarById(@Field ("id") String id);

    @FormUrlEncoded
    @POST("pekerja/updateNotifikasi")
    Call<ResponseModelNotifikasi> updateNotifkasi(@Field ("id") String id);

    @FormUrlEncoded
    @POST("pekerja/getNotifikasi")
    Call<ResponseModelNotifikasi> getNotifikasi(@Field ("id") String id);

    @FormUrlEncoded
    @POST("pekerja/daftarLowongan")
    Call<ResponseModelPendaftar> daftarLowongan(@Field ("idpekerja") String idpekerja,
                                               @Field ("idperusahaan") String idperusahaan,
                                               @Field ("idlowongan") String idlowongan
    );

    @FormUrlEncoded
    @POST("pekerja/getDetailLowongan")
    Call<ResponseModelLowongan> getDetailLowongan(@Field ("id") String id,
                                                  @Field ("idpekerja") String idpekerja
    );

    @FormUrlEncoded
    @POST("pekerja/getCariLowongan")
    Call<ResponseModelLowongan> getCariLowongan(@Field ("cari") String cari);

    @GET("pekerja/getLowongan")
    Call<ResponseModelLowongan> getLowongan();

    @FormUrlEncoded
    @POST("pekerja/deleteKeahlianPekerja")
    Call<ResponseModelPkeahlian> deleteKeahlianPekerja(@Field ("id") String id,
                                                       @Field ("idpkeahlian") String idpkeahlian
                                                    );

    @FormUrlEncoded
    @POST("pekerja/getKeahlianPekerja")
    Call<ResponseModelPkeahlian> getKeahlianPekerja(@Field ("id") String id);

    @FormUrlEncoded
    @POST("pekerja/insertKeahlianPekerja")
    Call<ResponseModelPkeahlian> insertKeahlianPekerja(@Field ("id") String id,
                                                   @Field("keahlian") String keahlian,
                                                   @Field("tingkat") String tingkat

    );

    @Multipart
    @POST("pekerja/updateResumePekerja")
    Call<ResponseModelPekerja> updateResumePekerja(@Part MultipartBody.Part file,
                                                       @Part ("id") RequestBody rId,
                                                       @Part ("filelama") RequestBody  rFileLama
    );

    @FormUrlEncoded
    @POST("pekerja/updatePendidikan")
    Call<ResponseModelPendidikan> updatePendidikan(@Field ("id") String id,
                                                @Field("nama") String nama,
                                                @Field("bulan") String bulan,
                                                @Field("tahun") String tahun,
                                                @Field("kualifikasi") String kualifikasi,
                                                @Field("jurusan") String jurusan,
                                                @Field("nilai") String nilai
                                            );

    @FormUrlEncoded
    @POST("pekerja/deletePendidikan")
    Call<ResponseModelPendidikan> deletePendidikan(@Field ("id") String id);

    @FormUrlEncoded
    @POST("pekerja/getPendidikanByIdPekerja")
    Call<ResponseModelPendidikan> getPendidikanByIdPekerja(@Field ("id") String id);

    @FormUrlEncoded
    @POST("pekerja/insertPendidikan")
    Call<ResponseModelPekerja> insertPendidikan(@Field ("id") String id,
                                                @Field("institusi") String institusi,
                                                @Field("bulan") String bulan,
                                                @Field("tahun") String tahun,
                                                @Field("kualifikasi") String kualifikasi,
                                                @Field("jurusan") String jurusan,
                                                @Field("nilai") String nilai
                                            );

    @FormUrlEncoded
    @POST("pekerja/ubahPassword")
    Call<ResponseModelPekerja> ubahPassword(@Field ("id") String id,
                                                @Field("passlama") String passlama,
                                                @Field("passbaru") String passbaru
                                             );

    @FormUrlEncoded
    @POST("pekerja/updateProfilPekerja")
    Call<ResponseModelPekerja> updateProfilPekerja(@Field ("id") String  id,
                                                         @Field ("namadepan") String  namadepan,
                                                         @Field ("namabelakang") String  namabelakang,
                                                         @Field ("telepon") String  telepon,
                                                         @Field ("alamat") String  alamat,
                                                         @Field ("lokasi") String  lokasi,
                                                         @Field ("deskripsi") String  deskripsi,
                                                         @Field ("gajiharapan") String  gajiharapan,
                                                         @Field ("fotolama") String  fotolama
                                                   );

    @Multipart
    @POST("pekerja/updateProfilPekerja")
    Call<ResponseModelPekerja> updateProfilPekerjaFoto(@Part MultipartBody.Part foto,
                                                             @Part ("id") RequestBody rId,
                                                             @Part ("namadepan") RequestBody  rNamaDepan,
                                                             @Part ("namabelakang") RequestBody  rTelp,
                                                             @Part ("telepon") RequestBody  rTelepon,
                                                             @Part ("alamat") RequestBody  rAlamat,
                                                             @Part ("lokasi") RequestBody  rLokasi,
                                                             @Part ("deskripsi") RequestBody  rDeskripsi,
                                                             @Part ("gajiharapan") RequestBody  rGajiHarapan,
                                                             @Part ("fotolama") RequestBody  rFotolama
                                                    );

    @FormUrlEncoded
    @POST("pekerja/detailPekerja")
    Call<ResponseModelPekerja> detailPekerja(@Field("id") String id);

    @FormUrlEncoded
    @POST("pekerja/simpleDetail")
    Call<ResponseModelPekerja> simpleDetailPekerja(@Field("email") String email);


    @FormUrlEncoded
    @POST("perusahaan/gantiPassword")
    Call<ResponseModelPerusahaan> gantiPassword(@Field ("id") String id,
                                                @Field("passlama") String passlama,
                                                @Field("passbaru") String passbaru
                                                    );

    @FormUrlEncoded
    @POST("perusahaan/updateStatusPendaftar")
    Call<ResponseModelPendaftar> updateStatusPendaftar(@Field ("id") String id,
                                                       @Field("status") String status
                                                       );
    //Perusahaan
    @FormUrlEncoded
    @POST("perusahaan/getDetailPendaftar")
    Call<ResponseModelPekerja> getDetailPendaftar(@Field ("id") String id);

    @FormUrlEncoded
    @POST("perusahaan/updateLowonganPerusahaan")
    Call<ResponseModelLowongan> updateLowonganPerusahaan(@Field ("id") String  id,
                                                         @Field ("deskripsi") String  deskripsi,
                                                         @Field ("requirement") String  requirement,
                                                         @Field ("waktu") String  waktu,
                                                         @Field ("gaji1") String  gaji1,
                                                         @Field ("gaji2") String  gaji2,
                                                         @Field ("keahlian") String  keahlian,
                                                         @Field("lokasi") String lokasi
                                                           );

    @FormUrlEncoded
    @POST("perusahaan/getPendaftarByIdLowongan")
    Call<ResponseModelPendaftar> getPendaftarByIdLowongan(@Field ("id") String id);

    @FormUrlEncoded
    @POST("perusahaan/updateProfilPerusahaan")
    Call<ResponseModelPerusahaan> updateProfilPerusahaan(@Field ("id") String  id,
                                                   @Field ("nama") String  nama,
                                                   @Field ("telp") String  telp,
                                                   @Field ("alamat") String  alamat,
                                                   @Field ("informasi") String  informasi,
                                                   @Field ("fotolama") String  fotolama);

    @Multipart
    @POST("perusahaan/updateProfilPerusahaan")
    Call<ResponseModelPerusahaan> updateProfilPerusahaanFoto(@Part MultipartBody.Part foto,
                                                   @Part ("id") RequestBody rId,
                                                   @Part ("nama") RequestBody  rNama,
                                                   @Part ("telp") RequestBody  rTelp,
                                                   @Part ("alamat") RequestBody  rAlamat,
                                                   @Part ("informasi") RequestBody  rInformasi,
                                                   @Part ("fotolama") RequestBody  rFotolama
                                                   );

    @FormUrlEncoded
    @POST("perusahaan/detailPerusahaan")
    Call<ResponseModelPerusahaan> detailPerusahaan(@Field("email") String email);

    @FormUrlEncoded
    @POST("perusahaan/detailLowongan")
    Call<ResponseModelLowongan> detailLowongan(@Field("id") String id);

    @FormUrlEncoded
    @POST("perusahaan/updateStatusLowongan")
    Call<ResponseModelLowongan> updateStatusLowongan(@Field("id") String id,
                                                        @Field("status") String status);


    @FormUrlEncoded
    @POST("perusahaan/buatLowongan")
    Call<ResponseModel> buatLowongan(@Field("perusahaanId") String perusahaanId,
                                             @Field("judul") String judul,
                                             @Field("deskripsi") String deskripsi,
                                             @Field("requirement") String requirement,
                                             @Field("waktu") String waktu,
                                             @Field("gaji1") String gaji1,
                                             @Field("gaji2") String gaji2,
                                             @Field("keahlian") String keahlian,
                                             @Field("lokasi") String lokasi);

    @GET("perusahaan/getCountKeahlian")
    Call<ResponseModelKeahlian> getCountKeahlian();

    @GET("perusahaan/getKeahlian")
    Call<ResponseModelKeahlian> getKeahlian();

    @FormUrlEncoded
    @POST("perusahaan/lowonganPunyaPerusahaan")
    Call<ResponseModelLowongan> lowonganPunyaPerusahaan(@Field("perusahaanId") String perusahaanId,
                                                        @Field("status") String status);

    @FormUrlEncoded
    @POST("perusahaan/simpleDetail")
    Call<ResponseModelPerusahaan> simpleDetail(@Field("email") String email);


    @FormUrlEncoded
    @POST("daftarPerusahaan")
    Call<ResponseModel> daftarPerusahaan(@Field("email") String email,
                                      @Field("nama") String nama,
                                      @Field("password") String password,
                                      @Field("telepon") String telepon);

    @FormUrlEncoded
    @POST("daftarPekerja")
    Call<ResponseModel> daftarPekerja(@Field("email") String email,
                                    @Field("namadepan") String namadepan,
                                    @Field("namabelakang") String namabelakang,
                                    @Field("password") String password,
                                    @Field("jk") String jk,
                                      @Field("telepon") String telepon);

    @FormUrlEncoded
    @POST("masuk")
    Call<ResponseModel> masuk(@Field("email") String email,
                                      @Field("password") String password,
                              @Field("token") String token);


    //Admin
    @FormUrlEncoded
    @POST("admin/getHalamanUtama")
    Call<ResponseModelAdmin> getHalamanUtama(@Field("id") String id);

    @FormUrlEncoded
    @POST("admin/getKeahlian")
    Call<ResponseModelAdmin> getKeahlian(@Field("id") String id);

    @FormUrlEncoded
    @POST("admin/insertKeahlian")
    Call<ResponseModelAdmin> insertKeahlian(@Field("id") String id,
                                            @Field("nama") String nama);

}
