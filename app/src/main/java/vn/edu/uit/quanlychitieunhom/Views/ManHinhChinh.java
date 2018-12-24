package vn.edu.uit.quanlychitieunhom.Views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.SimpleFragmentPagerAdapter;
import vn.edu.uit.quanlychitieunhom.Adapters.SpinnerNhomChiTieu_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Services.KyChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Util util = new Util();
    int StatusCode;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private LinearLayout header_container;
    private NavigationView nav_view;
    private LinearLayout vLsNapTien;
    private Spinner spNhomChiTieu;
    private ImageView imageViewUser;
    private TextView nameUser;
    private TextView tvQuyNhom;




    private taikhoan user_admin = new taikhoan();
    private List<nhomchitieu> List_NhomChiTieu = new ArrayList<>();
    private nhomchitieu NhomChiTieu = new nhomchitieu();


    protected void ReferenceById(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        imageViewUser = (ImageView) hView.findViewById(R.id.imgUser_navigation);
        nameUser = (TextView) hView.findViewById(R.id.tvNameUser);
        spNhomChiTieu = findViewById(R.id.spNhomChiTieu);
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
        tvQuyNhom = findViewById(R.id.tvQuyNhom);
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

        user_admin = util.getUserLocalStorage(getApplicationContext());

        if(user_admin != null){
            new getNhomChiTieuTask().execute();
            new getKyChiTieu().execute();
        }


        vLsNapTien = findViewById(R.id.LsNapTien);
        vLsNapTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LichSuNapTien.class);
                startActivity(i);
            }
        });

        if (user_admin.getAvatar()!=null)
        {
            util.getImageByURL(getApplicationContext(),user_admin.getAvatar(),imageViewUser);
        }
        nameUser.setText(user_admin.getTennguoidung());

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
            Intent i = new Intent(getApplicationContext(), HienThiNhomChiTieu.class);
            startActivity(i);
        }else if(id == R.id.nav_so_giao_dich){
            Toast.makeText(getApplicationContext(),"Thiết lập kỳ chi tiêu",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), ThietLapKiChiTieu.class);
            startActivity(i);

        }else if(id == R.id.nav_thong_ke){
            Toast.makeText(getApplicationContext(),"Thống kê",Toast.LENGTH_LONG).show();
            Intent iThongke = new Intent(getApplicationContext(), ThongKe.class);
            startActivity(iThongke);

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

    public void setSpinnerAdapter(){
        final SpinnerNhomChiTieu_Adapter spinnerAdapter = new SpinnerNhomChiTieu_Adapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item ,List_NhomChiTieu);
        spNhomChiTieu.setAdapter(spinnerAdapter);
        spNhomChiTieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                NhomChiTieu = spinnerAdapter.getItem(position);//TODO: event click to selected item from spinner
                new getKyChiTieu().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    public class  getNhomChiTieuTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                Call<List<nhomchitieu>> call = service.getAllNhomChiTieu(user_admin.getTentaikhoan());
                call.enqueue(new Callback<List<nhomchitieu>>() {
                    @Override
                    public void onResponse(Call<List<nhomchitieu>> call, Response<List<nhomchitieu>> response) {
                        StatusCode = response.code();
                        if(StatusCode== 200){
                            List_NhomChiTieu = response.body();
                            setSpinnerAdapter();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<nhomchitieu>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("Test", "Exception");
            }
            finally {
                util.setFlagEditNhom(getApplicationContext(),false);
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
                GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
                Call<List<giaodich>> call = service.getAllGiaoDichNhom(NhomChiTieu.getManhomchitieu());
                call.enqueue(new Callback<List<giaodich>>() {
                    @Override
                    public void onResponse(Call<List<giaodich>> call, final Response<List<giaodich>> response) {
                        StatusCode = response.code();
                        tvQuyNhom.setText(util.DoubleToStringByFormat(TinhQuyNhom(NhomChiTieu,response.body()),"#,###")+"đ");
                    }
                    @Override
                    public void onFailure(Call<List<giaodich>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("Test", "Exception");
            }finally {
                util.setFlagNewKyChiTieu(getApplicationContext(),false,0);
            }

            try {
                KyChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(KyChiTieu_Service.class);
                Call<List<kychitieu>> call = service.getAllKyChiTieu(NhomChiTieu.getManhomchitieu());
                call.enqueue(new Callback<List<kychitieu>>() {
                    @Override
                    public void onResponse(Call<List<kychitieu>> call, final Response<List<kychitieu>> response) {
                        StatusCode = response.code();
                        List<kychitieu> ListKyChiTieu = response.body();
                        SimpleFragmentPagerAdapter simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),response.body());
                        viewPager.setAdapter(simpleFragmentPagerAdapter);
                        viewPager.setCurrentItem(response.body().size() - 1,true);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(response.body().size() > 0) {
                                    Bundle bundle = new Bundle();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(response.body().get(viewPager.getCurrentItem()).getNhomchitieu()); //TODO: convert to JSON and pass to ThemGiaoDich.class
                                    bundle.putString("nhomchitieu", json);
                                    json = gson.toJson(response.body().get(viewPager.getCurrentItem()));
                                    bundle.putString("kychitieu", json);
                                    Intent i = new Intent(getApplicationContext(), ThemGiaoDich.class);
                                    i.putExtras(bundle);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(getApplicationContext(),"Không tìm thấy kỳ chi tiêu nào để tạo giao dịch", Toast.LENGTH_LONG).show();
                                }
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
            }finally {
                util.setFlagNewKyChiTieu(getApplicationContext(),false,0);
            }
            // TODO: register the new account here.
            return (StatusCode == 200)? true : false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.FlagNewKyChiTieu flagNewKyChiTieu = util.getFlagNewKyChiTieu(getApplicationContext());
        if(flagNewKyChiTieu.isFlag() && flagNewKyChiTieu.getManhom() == NhomChiTieu.getManhomchitieu()){
            new getKyChiTieu().execute();
        }
        if(util.getFlagEditNhom(getApplicationContext())){
            new getNhomChiTieuTask().execute();
        }
    }

    public double TinhQuyNhom(nhomchitieu nhom,List<giaodich> list_GiaoDich){
        double quynhom = nhom.getQuy();
        for(giaodich item:list_GiaoDich){
            if(item.getLoaigiaodich().getNhom().equals("chi")){
                quynhom-=item.getSotien();
            }else{
                quynhom+=item.getSotien();
            }
        }
        return quynhom;
    }

}
