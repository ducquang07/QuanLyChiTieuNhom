package vn.edu.uit.quanlychitieunhom.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.List_NgayGiaoDich_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.list_giaodich;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Utils.Util;


@SuppressLint("ValidFragment")
public class Fragment_HienThiGiaoDich extends Fragment {
    ProgressBar progressBar;
    private int MaKyChiTieu;
    private ListView lvTransaction;
    private List_NgayGiaoDich_Adapter list_ngaygiaoDich_adapter;
    private List<giaodich> List_GiaoDich;
    private List<list_giaodich> List_GiaoDichTheoNgay;
    Util util = new Util();

    public Fragment_HienThiGiaoDich(int MaKyChiTieu) {
        this.MaKyChiTieu = MaKyChiTieu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hien_thi_giao_dich,container,false);
        /* Get list view component from view */
        progressBar = (ProgressBar) view.findViewById(R.id.proBarManHinhChinh);
        progressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        lvTransaction = (ListView) view.findViewById(R.id.lv_transaction);
        List_GiaoDich = new ArrayList<>();
        getGiaoDich();
        return view;
    }

    /*Get list giao dich by request API*/
    public void getGiaoDich(){
        try {
            GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
            Call<List<giaodich>> call = service.getGiaoDichOfKyChiTieu(this.MaKyChiTieu);
            call.enqueue(new Callback<List<giaodich>>() {
                @Override
                public void onResponse(Call<List<giaodich>> call, Response<List<giaodich>> response) {
                    List_GiaoDich = response.body();
//                    Log.d("test", String.valueOf(response.body()));
                    List_GiaoDichTheoNgay = util.deployKyChiTieu(List_GiaoDich);
                    GeneratedAdapter();
                }
                @Override
                public void onFailure(Call<List<giaodich>> call, Throwable t) {
                    Toast.makeText(getContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Test", "Exception");
        }
    }

    /*Create List Giao Dich adapter to set up listview */
    public void GeneratedAdapter(){
        /*Pass param for list_adapter*/
        list_ngaygiaoDich_adapter = new List_NgayGiaoDich_Adapter(getContext(),List_GiaoDichTheoNgay);

        /*Set adapter for listview*/
        lvTransaction.setAdapter(list_ngaygiaoDich_adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
