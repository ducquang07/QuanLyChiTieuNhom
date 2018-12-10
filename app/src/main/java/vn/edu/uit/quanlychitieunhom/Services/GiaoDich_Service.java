package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;

public interface GiaoDich_Service {

    @GET("/api/giaodich/list")
    Call<List<giaodich>> getAllTransaction();

    @GET("/api/giaodich/kychitieu/{makychitieu}")
    Call<List<giaodich>> getGiaoDichOfKyChiTieu(@Path("makychitieu") int makychitieu);
}


