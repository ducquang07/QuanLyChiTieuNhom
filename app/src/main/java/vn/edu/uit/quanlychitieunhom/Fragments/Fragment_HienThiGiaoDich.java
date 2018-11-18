package vn.edu.uit.quanlychitieunhom.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.Adapters.List_GiaoDich_Adapter;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.model.giaodich;

public class Fragment_HienThiGiaoDich extends Fragment {

    private ListView lvTransaction;
    private List_GiaoDich_Adapter list_giaoDich_adapter;
    private List<giaodich> List_GiaoDich;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hien_thi_giao_dich,container,false);
        /* Get list view component from view */
        lvTransaction = (ListView) view.findViewById(R.id.lv_transaction);

        /* List view param for List_GiaoDich_Adapter*/
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
        list_giaoDich_adapter = new List_GiaoDich_Adapter(getContext(),List_GiaoDich);

        /*Set adapter for listview*/
        lvTransaction.setAdapter(list_giaoDich_adapter);

        return view;
    }
}
