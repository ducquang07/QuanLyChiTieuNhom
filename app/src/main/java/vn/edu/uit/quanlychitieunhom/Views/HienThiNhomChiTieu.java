package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vn.edu.uit.quanlychitieunhom.R;

public class HienThiNhomChiTieu extends AppCompatActivity {

    private FloatingActionButton fabThemNhom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_nhom_chi_tieu);
        ReferenceById();
        addControl();

    }

    private void ReferenceById(){
        fabThemNhom = findViewById(R.id.fabThemNhom);
    }

    private void addControl() {
        fabThemNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThemNhomChiTieu.class);
                startActivity(i);
            }
        });
    }

}
