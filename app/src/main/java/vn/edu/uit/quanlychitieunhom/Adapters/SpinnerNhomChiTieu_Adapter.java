package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.loaigiaodich;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class SpinnerNhomChiTieu_Adapter extends ArrayAdapter<nhomchitieu> {

    Util util = new Util();
    int StatusCode;
    private Context context;
    private List<nhomchitieu> values = new ArrayList<>();
    private taikhoan user_admin;

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



    public class  getNhomChiTieuTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            user_admin = util.getUserLocalStorage(getContext());
        }
        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                Call<List<nhomchitieu>> call = service.getAllNhomChiTieu(user_admin.getTentaikhoan());
                call.enqueue(new Callback<List<nhomchitieu>>() {
                    @Override
                    public void onResponse(Call<List<nhomchitieu>> call, Response<List<nhomchitieu>> response) {
                        StatusCode = response.code();
                        if(StatusCode== 200){
                            values = response.body();
//                            setSpinnerAdapter();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<nhomchitieu>> call, Throwable t) {
                        Toast.makeText(getContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("Test", "Exception");
            }
            // TODO: register the new account here.
            return (StatusCode == 200)? true : false;
        }
    }

    public int getPositionById(int manhomchitieu){
        for (int i = 0; i< values.size(); i++) {
            if(values.get(i).getManhomchitieu() == manhomchitieu){
                return i;
            }
        }
        return 0;
    }
}

