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
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.List_NgayGiaoDich_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.model.giaodich;



public class Fragment_HienThiGiaoDich extends Fragment {

    private ListView lvTransaction;
    private List_NgayGiaoDich_Adapter list_ngaygiaoDich_adapter;
    private List<giaodich> List_GiaoDich;
    private List<String> label_date;


    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hien_thi_giao_dich,container,false);
        /* Get list view component from view */
        lvTransaction = (ListView) view.findViewById(R.id.lv_transaction);

        List_GiaoDich = new ArrayList<>();

        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));
        List_GiaoDich.add(new giaodich(001,new Date(), (double) 2000,"Note something","no image",1,"Thai Nguyen",1));

        /*Pass param for list_adapter*/
        list_ngaygiaoDich_adapter = new List_NgayGiaoDich_Adapter(getContext(),List_GiaoDich);

        /*Set adapter for listview*/
        lvTransaction.setAdapter(list_ngaygiaoDich_adapter);

//        getGiaoDich();

        return view;
    }










    /*Get list giao dich by request API*/
    public void getGiaoDich(){
        try {
            GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
            Call<List<giaodich>> call = service.getAllTransaction();
            call.enqueue(new Callback<List<giaodich>>() {
                @Override
                public void onResponse(Call<List<giaodich>> call, Response<List<giaodich>> response) {
                    List_GiaoDich = response.body();

//                    for (giaodich item: List_GiaoDich) {
//                        String temp = dateFormat.format(item.getNgaygiaodich());
//                        if(!label_date.contains(temp)){
//                            label_date.add(dateFormat.format(temp));
//                            list_giaodichpage.add( new GiaoDichPage(temp,item));
//                        }
//                    }

                    /*TO DO*/
                    GeneratedAdapter();

                }

                @Override
                public void onFailure(Call<List<giaodich>> call, Throwable t) {
//                    progressDoalog.dismiss();
                    Toast.makeText(getContext(),"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Test", "Exception");
        }
    }

    /*Create List Giao Dich adapter to set up listview */
    public void GeneratedAdapter(){
        /*Pass param for list_adapter*/
        list_ngaygiaoDich_adapter = new List_NgayGiaoDich_Adapter(getContext(),List_GiaoDich);

        /*Set adapter for listview*/
        lvTransaction.setAdapter(list_ngaygiaoDich_adapter);
    }
}
