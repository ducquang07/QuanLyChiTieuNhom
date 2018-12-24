package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;

public interface KyChiTieu_Service {

    @GET("/api/kychitieu/getAllKyByNhom/{manhomchitieu}")
    Call<List<kychitieu>> getAllKyChiTieu(@Path("manhomchitieu") int manhomchitieu);

    @POST("/api/kychitieu/")
    Call<Void> insert(@Body kychitieu body);

    @PUT("/api/kychitieu/")
    Call<Void> update(@Body kychitieu body);

    @DELETE("/api/kychitieu/{makychitieu}")
    Call<Void> delete(@Path("makychitieu") int makychitieu);
}