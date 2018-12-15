package vn.edu.uit.quanlychitieunhom.Services;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;

public interface TaiKhoan_Service {

    @POST("/api/taikhoan/checklogin")
    @FormUrlEncoded
    Call<taikhoan> login(@Field("tentaikhoan") String tentaikhoan,
                         @Field("matkhau") String matkhau);

    @POST("/api/taikhoan/insert")
    @FormUrlEncoded
    Call<Void> signup(@Field("email") String email,
                      @Field("tennguoidung") String tennguoidung,
                      @Field("tentaikhoan") String tentaikhoan,
                      @Field("matkhau") String matkhau);

//    @Headers("Content-Type: application/json")
    @PUT("/api/taikhoan/update/{tentaikhoan}")
    Call<taikhoan> update(@Path("tentaikhoan") String tentaikhoan,
                              @Body taikhoan body);
}