package vn.edu.uit.quanlychitieunhom.Services;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;

public interface Image_Service {

    @GET
    Call<ResponseBody> getImage(@Url String url);
}

