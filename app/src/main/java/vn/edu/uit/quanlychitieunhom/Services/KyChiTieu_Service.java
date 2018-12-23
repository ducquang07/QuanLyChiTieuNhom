package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;

public interface KyChiTieu_Service {

//    @GET("/api/kychitieu/list")
//    Call<List<kychitieu>> getAllKyChiTieu();

//    @GET("/api/kychitieu/getAll/{tentaikhoan}")
//    Call<List<kychitieu>> getAllKyChiTieu(@Path("tentaikhoan") String tentaikhoan);

    @GET("/api/kychitieu/getAllKyByNhom/{manhomchitieu}")
    Call<List<kychitieu>> getAllKyChiTieu(@Path("manhomchitieu") int manhomchitieu);

    @POST("/api/kychitieu/")
    Call<Void> insert(@Body kychitieu body);

}