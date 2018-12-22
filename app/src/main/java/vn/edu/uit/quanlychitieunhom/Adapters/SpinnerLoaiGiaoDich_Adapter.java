package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.Models.loaigiaodich;

public class SpinnerLoaiGiaoDich_Adapter extends ArrayAdapter<loaigiaodich> {
    private Context context;
    private List<loaigiaodich> values = new ArrayList<>();

    public SpinnerLoaiGiaoDich_Adapter(Context context, int textViewResourceId,List<loaigiaodich> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public loaigiaodich getItem(int position) {
        return values.get(position);
    }


    @Override
    public long getItemId(int position) {
        return values.get(position).getMaloaigiaodich();
    }


    public int getPositionById(int Maloaigiaodich){
        for (int i = 0; i< values.size(); i++) {
            if(values.get(i).getMaloaigiaodich() == Maloaigiaodich){
                return i;
            }
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getTenloaigiaodich());
        return label;
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getTenloaigiaodich());
        return label;
    }
}
