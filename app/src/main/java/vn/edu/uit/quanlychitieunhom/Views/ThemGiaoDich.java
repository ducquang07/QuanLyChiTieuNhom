package vn.edu.uit.quanlychitieunhom.Views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import vn.edu.uit.quanlychitieunhom.R;

public class ThemGiaoDich extends AppCompatActivity {

    private EditText txtDate;
    private Button btnThem;
    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_giao_dich);

        // Capture our View elements
        txtDate = (EditText) findViewById(R.id.txtDate);
        btnThem = findViewById(R.id.btnThem);

        txtDate.setInputType(InputType.TYPE_NULL);

        // Set an OnClickListener on the Change The Date Button
        txtDate.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

//        txtDate.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                showDialog(DATE_DIALOG_ID);
//                return false;
//            }
//        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Thêm giao dịch", Toast.LENGTH_LONG).show();
            }
        });

        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });

        // Get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Display the current date
        updateDisplay();
    }

    // The callback received when the user "sets" the date in the Dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    // Update the date in the TextView
    private void updateDisplay() {
        txtDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append("/").append(mMonth + 1).append("/")
                .append(mYear).append(" "));
    }

    // Create and return DatePickerDialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }
}
