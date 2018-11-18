package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.model.giaodich;

public class List_GiaoDich_Adapter extends BaseAdapter {

    private Context mContext;
    private List<giaodich> mTransactionList;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM yyyy");
    NumberFormat  numberFormat = new DecimalFormat("#,###,###");



    public List_GiaoDich_Adapter(Context mContext, List<giaodich> mTransactionList) {
        this.mContext = mContext;
        this.mTransactionList = mTransactionList;
    }

    @Override
    public int getCount() {
        return mTransactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.fragment_giao_dich_item,null);
        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        TextView tvTransactionName = (TextView) v.findViewById(R.id.tvTransactionName);
        TextView tvCharge = (TextView) v.findViewById(R.id.tvCharge);
        TextView tvDay = (TextView) v.findViewById(R.id.tvDay);

        tvDay.setText(
                mTransactionList.get(position).getNgaygiaodich().getDay()>=10?
                Integer.toString(mTransactionList.get(position).getNgaygiaodich().getDay()):
                "0"+Integer.toString(mTransactionList.get(position).getNgaygiaodich().getDay())
        );
        tvDate.setText("Tháng " + dateFormat.format(mTransactionList.get(position).getNgaygiaodich()));
        tvTransactionName.setText(mTransactionList.get(position).getGhichu());
        tvCharge.setText(numberFormat.format(mTransactionList.get(position).getSotien()));
        v.setTag(mTransactionList.get(position).getMagiaodich());

        return v;
    }
}
