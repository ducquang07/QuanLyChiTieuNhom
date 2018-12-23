package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;

public interface NhomChiTieu_Service {

    @POST("/api/nhomchitieu/insert")
    Call<nhomchitieu> insert(@Body nhomchitieu body);

    @GET("/api/nhomchitieu/getallnhomchitieuoftaikhoan/{tentaikhoan}")
    Call<List<nhomchitieu>> getAllNhomChiTieu(@Path("tentaikhoan") String tentaikhoan);

    @GET("/api/thanhviennhom/getthanhvien/{manhomchitieu}")
    Call<List<taikhoan>> getAllThanhVien(@Path("manhomchitieu") int manhomchitieu);

    @DELETE("/api/thanhviennhom/delete/{manhomchitieu}/{tentaikhoan}")
    Call<Void> deleteThanhVien(@Path("manhomchitieu") int manhomchitieu,
                               @Path("tentaikhoan") String tentaikhoan);

    @DELETE("/api/nhomchitieu/delete/{manhomchitieu}")
    Call<Void> delete(@Path("manhomchitieu") int manhomchitieu);
}
