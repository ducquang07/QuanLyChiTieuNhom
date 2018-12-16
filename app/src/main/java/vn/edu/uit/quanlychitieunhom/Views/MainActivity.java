package vn.edu.uit.quanlychitieunhom.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.SimpleFragmentPagerAdapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.KyChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Services.TaiKhoan_Service;

import static android.view.View.GONE;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Login
    private EditText userId;
    private EditText password;
    private ProgressBar progressBar;
    private LinearLayout mLinearLayout;
    private int StatusCode ;
    private Button btnLogin;
    private Button btnRegister;
    private taikhoan user_admin = new taikhoan();


    //ManHinhChinh
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private LinearLayout header_container;
    private NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeTranslucentStatusBar();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String exist_user = sharedPref.getString(getString(R.string.user),"");
        Gson gson = new Gson();
        user_admin = gson.fromJson(exist_user, taikhoan.class);
        if(exist_user.equals("")){
            this.openLoginView();
        }
        else{
            this.openMainView();
        }
    }

    //Login
    private void addControls() {

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                userId.setError(null);
                password.setError(null);
                new validateUserTask().execute(new String[]{userId.getText().toString(), password.getText().toString()});
            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            //TODO:SAVE LOCAL STORAGE
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body()); // taikhoan - instance of taikhoan
                            editor.putString(getString(R.string.user),json);
                            editor.apply();
                            //TODO:SAVE LOCAL STORAGE
//                            openMainView();
                            Intent intent = new Intent(getApplicationContext(),ManHinhChinh.class);
                            startActivity(intent);
                            finish();
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
    }

    public class  getKyChiTieu extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                KyChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(KyChiTieu_Service.class);
                Call<List<kychitieu>> call = service.getAllKyChiTieu(user_admin.getTentaikhoan());
                call.enqueue(new Callback<List<kychitieu>>() {
                    @Override
                    public void onResponse(Call<List<kychitieu>> call, Response<List<kychitieu>> response) {
                        StatusCode = response.code();
                        SimpleFragmentPagerAdapter simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),response.body());
                        viewPager.setAdapter(simpleFragmentPagerAdapter);
                        viewPager.setCurrentItem(6,false);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getApplicationContext(), ThemGiaoDich.class);
                                startActivity(i);
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<List<kychitieu>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("Test", "Exception");
            }
            // TODO: register the new account here.
            return (StatusCode == 200)? true : false;
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



    //ManHinhChinh
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        header_container = findViewById(R.id.header_container);
        header_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThongTinNguoiDung.class);
                startActivity(i);
            }
        });

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        header_container = findViewById(R.id.header_container);
        header_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThongTinNguoiDung.class);
                startActivity(i);
            }
        });

        if(id == R.id.nav_nhom){
            Toast.makeText(getApplicationContext(),"Nhóm",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), HienThiNhomChiTieu.class);
            startActivity(i);
        }else if(id == R.id.nav_xu_huong){
            Toast.makeText(getApplicationContext(),"Xu hướng",Toast.LENGTH_LONG).show();
        }
        else if(id == R.id.nav_so_giao_dich){
            Toast.makeText(getApplicationContext(),"Sổ giao dịch",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.nav_log_out){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(getString(R.string.user));
            editor.apply();
            this.openLoginView();
        }
        return true;
    }

    protected void ReferenceById(){
        mDrawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = (TabLayout)  findViewById(R.id.tab_Layout);
        viewPager = (ViewPager) findViewById(R.id.view_paper);
        viewPager.setOffscreenPageLimit(3);
        fab = findViewById(R.id.fb);
        nav_view = findViewById(R.id.nav_view);
        if (nav_view != null) {
            nav_view.setNavigationItemSelectedListener(this);
        }
    }

    /* Code xu li transparent status bar*/
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //make translucent statusBar on kitkat devices
    public void makeTranslucentStatusBar(){
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    public void openMainView(){
        setContentView(R.layout.activity_man_hinh_chinh);
        ReferenceById();
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        tabLayout.setupWithViewPager(viewPager);

        new getKyChiTieu().execute();

    }

    public void openLoginView(){
        setContentView(R.layout.activity_main);
        userId = (EditText) findViewById(R.id.textUserID);
        password = (EditText) findViewById(R.id.textPassWord);
        progressBar = (ProgressBar) findViewById(R.id.proBarLogin);
        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        progressBar.setVisibility(View.GONE);
        addControls();
    }
}


