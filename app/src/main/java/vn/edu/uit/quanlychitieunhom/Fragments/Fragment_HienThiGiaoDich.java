package vn.edu.uit.quanlychitieunhom.Fragments;

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


import com.google.gson.Gson;

import java.util.ArrayList;
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


public class Fragment_HienThiGiaoDich extends Fragment {
    ProgressBar progressBar;
    protected int MaKyChiTieu;
    protected  int MaNhomChiTieu;
    private ListView lvTransaction;
    private List_NgayGiaoDich_Adapter list_ngaygiaoDich_adapter;
    private List<giaodich> List_GiaoDich;
    private List<list_giaodich> List_GiaoDichTheoNgay;
    Util util = new Util();
    public int position;


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

    public void setMaKiChiTieu(int makichitieu){
        this.MaKyChiTieu = makichitieu;
    }
    public void setMaNhomChiTieu(int manhomchitieu){this.MaNhomChiTieu = manhomchitieu;}
    /*Get list giao dich by request API*/
    public void getGiaoDich(){
        try {
            GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
            Call<List<giaodich>> call = service.getGiaoDichOfKyChiTieu(this.MaKyChiTieu,this.MaNhomChiTieu);
            call.enqueue(new Callback<List<giaodich>>() {
                @Override
                public void onResponse(Call<List<giaodich>> call, Response<List<giaodich>> response) {
                    Gson gson = new Gson();
                    List_GiaoDich = response.body();
//                    Log.d("TEST_MAGIAODICH",String.valueOf(MaKyChiTieu)+"/"+String.valueOf(MaNhomChiTieu));
//                    Log.d("TEST_GIAODICH",gson.toJson(response.body()));
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
        finally {
            util.setFlagNewGiaoDich(getContext(),false,0);
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


    @Override
    public void onResume() {
        Util.FlagNewGiaoDich flag = util.getFlagNewGiaoDich(getContext());
        if(flag != null){
            if(flag.getMakychitieu() == MaKyChiTieu ){
                if(flag.isFlag()){
                    getGiaoDich();
                }
            }
        }

        super.onResume();
    }


}
