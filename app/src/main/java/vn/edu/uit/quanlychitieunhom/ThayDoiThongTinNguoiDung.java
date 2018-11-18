package vn.edu.uit.quanlychitieunhom;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;


public class ThayDoiThongTinNguoiDung extends AppCompatActivity {

    private ImageButton mPickDate;
    private TextView mDateDisplay;
    private CheckBox checkboxPwd;
    private DatePickerDialog datePickerDialog;
    private LinearLayout backgroundChangePwd;
    private ImageButton btnClear;
    private EditText txtName;
    int year;
    int month;
    int day;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_thong_tin);
        addControls();



    }

    private void addControls()
    {
        mPickDate = (ImageButton) findViewById(R.id.pickDate);
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        checkboxPwd = findViewById(R.id.ckb_password);
        backgroundChangePwd = findViewById(R.id.background_pwd);
        btnClear = findViewById(R.id.btnclear);
        txtName = findViewById(R.id.txtName);

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
