package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Fragments.Fragment_HienThiGiaoDich;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Services.KyChiTieu_Service;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitle = new String[]{"Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng trước","Tháng này","Tương lai"};
    Context context;
    private int pagecount = 8;
    private List<kychitieu> List_KyChiTieu;

    public SimpleFragmentPagerAdapter(FragmentManager fm, final List<kychitieu> context) {
        super(fm);
//        this.context=context;
        this.List_KyChiTieu = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment_HienThiGiaoDich fragmentDemo = new Fragment_HienThiGiaoDich();
        return fragmentDemo;
    }

    @Override
    public int getCount() {
        return List_KyChiTieu.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return List_KyChiTieu.get(position).getTenkychitieu();
    }
}
