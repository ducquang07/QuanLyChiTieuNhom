package vn.edu.uit.quanlychitieunhom.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;

public class RecyclerView_ThanhVienNhom_Adapter extends RecyclerView.Adapter<RecyclerView_ThanhVienNhom_Adapter.ViewHolder>{
    Util util = new Util();
    private List<taikhoan> taikhoanlist;
    private Context context;
    private int manhomchitieu;
    private List<taikhoan> deleted_taikhoan;


    public RecyclerView_ThanhVienNhom_Adapter(int manhomchitieu, List<taikhoan> taikhoanlist, Context context) {
        this.taikhoanlist = taikhoanlist;
        this.context = context;
        this.manhomchitieu = manhomchitieu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_thanhvien,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            viewHolder.txtTen.setText(taikhoanlist.get(i).getTennguoidung());
            //viewHolder.imgAvatar.setImageResource(taikhoanlist.get(i));
            viewHolder.txtTenTaiKhoan.setText(taikhoanlist.get(i).getTentaikhoan());
            viewHolder.txtEmail.setText(taikhoanlist.get(i).getEmail());
            viewHolder.txtSodienthoai.setText(taikhoanlist.get(i).getSodienthoai());

            viewHolder.btnXoaThanhVien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Xóa thành viên")
                            .setMessage("Bạn có chắc muốn xóa thành viên này khỏi nhóm ?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        showProgress(viewHolder,true);
                                        Thread.sleep(2000);
                                        NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                                        Call<Void> call = service.deleteThanhVien(manhomchitieu,taikhoanlist.get(i).getTentaikhoan());
                                        call.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if(response.code() == 200){
                                                    showProgress(viewHolder,false);
                                                    Toast.makeText(context, "Đã xóa !", Toast.LENGTH_SHORT).show();
                                                    viewHolder.container.setVisibility(View.GONE);
                                                    util.setFlagEditNhom(context,true);
                                                }
                                                else{
                                                    try {
                                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                                        Log.d("ERROR",jObjError.toString());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.d("test",String.valueOf(response.code()));
                                                    showProgress(viewHolder,false);
                                                    Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                showProgress(viewHolder,false);
                                                Log.d("ERR",t.getMessage());
                                                Toast.makeText(context, "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (Exception e) {
                                        Log.d("ERROR", e.toString());
                                    }
                                    finally {
//                                        util.setFlagNewGiaoDich(getApplicationContext(),true, current_giaodich.getKychitieu().getMakychitieu());
                                    }
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return taikhoanlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAvatar;
        TextView txtTen;
        TextView txtTenTaiKhoan;
        TextView txtEmail;
        TextView txtSodienthoai;
        ImageButton btnXoaThanhVien;
        ProgressBar progressBar;
        RelativeLayout container;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           txtTen = (TextView)itemView.findViewById(R.id.txtTenThanhvien);
           imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
           txtTenTaiKhoan = (TextView) itemView.findViewById(R.id.txtTenTaiKhoan);
           txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
           txtSodienthoai = (TextView) itemView.findViewById(R.id.txtSodienthoai);
           btnXoaThanhVien =(ImageButton) itemView.findViewById(R.id.btnXoaThanhVien);
           progressBar = (ProgressBar) itemView.findViewById(R.id.proBarThanhVienNhom);
           progressBar.setVisibility(View.GONE);
           container = (RelativeLayout) itemView.findViewById(R.id.rlContainer);
       }
   }


    public void showProgress(ViewHolder viewHolder, boolean BOOL){
        if(BOOL){
            viewHolder.progressBar.setVisibility(GONE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.progressBar.setVisibility(GONE);
        }
    }


}
