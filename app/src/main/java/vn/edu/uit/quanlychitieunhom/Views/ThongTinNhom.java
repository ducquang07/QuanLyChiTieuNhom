package vn.edu.uit.quanlychitieunhom.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.uit.quanlychitieunhom.Adapters.RecyclerView_ThanhVienNhom_Adapter;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;

public class ThongTinNhom extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nhom);
        initView();
    }

    public  void initView()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv_listthanhvien);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //Chỗ này truyền vào danh sách thành viên nhóm
        ArrayList<taikhoan> taikhoanArrayList = new ArrayList<>();
        taikhoanArrayList.add(new taikhoan("ngocdiem","Trần Thị Ngọc Diễm","khong co"));
        taikhoanArrayList.add(new taikhoan("hoaithanh","Đặng Võ Hoài Thanh", "khong co"));
        taikhoanArrayList.add(new taikhoan("ducquang","Phan Đức Quang", "khong co"));
        taikhoanArrayList.add(new taikhoan("thanhthai","Nguyễn Thành Thái", "khong co"));
        //
        RecyclerView_ThanhVienNhom_Adapter listThanhVienNhomAdapter = new RecyclerView_ThanhVienNhom_Adapter(taikhoanArrayList,getApplicationContext());
        recyclerView.setAdapter(listThanhVienNhomAdapter);
    }
}
