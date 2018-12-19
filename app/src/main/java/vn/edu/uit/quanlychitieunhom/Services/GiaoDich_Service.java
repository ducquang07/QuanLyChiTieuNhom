package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;

public interface GiaoDich_Service {

//    @GET("/api/giaodich/list")
//    Call<List<giaodich>> getAllTransaction();

    @GET("/api/giaodich/kychitieu/{makychitieu}/{manhomchitieu}")
    Call<List<giaodich>> getGiaoDichOfKyChiTieu(@Path("makychitieu") int makychitieu,
                                                @Path("manhomchitieu") int manhomchitieu);

    @POST("/api/giaodich/")
    Call<Void> insert(@Body giaodich body);
}


