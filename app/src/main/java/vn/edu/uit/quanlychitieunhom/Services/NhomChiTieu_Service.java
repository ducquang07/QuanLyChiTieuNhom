package vn.edu.uit.quanlychitieunhom.Services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;

public interface NhomChiTieu_Service {

    @POST("/api/nhomchitieu/insert")
    Call<nhomchitieu> insert(@Body nhomchitieu body);
}
