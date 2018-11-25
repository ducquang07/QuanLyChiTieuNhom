package vn.edu.uit.quanlychitieunhom.Views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import vn.edu.uit.quanlychitieunhom.R;

public class ThemKiChiTieu extends AppCompatActivity {

    private ImageButton mPickDateFrom;
    private TextView mDateDisplayFrom;
    private ImageButton mPickDateTo;
    private TextView mDateDisplayTo;
    private DatePickerDialog datePickerDialog;
    int year;
    int month;
    int day;
    Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_ki_chi_tieu);
        addControls();

    }

    private void addControls()
    {
        mPickDateFrom =  findViewById(R.id.pickDateFrom);
        mDateDisplayFrom = findViewById(R.id.dateDisplayFrom);

        mPickDateTo = findViewById(R.id.pickDateTo);
        mDateDisplayTo= findViewById(R.id.dateDisplayTo);

        mPickDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current day
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //
                datePickerDialog = new DatePickerDialog(ThemKiChiTieu.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                mDateDisplayFrom.setText(day + "/" + month + "/" + year);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });

        mPickDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current day
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //
                datePickerDialog = new DatePickerDialog(ThemKiChiTieu.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                mDateDisplayTo.setText(day + "/" + month + "/" + year);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });

    }
}
