package vn.edu.uit.quanlychitieunhom.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import vn.edu.uit.quanlychitieunhom.Adapters.SimpleFragmentPagerAdapter;
import vn.edu.uit.quanlychitieunhom.R;


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



    protected void ReferenceById(){
        mDrawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = (TabLayout)  findViewById(R.id.tab_Layout);
        viewPager = (ViewPager) findViewById(R.id.view_paper);
        fab = findViewById(R.id.fb);
//        header_container = findViewById(R.id.header_container);
        nav_view = findViewById(R.id.nav_view);
        if (nav_view != null) {
            nav_view.setNavigationItemSelectedListener(this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        makeTranslucentStatusBar();
        super.onCreate(savedInstanceState);
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
        SimpleFragmentPagerAdapter simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(simpleFragmentPagerAdapter);
        viewPager.setCurrentItem(6,false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ThemGiaoDich.class);
                startActivity(i);
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

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
        }else if(id == R.id.nav_so_no){
            Toast.makeText(getApplicationContext(),"Sổ nợ",Toast.LENGTH_LONG).show();
        }else if(id == R.id.nav_xu_huong){
            Toast.makeText(getApplicationContext(),"Xu hướng",Toast.LENGTH_LONG).show();
        }else if(id == R.id.nav_so_giao_dich){
            Toast.makeText(getApplicationContext(),"Sổ giao dịch",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
