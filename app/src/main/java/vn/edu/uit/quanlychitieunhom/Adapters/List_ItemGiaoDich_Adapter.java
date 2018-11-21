package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Utils.Util;
import vn.edu.uit.quanlychitieunhom.Views.ChiTietHoaDon;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;

public class List_ItemGiaoDich_Adapter extends BaseAdapter {

    private Context mContext;
    private List<giaodich> list_itemGiaodich;
    Util util = new Util();

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
    public View getView(int position, View convertView, final ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.fragment_giao_dich_item,null);
        TextView tvTransactionName = (TextView) v.findViewById(R.id.tvTransactionName);
        TextView tvCharge = (TextView) v.findViewById(R.id.tvCharge);

        tvTransactionName.setText(list_itemGiaodich.get(position).getGhichu());
//        tvCharge.setText(numberFormat.format(list_itemGiaodich.get(position).getSotien()));
        tvCharge.setText(util.DoubleToStringByFormat(list_itemGiaodich.get(position).getSotien(),"#,###,###"));
        LinearLayout ln_item = (LinearLayout) v.findViewById(R.id.ln_item);
        ln_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ChiTietHoaDon.class);
                mContext.startActivity(i);
            }
        });
        return v;
    }
}
