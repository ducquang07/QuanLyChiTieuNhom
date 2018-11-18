package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.edu.uit.quanlychitieunhom.Fragments.Fragment_HienThiGiaoDich;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitle = new String[]{"Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng trước","Tháng này","Tương lai","Tháng 1","Tháng 2","Tháng 3","Tháng 4"};
    Context context;
    private int pagecount = 12;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment_HienThiGiaoDich fragmentDemo = new Fragment_HienThiGiaoDich();
        return fragmentDemo;
    }

    @Override
    public int getCount() {
        return pagecount;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
