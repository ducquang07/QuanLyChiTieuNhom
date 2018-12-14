package vn.edu.uit.quanlychitieunhom.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Utils.Util;


public class ThayDoiThongTinNguoiDung extends AppCompatActivity {

    Util util = new Util();
    private ImageButton mPickDate;
    private TextView mDateDisplay;
    private CheckBox checkboxPwd;
    private DatePickerDialog datePickerDialog;
    private LinearLayout backgroundChangePwd;
    private ImageButton btnClear;
    private EditText txtName;
    private EditText txtSodienthoai;
    private RadioButton rbtnNam;
    private RadioButton rbtnNu;

    int year;
    int month;
    int day;
    Calendar calendar;
    Date ngaysinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_thong_tin);
        GetComponentByID();
        setInfoUser();
        addControls();
    }


    public void GetComponentByID(){
        mPickDate = (ImageButton) findViewById(R.id.pickDate);
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        checkboxPwd = findViewById(R.id.ckb_password);
        backgroundChangePwd = findViewById(R.id.background_pwd);
        btnClear = findViewById(R.id.btnclear);
        txtName = findViewById(R.id.txtName);
        txtSodienthoai = findViewById(R.id.txtSodienthoai);
        rbtnNam = findViewById(R.id.rbtnNam);
        rbtnNu = findViewById(R.id.rbtnNu);
    }

    public void setInfoUser(){
        Intent i= getIntent();

        txtName.setText(i.getStringExtra("tennguoidung"));
        txtSodienthoai.setText(i.getStringExtra("sodienthoai"));
        String gioitinh = i.getStringExtra("gioitinh");
        if(gioitinh != null){
            if(gioitinh.equals("Nam")){
                rbtnNam.setChecked(true);
            }
            else rbtnNu.setChecked(true);
        }

        mDateDisplay.setText(!(i.getSerializableExtra("ngaysinh") != null)?"":util.DateStringByFormat((Date) i.getSerializableExtra("ngaysinh"),"dd/MM/yyyy"));
    }


    private void addControls()
    {
        //Call when click button edit birthday
        mPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current day
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //
                datePickerDialog = new DatePickerDialog(ThayDoiThongTinNguoiDung.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                mDateDisplay.setText(day + "/" + month + "/" + year);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });

        //Call when changepassword checkbox is checked
        checkboxPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    backgroundChangePwd.setVisibility(View.VISIBLE);
                else
                    backgroundChangePwd.setVisibility(View.INVISIBLE);
            }
        });

        //Call when click imagebutton x to clear edittext
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setText("");
            }
        });

    }







}
