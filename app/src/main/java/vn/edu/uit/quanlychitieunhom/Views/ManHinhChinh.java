package vn.edu.uit.quanlychitieunhom.Views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.SimpleFragmentPagerAdapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.KyChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;

public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private LinearLayout header_container;
    private NavigationView nav_view;
    private taikhoan user_admin = new taikhoan();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        makeTranslucentStatusBar();
        setContentView(R.layout.activity_man_hinh_chinh);

        /* Get component from view*/
        ReferenceById();

        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String exist_user = sharedPref.getString(getString(R.string.user),"");
        Gson gson = new Gson();
        user_admin = gson.fromJson(exist_user, taikhoan.class);

        if(user_admin != null){
            try {
                KyChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(KyChiTieu_Service.class);
                Call<List<kychitieu>> call = service.getAllKyChiTieu(user_admin.getTentaikhoan());
                call.enqueue(new Callback<List<kychitieu>>() {
                    @Override
                    public void onResponse(Call<List<kychitieu>> call, Response<List<kychitieu>> response) {
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
        }


    }


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
        }else if(id == R.id.nav_xu_huong){
            Toast.makeText(getApplicationContext(),"Xu hướng",Toast.LENGTH_LONG).show();
        }else if(id == R.id.nav_so_giao_dich){
            Toast.makeText(getApplicationContext(),"Sổ giao dịch",Toast.LENGTH_LONG).show();
        }else if(id==R.id.nav_log_out){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(getString(R.string.user));
            editor.apply();

            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
        return true;
    }


}
