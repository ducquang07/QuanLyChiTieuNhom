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
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;

public interface GiaoDich_Service {

//    @GET("/api/giaodich/list")
//    Call<List<giaodich>> getAllTransaction();

    @GET("/api/giaodich/kychitieu/{makychitieu}/{manhomchitieu}")
    Call<List<giaodich>> getGiaoDichOfKyChiTieu(@Path("makychitieu") int makychitieu,
                                                @Path("manhomchitieu") int manhomchitieu);

    @POST("/api/giaodich/")
    Call<Void> insert(@Body giaodich body);

    @PUT("/api/giaodich/")
    Call<Void> update(@Body giaodich body);

    @DELETE("/api/giaodich/{magiaodich}")
    Call<Void> delete(@Path("magiaodich") int magiaodich);

    @GET("/api/giaodich/thongkegiaodich/{ngaybatdau}/{ngayketthuc}/{tentaikhoan}")
    Call<List<giaodich>> getThongKe(@Path( "ngaybatdau" ) String ngaybatdau,
                                    @Path("ngayketthuc") String ngayketthuc,
                                    @Path("tentaikhoan") String tentaikhoan);
}


