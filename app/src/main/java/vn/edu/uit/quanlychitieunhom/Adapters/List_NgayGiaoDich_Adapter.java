package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;

import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.Models.list_giaodich;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Utils.Util;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;


public class List_NgayGiaoDich_Adapter extends BaseAdapter {

    private Context mContext;
    private List<giaodich> mTransactionList;
    private ArrayList<giaodich> list_itemGiaodich;
    private List<list_giaodich>mDateTransactionList;

    private FragmentActivity myContext;

    Util util = new Util();



    public List_NgayGiaoDich_Adapter(Context mContext, List<list_giaodich> mDateTransactionList) {
        this.mContext = mContext;
        this.mDateTransactionList = mDateTransactionList;
    }

    @Override
    public int getCount() {
        return mDateTransactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDateTransactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View v = View.inflate(mContext,R.layout.fragment_giao_dich,null);

        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        TextView tvDay = (TextView) v.findViewById(R.id.tvDay);
        ListView lv_item = (ListView) v.findViewById(R.id.lv_item);
        TextView lv_Total = (TextView) v.findViewById(R.id.tvTotal);

        tvDay.setText(
                mDateTransactionList.get(position).getNgaygiaodich().getDate()>=10?
                Integer.toString(mDateTransactionList.get(position).getNgaygiaodich().getDate()):
                "0"+Integer.toString(mDateTransactionList.get(position).getNgaygiaodich().getDate())
        );

        tvDate.setText("Tháng " + util.DateStringByFormat(mDateTransactionList.get(position).getNgaygiaodich(),"MM yyyy"));

        lv_Total.setText(util.IntegerToStringByFormat(mDateTransactionList.get(position).TongTien(),"#,###")+"đ");

        list_itemGiaodich = new ArrayList<>();
        list_itemGiaodich = (ArrayList<giaodich>) mDateTransactionList.get(position).getDanhsachchitieu();

        /*Pass param for list_adapter*/
        List_ItemGiaoDich_Adapter list_itemGiaoDich_adapter= new List_ItemGiaoDich_Adapter(v.getContext(),list_itemGiaodich);

        /*Set adapter for listview*/
        lv_item.setAdapter(list_itemGiaoDich_adapter);


        LinearLayout container = (LinearLayout) v.findViewById(R.id.ln_container);
        ViewGroup.LayoutParams param = container.getLayoutParams();
        param.height= 145*list_itemGiaodich.size();
        container.setLayoutParams(param);
        return v;
    }


}
