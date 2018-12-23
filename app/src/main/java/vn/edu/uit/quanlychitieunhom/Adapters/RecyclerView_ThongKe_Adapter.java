package vn.edu.uit.quanlychitieunhom.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.list_thongke;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.R;

public class RecyclerView_ThongKe_Adapter extends RecyclerView.Adapter<RecyclerView_ThongKe_Adapter.ViewHolder> {

    List<nhomchitieu> list_nhom;
    List<giaodich> list_giaodich;
    Context context;

    public RecyclerView_ThongKe_Adapter(List<nhomchitieu> list_nhom, List<giaodich> list_giaodich, Context context) {
        this.list_nhom = list_nhom;
        this.list_giaodich = list_giaodich;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_thong_ke,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.txtTennhom.setText( list_nhom.get(i).getTennhomchitieu());
            viewHolder.txtTongchi.setText(String.valueOf( getTongTien( list_nhom.get( i ),list_giaodich ) ));
    }

    @Override
    public int getItemCount() {
        return list_nhom.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTennhom;
        TextView txtTongchi;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txtTennhom = (TextView) itemView.findViewById( R.id.txttennhom );
            txtTongchi = (TextView) itemView.findViewById( R.id.txtTongTienChi );
        }
    }


    public int getTongTien(nhomchitieu nhom,List<giaodich> list_giaodich){
        int tongtien = 0;
        for (giaodich item : list_giaodich){
            if(item.getNhomchitieu().getManhomchitieu() == nhom.getManhomchitieu() &&
                item.getLoaigiaodich().getNhom().equals("chi")){
                tongtien+= item.getSotien();
            }
        }
        return tongtien;
    }
}
