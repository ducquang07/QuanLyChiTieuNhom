package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.RecyclerView_ThanhVienNhom_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class ThongTinNhom extends AppCompatActivity {
    Util util = new Util();
    private nhomchitieu Nhom = new nhomchitieu();
    private List<taikhoan> taikhoanArrayList = new ArrayList<>();
    private TextView tennhom;
    private TextView quynhom;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nhom);
        GetComponentByID();
        initView();
    }

    public  void initView()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv_listthanhvien);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Intent i = getIntent();
        Gson gson = new Gson();


        taikhoanArrayList = Arrays.asList(gson.fromJson(i.getStringExtra("list_thanhvien"), taikhoan[].class));
        Nhom = gson.fromJson(i.getStringExtra("nhomchitieu"),nhomchitieu.class);
        tennhom.setText(Nhom.getTennhomchitieu());
        quynhom.setText(String.valueOf(Nhom.getQuy()));

        RecyclerView_ThanhVienNhom_Adapter listThanhVienNhomAdapter = new RecyclerView_ThanhVienNhom_Adapter(Nhom.getManhomchitieu(),taikhoanArrayList,getApplicationContext());
        recyclerView.setAdapter(listThanhVienNhomAdapter);
    }


    public void GetComponentByID(){
        tennhom = (TextView) findViewById(R.id.txtTenNhom);
        quynhom = (TextView) findViewById(R.id.txtQuyNhom);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit:
//                EditMode(true);
                return true;
            case R.id.remove:
                new deleteNhomChiTieuTask().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //TODO: insert new Giao dich
    public class  deleteNhomChiTieuTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
//            showProgress(true);
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            // TODO: register the new account here.
            try {
                NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                Call<Void> call = service.delete(Nhom.getManhomchitieu());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
//                            showProgress(false);
//                            clearInput();
                            Toast.makeText(getApplicationContext(), "Đã xóa !", Toast.LENGTH_SHORT).show();
                            util.setFlagEditNhom(getApplicationContext(),true);
                            finish();
                        }
                        else{
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Log.d("ERROR",jObjError.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("test",String.valueOf(response.code()));
//                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
//                        showProgress(false);
                        Log.d("ERR",t.getMessage());
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }
            return true;
        }

    }
}
