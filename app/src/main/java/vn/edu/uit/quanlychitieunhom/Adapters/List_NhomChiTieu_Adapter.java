package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class List_NhomChiTieu_Adapter  extends BaseAdapter {

    private Context mContext;
    private List<nhomchitieu> list_NhomChiTieu;
    Util util = new Util();

    public List_NhomChiTieu_Adapter(Context mContext, List<nhomchitieu> list_NhomChiTieu) {
        this.mContext = mContext;
        this.list_NhomChiTieu = list_NhomChiTieu;
    }

    @Override
    public int getCount() {
        return list_NhomChiTieu.size();
    }

    @Override
    public nhomchitieu getItem(int position) {
        return list_NhomChiTieu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list_NhomChiTieu.get(position).getManhomchitieu();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.activity_fragment__nhom_chi_tieu__item,null);
        TextView tvTenNhom = (TextView) v.findViewById(R.id.txtTenNhom);
        TextView tvSLThanhVien = (TextView) v.findViewById(R.id.txtSLThanhVien);

        tvTenNhom.setText(list_NhomChiTieu.get(position).getTennhomchitieu());
        tvSLThanhVien.setText(String.valueOf(list_NhomChiTieu.get(position).getQuy()));
        return v;
    }
}
