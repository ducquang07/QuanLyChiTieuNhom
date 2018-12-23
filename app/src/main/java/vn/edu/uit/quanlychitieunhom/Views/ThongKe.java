package vn.edu.uit.quanlychitieunhom.Views;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.RecyclerView_ThongKe_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.list_thongke;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

public class ThongKe extends AppCompatActivity {
    Util util = new Util();
    int StatusCode;

    private List<giaodich> list_giaodich = new ArrayList<>(  );
    private List<nhomchitieu> list_nhom = new ArrayList<>(  );
    private taikhoan user_admin;

    private EditText mPickDateFrom;
    private EditText mPickDateTo;
    private DatePickerDialog datePickerDialog;
    private Button btnThongKe;
    private RecyclerView recyclerView;

    static final int DATE_DIALOG_ID = 0;
    int year;
    int month;
    int day;
    Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        user_admin = util.getUserLocalStorage( getApplicationContext() );
        setContentView( R.layout.activity_thong_ke);
        getComponentByID();
        addControls();

    }

    public void getComponentByID(){
        mPickDateFrom =  findViewById(R.id.mPickDateFrom);
        mPickDateTo = findViewById(R.id.mPickDateTo);
        btnThongKe = (Button)findViewById( R.id.buttonthongke );

    }

    private void addControls() {

        mPickDateTo.setText(util.DateStringByFormat(new Date(),"yyyy-MM-dd"));
        mPickDateFrom.setText(util.DateStringByFormat(new Date(),"yyyy-MM-dd"));


        mPickDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current day
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //
                datePickerDialog = new DatePickerDialog(ThongKe.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                mPickDateFrom.setText(year + "-" + (month + 1) + "-" + day);
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
                datePickerDialog = new DatePickerDialog(ThongKe.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                mPickDateTo.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });


        mPickDateFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });

        mPickDateTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });

        //Button Thống kê
        btnThongKe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
                Log.d( "TEST", mPickDateFrom.getText().toString());
                Log.d( "TEST", mPickDateTo.getText().toString());
                Log.d( "TEST", user_admin.getTentaikhoan());

                new getThongKeTask().execute( mPickDateFrom.getText().toString(), mPickDateTo.getText().toString(),user_admin.getTentaikhoan() );
            }
        } );

    }

    public  void initView()
    {

        recyclerView = (RecyclerView)findViewById(R.id.rcl_listthongke);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
//        // Truyền vào list thống kê
//        List<list_thongke> list_thongkeList= new ArrayList<>(  );
//        //dữ liệu test
//        list_thongkeList.add( new list_thongke( "Nhóm 1" , "30000") );
//        list_thongkeList.add( new list_thongke( "Nhóm 2" , "20000") );
//        list_thongkeList.add( new list_thongke( "Nhóm 3" , "20000") );
//        list_thongkeList.add( new list_thongke( "Nhóm 1" , "30000") );
//        list_thongkeList.add( new list_thongke( "Nhóm 2" , "20000") );
//        list_thongkeList.add( new list_thongke( "Nhóm 3" , "20000") );
//        //
//
//        RecyclerView_ThongKe_Adapter listThanhVienNhomAdapter = new RecyclerView_ThongKe_Adapter(list_thongkeList,getApplicationContext());
//        recyclerView.setAdapter(listThanhVienNhomAdapter);
    }



    public class  getThongKeTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
                Call<List<giaodich>> call = service.getThongKe(params[0],params[1],params[2]);
                call.enqueue(new Callback<List<giaodich>>() {
                    @Override
                    public void onResponse(Call<List<giaodich>> call, final Response<List<giaodich>> response) {
                        StatusCode = response.code();
                        list_giaodich =response.body();
                        RecyclerView_ThongKe_Adapter listThanhVienNhomAdapter = new RecyclerView_ThongKe_Adapter(deployThongKe( list_nhom,response.body()),response.body(),getApplicationContext());
                        recyclerView.setAdapter(listThanhVienNhomAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<giaodich>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("Test", "Exception");
            }
            finally {
                util.setFlagNewKyChiTieu(getApplicationContext(),false,0);
            }
            // TODO: register the new account here.
            return (StatusCode == 200)? true : false;
        }



        public List<nhomchitieu> deployThongKe(List<nhomchitieu>list_nhom,List<giaodich> list_giaodich){
            for(giaodich item:list_giaodich){
                if(!checkExist( item.getNhomchitieu(),list_nhom )){
                    list_nhom.add( item.getNhomchitieu());
                }
            }

            return list_nhom;
        }

        public boolean checkExist(nhomchitieu nhom,List<nhomchitieu>list_nhom){
            for(nhomchitieu item : list_nhom){
                if(item.getManhomchitieu() == nhom.getManhomchitieu()){
                    return true;
                }
            }
            return false;
        }
    }
}
