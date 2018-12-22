package vn.edu.uit.quanlychitieunhom.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;

public class RecyclerView_ThanhVienNhom_Adapter extends RecyclerView.Adapter<RecyclerView_ThanhVienNhom_Adapter.ViewHolder>{

    ArrayList<taikhoan> taikhoanlist;
    Context context;

    public RecyclerView_ThanhVienNhom_Adapter(ArrayList<taikhoan> taikhoanlist, Context context) {
        this.taikhoanlist = taikhoanlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_thanhvien,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.txtTen.setText(taikhoanlist.get(i).getTennguoidung());
            //viewHolder.imgAvatar.setImageResource(taikhoanlist.get(i));
    }

    @Override
    public int getItemCount() {
        return taikhoanlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAvatar;
        TextView txtTen;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           txtTen = (TextView)itemView.findViewById(R.id.txtTenThanhvien);
           imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
       }
   }
}
