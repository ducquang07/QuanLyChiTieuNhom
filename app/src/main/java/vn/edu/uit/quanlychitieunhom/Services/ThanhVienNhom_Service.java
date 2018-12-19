package vn.edu.uit.quanlychitieunhom.Services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ThanhVienNhom_Service {

    @POST("/api/thanhviennhom/insert")
    @FormUrlEncoded
    Call<Void> insert(@Field("manhomchitieu") Integer manhomchitieu, @Field("tentaikhoan") String tentaikhoan);
}
