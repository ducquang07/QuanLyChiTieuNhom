package vn.edu.uit.quanlychitieunhom.Services;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;

public interface NhomChiTieu_Service {
    @GET("/api/nhomchitieu/getallnhomchitieuoftaikhoan/{tentaikhoan}")
    Call<List<nhomchitieu>> getAllNhomChiTieu(@Path("tentaikhoan") String tentaikhoan);
}
