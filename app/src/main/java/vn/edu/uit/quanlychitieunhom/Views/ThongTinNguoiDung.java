package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class ThongTinNguoiDung extends AppCompatActivity {

    Util util = new Util();
    private Button btnthaydoi;
    private TextView txtngaysinh;
    private TextView txtngaydangki;
    private TextView txttennguoidung;
    private TextView txttentaikhoan;
    private TextView txtgioitinh;
    private TextView txtsodienthoai;
    private TextView txtEmail;
    private taikhoan user_admin;
    private ImageView imageViewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        this.GetComponentByID();
        addControls();
        this.GetInfoUser();

    }

    public void GetComponentByID(){
        txttennguoidung = (TextView) findViewById(R.id.txttennguoidung);
        txttentaikhoan = (TextView) findViewById(R.id.txttentaikhoan);
        txtgioitinh = (TextView) findViewById(R.id.txtgioitinh);
        txtngaysinh = (TextView) findViewById(R.id.txtngaysinh);
        txtsodienthoai = (TextView) findViewById(R.id.txtsodienthoai);
        txtngaydangki =(TextView) findViewById(R.id.txtngaydangki);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        imageViewUser = (ImageView) findViewById(R.id.ImageViewUser);
    }

    public void GetInfoUser(){
        user_admin = util.getUserLocalStorage(getApplicationContext());
        txttennguoidung.setText(user_admin.getTennguoidung());
        txttentaikhoan.setText(user_admin.getTentaikhoan());
        txtEmail.setText(user_admin.getEmail());
        txtgioitinh.setText((user_admin.getGioitinh() != null)?user_admin.getGioitinh():"Chưa cập nhật");
        txtngaysinh.setText((user_admin.getNgaysinh() != null )?util.DateStringByFormat(user_admin.getNgaysinh(),"dd/MM/yyyy"):"Chưa cập nhật");
        txtsodienthoai.setText((user_admin.getSodienthoai() != null)?user_admin.getSodienthoai():"Chưa cập nhật");
        txtngaydangki.setText((user_admin.getNgaydangky() != null)?util.DateStringByFormat(user_admin.getNgaydangky(),"dd/MM/yyyy"):"Chưa cập nhật");
    }

    private void addControls() {
        btnthaydoi = findViewById(R.id.btn_doithongtin);
        btnthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tentaikhoan",user_admin.getTentaikhoan());
                bundle.putString("tennguoidung",user_admin.getTennguoidung());
                bundle.putString("sodienthoai",user_admin.getSodienthoai());
                bundle.putString("gioitinh",user_admin.getGioitinh());
                bundle.putSerializable("ngaysinh",user_admin.getNgaysinh());
                Intent i = new Intent(getApplicationContext(), ThayDoiThongTinNguoiDung.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        imageViewUser.setImageBitmap(util.getImageUser(getApplicationContext()));
    }

    private String getDay(String ngay)
    {
        String[] separated = ngay.split("-");
        String[] _separated = separated[2].split("T");
        return _separated[0] +"-" + separated[1] +"-" + separated[0];
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetInfoUser();
    }
}
