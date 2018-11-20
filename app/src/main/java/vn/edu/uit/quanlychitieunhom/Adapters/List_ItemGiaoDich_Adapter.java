package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.Fragments.Fragment_GiaoDich_Item;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.model.giaodich;

public class List_ItemGiaoDich_Adapter extends BaseAdapter {

    private Context mContext;
    private List<giaodich> list_itemGiaodich;
    NumberFormat numberFormat = new DecimalFormat("#,###,###");


    public List_ItemGiaoDich_Adapter(Context mContext, List<giaodich> list_itemGiaodich) {
        this.mContext = mContext;
        this.list_itemGiaodich = list_itemGiaodich;
    }

    @Override
    public int getCount() {
        return list_itemGiaodich.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemGiaodich.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list_itemGiaodich.get(position).getMagiaodich();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.fragment_giao_dich_item,null);
        TextView tvTransactionName = (TextView) v.findViewById(R.id.tvTransactionName);
        TextView tvCharge = (TextView) v.findViewById(R.id.tvCharge);

        tvTransactionName.setText(list_itemGiaodich.get(position).getGhichu());
        tvCharge.setText(numberFormat.format(list_itemGiaodich.get(position).getSotien()));
        return v;
    }
}
