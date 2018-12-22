package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.List_NhomChiTieu_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class HienThiNhomChiTieu extends AppCompatActivity {

    Util util = new Util();
    private FloatingActionButton fabThemNhom;
    private ListView listViewNhom;
    private taikhoan user_admin = new taikhoan();
    private List<nhomchitieu> List_NhomChiTieu = new ArrayList<>();
    int StatusCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_nhom_chi_tieu);
        ReferenceById();
        addControl();
        user_admin = util.getUserLocalStorage(getApplicationContext());
        new getNhomChiTieuTask().execute();

    }

    private void ReferenceById(){
        listViewNhom = findViewById(R.id.listViewNhomChiTieu);
        fabThemNhom = findViewById(R.id.fabThemNhom);
    }

    private void addControl() {
        fabThemNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThemNhomChiTieu.class);
                startActivity(i);
            }
        });
    }

    public class  getNhomChiTieuTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                Call<List<nhomchitieu>> call = service.getAllNhomChiTieu(user_admin.getTentaikhoan());
                call.enqueue(new Callback<List<nhomchitieu>>() {
                    @Override
                    public void onResponse(Call<List<nhomchitieu>> call, Response<List<nhomchitieu>> response) {
                        StatusCode = response.code();
                        if(StatusCode== 200){
                            List_NhomChiTieu_Adapter list_nhomChiTieu_adapter = new List_NhomChiTieu_Adapter(getApplicationContext(),response.body());
                            listViewNhom.setAdapter(list_nhomChiTieu_adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<List<nhomchitieu>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("Test", "Exception");
            }
            // TODO: register the new account here.
            return (StatusCode == 200)? true : false;
        }
    }



}
