package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;

public interface KyChiTieu_Service {

    @GET("/api/kychitieu/list")
    Call<List<kychitieu>> getAllKyChiTieu();

//    @GET("/api/nhomchitieu/getallnhomchitieuoftaikhoan/{tentaikhoan}")
//    Call<List<kychitieu>> getAllKyChiTieu(@Path("tentaikhoan") String tentaikhoan);
}