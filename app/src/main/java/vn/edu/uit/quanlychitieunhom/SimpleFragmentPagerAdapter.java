package vn.edu.uit.quanlychitieunhom;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        FragmentHienThiGiaoDich fragmentDemo = new FragmentHienThiGiaoDich();
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
