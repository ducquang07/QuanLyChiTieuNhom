package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.TaiKhoan_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;

public class DangKiTaiKhoan extends AppCompatActivity {
    Util util = new Util();

    private int ERR = 0;
    private EditText email;
    private EditText tennguoidung;
    private EditText tendangnhap;
    private EditText matkhau;
    private ProgressBar progressBar;

    private taikhoan new_user;
    int StatusCode;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_ki);
        getComponentByID();
        progressBar.setVisibility(View.GONE);
        addControl();
    }

    public void addControl(){
        Button btn_dangky = (Button) findViewById(R.id.btnSignUp);
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate("Email",true,email);
                checkValidate("Tên người dùng",false,tennguoidung);
                checkValidate("Tên đăng nhập",false,tendangnhap);
                checkValidate("Mật khẩu",false,matkhau);
                if(ERR == 0){
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(matkhau.getWindowToken(), 0);
                    email.setError(null);
                    tennguoidung.setError(null);
                    tendangnhap.setError(null);
                    matkhau.setError(null);
                    new signupTask().execute(   email.getText().toString(),
                                                tennguoidung.getText().toString(),
                                                tendangnhap.getText().toString(),
                                                matkhau.getText().toString());
                }
                else{
                    ERR = 0;
                }
            }
        });
    }

    public void getComponentByID(){
        email = findViewById(R.id.txtEmail);
        tennguoidung = findViewById(R.id.txtTenNguoiDung);
        tendangnhap = findViewById(R.id.txtTenDangNhap);
        matkhau = findViewById(R.id.txtMatKhau);
        progressBar = findViewById(R.id.proBarSignUp);
    }


    public int checkValidate(String label,Boolean isMail,EditText editText){
        if(isMail){
            if(!util.checkMailFormat(editText.getText().toString())) {
                editText.setError("Email không hợp lệ");
                return ERR++;
            }
        }
        if(editText.getText().toString().equals(null)||editText.getText().equals("") || editText.getText().toString().isEmpty()){
            editText.setError(label+ " không được để trống");
            return ERR++;
        }
        editText.setError(null);
        return ERR;
    }


    public class  signupTask extends AsyncTask<String, Void, Boolean> {

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
                Call<Void> call = service.signup(params[0], params[1],params[2],params[3]);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

//                        new_user = response.body();
                        StatusCode = response.code();
                        Log.d("STATUSCODE",String.valueOf(StatusCode));
                        if(StatusCode== 204){
                            Log.d("test",String.valueOf(response.code()));
                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                        }
                        else if(StatusCode == 201){
                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
                            clearFormData();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showProgress(false);
                        Log.d("ERR",t.getMessage());
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }
            return (StatusCode == 201)? true : false;
        }
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


    public void clearFormData(){
        email.setText("");
        tennguoidung.setText("");
        tendangnhap.setText("");
        matkhau.setText("");
    }
}
