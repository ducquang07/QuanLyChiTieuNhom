package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.Models.loaigiaodich;

public interface LoaiGiaoDich_Service {
    @GET("api/loaigiaodich/list")
    Call<List<loaigiaodich>> getAllKyChiTieu();

}
