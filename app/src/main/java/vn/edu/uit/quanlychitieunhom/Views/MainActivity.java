package vn.edu.uit.quanlychitieunhom.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import static android.view.View.GONE;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity {

    
    private EditText userId;
    private EditText password;
    private ProgressBar progressBar;
    private LinearLayout mLinearLayout;
    private int StatusCode ;
    private Button btnLogin;
    private Button btnRegister;
    private taikhoan user_admin = new taikhoan();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String exist_user = sharedPref.getString(getString(R.string.user),"");
        Gson gson = new Gson();
        user_admin = gson.fromJson(exist_user, taikhoan.class);
        if(exist_user.equals("")){
            setContentView(R.layout.activity_main);
            userId = (EditText) findViewById(R.id.textUserID);
            password = (EditText) findViewById(R.id.textPassWord);
            progressBar = (ProgressBar) findViewById(R.id.proBarLogin);
            mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
            progressBar.setVisibility(View.GONE);
            addControls();
        }
        else{
            Log.d("user_exist",exist_user);
            Intent i = new Intent(getApplicationContext(), ManHinhChinh.class);
            startActivity(i);
        }
    }

    private void addControls() {

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                new validateUserTask().execute(new String[]{userId.getText().toString(), password.getText().toString()});
            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Login(userId.getText().toString(), password.getText().toString());
                Intent i = new Intent(getApplicationContext(), DangKiTaiKhoan.class);
                startActivity(i);
            }
        });
    }


    public class  validateUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                Thread.sleep(2000);
                TaiKhoan_Service service = RetrofitClientInstance.getRetrofitInstance().create(TaiKhoan_Service.class);
                Call<taikhoan> call = service.login(params[0], params[1]);
                call.enqueue(new Callback<taikhoan>() {
                    @Override
                    public void onResponse(Call<taikhoan> call, Response<taikhoan> response) {

                        user_admin = response.body();
                        StatusCode = response.code();
                        if(StatusCode== 401){
                            Log.d("test",String.valueOf(response.code()));
                            showProgress(false);

                            userId.setError("Tên đăng nhập không đúng");
                            password.setError("Mật khẩu không đúng");
                            Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body()); // taikhoan - instance of taikhoan
                            editor.putString(getString(R.string.user),json);
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(), ManHinhChinh.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<taikhoan> call, Throwable t) {
                        showProgress(false);
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }

            // TODO: register the new account here.
            return (StatusCode == 200)? true : false;
        }

//        @Override
//        protected void onPostExecute(Boolean success) {
//            super.onPreExecute();
////            progressBar.setVisibility(GONE);
//        }
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


