package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import vn.edu.uit.quanlychitieunhom.R;

public class ThemNhomChiTieu extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    private Button btnThemthanhvien;
    private Button btnThemNhom;
    private Integer soThanhVien = 0;
    private String viewValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_nhom_chi_tieu);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        btnThemthanhvien = findViewById(R.id.btnThemThanhVien);
        btnThemNhom = findViewById(R.id.btnThemNhom);
        addControl();

    }

    private void addControl() {
        btnThemthanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soThanhVien < 3)//gioi han so thanh vien nhom
                {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.fragment_ten_thanh_vien, null);
                    // Add the new row before the add field button.
                    parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());//add rowView vao "parentLinearLayout" tai vi tri parentLinearLayout.getChildCount()
                    soThanhVien++;
                }
            }
        });

        btnThemNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = parentLinearLayout.getChildCount();//lay ra so con cua layout "parentLinearLayout"
                for (int i = 0; i < count; i++) {
                    View view = parentLinearLayout.getChildAt(i);//lay ra con thu i cua "parentLinearLayout"
                    if (view instanceof EditText) {// kiem tra xem co phai la edit text
                        viewValue = ((EditText) view).getText().toString();//lay value tu edit text
                        Log.i("Value Edit",viewValue);
                    }
                }
            }
        });
    }
}
