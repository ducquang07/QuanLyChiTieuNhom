package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;
import vn.edu.uit.quanlychitieunhom.Views.ChiTietHoaDon;
import vn.edu.uit.quanlychitieunhom.Views.ThongTinNhom;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.activity_fragment__nhom_chi_tieu__item,null);
        TextView tvTenNhom = (TextView) v.findViewById(R.id.txtTenNhom);
        final TextView tvSLThanhVien = (TextView) v.findViewById(R.id.txtSLThanhVien);

        tvTenNhom.setText(list_NhomChiTieu.get(position).getTennhomchitieu());
        final LinearLayout ln = (LinearLayout) v.findViewById(R.id.ln_nhom_item);

                try {
                    NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                    Call<List<taikhoan>> call = service.getAllThanhVien(list_NhomChiTieu.get(position).getManhomchitieu());
                    call.enqueue(new Callback<List<taikhoan>>() {
                        @Override
                        public void onResponse(Call<List<taikhoan>> call, final Response<List<taikhoan>> response) {
                            if(response.code()== 200){
                                response.body();
                                tvSLThanhVien.setText(response.body().size()+" thành viên");
                                ln.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    Gson gson = new Gson();
                                    String list_thanhvien = gson.toJson(response.body());
                                    bundle.putString("list_thanhvien",list_thanhvien);
                                    Intent i = new Intent(v.getContext(),ThongTinNhom.class);
                                    i.putExtras(bundle);
                                    mContext.startActivity(i);
                                    }
                                });
                            }
                        }
                        @Override
                        public void onFailure(Call<List<taikhoan>> call, Throwable t) {
                            Toast.makeText(mContext,"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.d("Test", "Exception");
                }
                return v;
        }

}
