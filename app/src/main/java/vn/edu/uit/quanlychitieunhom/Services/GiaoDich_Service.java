package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;

public interface GiaoDich_Service {

    @GET("/api/giaodich/list")
    Call<List<giaodich>> getAllTransaction();

}


