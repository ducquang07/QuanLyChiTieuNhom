package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class ChiTietHoaDon extends AppCompatActivity {
    Util util = new Util();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        Intent i= getIntent();


        Date ngay = (Date) i.getSerializableExtra("ngaygiaodich");

        TextView create_by = (TextView) findViewById(R.id.tvTenNguoiNhap);
        TextView sotien = (TextView) findViewById(R.id.tvTongTien);
        TextView ngaygiaodich = (TextView) findViewById(R.id.tvNgayGiaoDich);

        create_by.setText(i.getStringExtra("create_by"));
        sotien.setText(Double.toString(i.getDoubleExtra("sotien",0)));
        ngaygiaodich.setText(util.DateStringByFormat(ngay,"dd/MM/yyyy"));
    }
}
