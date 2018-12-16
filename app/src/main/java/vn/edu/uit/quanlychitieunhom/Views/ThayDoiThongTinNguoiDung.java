package vn.edu.uit.quanlychitieunhom.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.TaiKhoan_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;


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
    private EditText txtOldPassword;
    private EditText txtNewPassword;
    private RadioButton rbtnNam;
    private RadioButton rbtnNu;
    private Button btnCapNhat;
    private ProgressBar progressBar;

    int year;
    int month;
    int day;
    Calendar calendar;
    int StatusCode;
    taikhoan user_admin;
    int ERR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_thong_tin);
        user_admin = util.getUserLocalStorage(getApplicationContext());
        GetComponentByID();
        progressBar.setVisibility(GONE);
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
        txtOldPassword = findViewById(R.id.txtOldPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        rbtnNam = findViewById(R.id.rbtnNam);
        rbtnNu = findViewById(R.id.rbtnNu);
        btnCapNhat =findViewById(R.id.btn_doithongtin);
        progressBar = findViewById(R.id.proBarCapNhatThongTin);
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
                                mDateDisplay.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setError(null);
                txtOldPassword.setError(null);
                txtNewPassword.setError(null);
                checkValidate("Tên người dùng",txtName);
                if(checkboxPwd.isChecked()){
                    checkPassword();
//                    checkValidate("Mật khẩu cũ",txtOldPassword);
                    checkValidate("Mật khẩu mới",txtNewPassword);
                }
                if(ERR == 0){
                    new updateUserTask().execute();
                }
                else{
                    ERR = 0;
                }
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


    public class  updateUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            // TODO: register the new account here.
            try {
                Thread.sleep(2000);
                TaiKhoan_Service service = RetrofitClientInstance.getRetrofitInstance().create(TaiKhoan_Service.class);
                getInputInfo();
                Call<taikhoan> call = service.update(user_admin.getTentaikhoan(),user_admin);
                call.enqueue(new Callback<taikhoan>() {
                    @Override
                    public void onResponse(Call<taikhoan> call, Response<taikhoan> response) {


                        StatusCode = response.code();
                        if(StatusCode == 200){
                            util.setUserLocalStorage(getApplicationContext(),response.body());
                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Log.d("ERROR",jObjError.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("test",String.valueOf(response.code()));
                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<taikhoan> call, Throwable t) {
                        showProgress(false);
                        Log.d("ERR",t.getMessage());
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }
            return (StatusCode == 200)? true : false;
        }
    }


    public void getInputInfo() throws JSONException, ParseException {
        user_admin.setTennguoidung(txtName.getText().toString());
        user_admin.setSodienthoai(txtSodienthoai.getText().toString());
        if(rbtnNam.isChecked()) user_admin.setGioitinh("Nam");
        if(rbtnNu.isChecked()) user_admin.setGioitinh("Nữ");
        if(!mDateDisplay.getText().toString().equals("")) user_admin.setNgaysinh(new SimpleDateFormat("dd/MM/yyyy").parse(mDateDisplay.getText().toString()));
        if(checkboxPwd.isChecked()){
            user_admin.setMatkhau(txtNewPassword.getText().toString());
        }
    }


    public int checkValidate(String label,EditText editText){

            if(editText.getText().toString().equals(null)||editText.getText().equals("") || editText.getText().toString().isEmpty()){
                editText.setError(label+ " không được để trống");
                return ERR++;
            }
            editText.setError(null);
            return ERR;
    }

    public int checkPassword(){
        if(txtOldPassword.getText().toString().equals(null)||txtOldPassword.getText().equals("") || txtOldPassword.getText().toString().isEmpty()){
            txtOldPassword.setError("Mật khẩu cũ không được để trống");
            return ERR++;
        }
        if(!user_admin.getMatkhau().equals(txtOldPassword.getText().toString())){
            txtOldPassword.setError("Mật khẩu không đúng");
            return ERR++;
        }
        return ERR;
    }

    public void showProgress(boolean BOOL){
        if(BOOL){
            progressBar.setVisibility(GONE);
            progressBar.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
        }
        else{
            progressBar.setVisibility(GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}
