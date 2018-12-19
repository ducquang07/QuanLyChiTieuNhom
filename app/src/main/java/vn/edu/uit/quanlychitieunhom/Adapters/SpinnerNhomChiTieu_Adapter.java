package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.Models.loaigiaodich;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;

public class SpinnerNhomChiTieu_Adapter extends ArrayAdapter<nhomchitieu> {
    private Context context;
    private List<nhomchitieu> values = new ArrayList<>();
    public SpinnerNhomChiTieu_Adapter(Context context, int textViewResourceId,List<nhomchitieu> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public nhomchitieu getItem(int position) {
        return values.get(position);
    }


    @Override
    public long getItemId(int position) {
        return values.get(position).getManhomchitieu();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getTennhomchitieu());
        return label;
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getTennhomchitieu());
        return label;
    }
}

