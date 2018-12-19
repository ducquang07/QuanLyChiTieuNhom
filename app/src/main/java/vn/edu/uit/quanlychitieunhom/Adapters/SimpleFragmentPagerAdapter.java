package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Fragments.Fragment_HienThiGiaoDich;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Services.KyChiTieu_Service;

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<kychitieu> List_KyChiTieu;
    private FragmentManager fm;
    private Map<Integer, String> mFragmentTags;



    public SimpleFragmentPagerAdapter(FragmentManager fm, List<kychitieu> context) {
        super(fm);
        this.List_KyChiTieu = context;
        this.fm = fm;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment_HienThiGiaoDich fragment_hienThiGiaoDich = new Fragment_HienThiGiaoDich();
        fragment_hienThiGiaoDich.setMaKiChiTieu(List_KyChiTieu.get(position).getMakychitieu());
        fragment_hienThiGiaoDich.setMaNhomChiTieu(List_KyChiTieu.get(position).getNhomchitieu().getManhomchitieu());
        return fragment_hienThiGiaoDich;
    }

    @Override
    public int getCount() {
//        if(List_KyChiTieu == null || List_KyChiTieu.isEmpty()){
//            return 0;
//        }
        return List_KyChiTieu.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return List_KyChiTieu.get(position).getTenkychitieu();
    }

    public void setData(List<kychitieu> data) {
        this.List_KyChiTieu = data;
    }

}
