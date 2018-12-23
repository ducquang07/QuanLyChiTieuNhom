package vn.edu.uit.quanlychitieunhom.Views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.SpinnerLoaiGiaoDich_Adapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.loaigiaodich;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Services.LoaiGiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;
import static vn.edu.uit.quanlychitieunhom.Views.ThemGiaoDich.DATE_DIALOG_ID;

public class ChiTietHoaDon extends AppCompatActivity {
    Util util = new Util();
    int StatusCode;

    private TextView create_by;
    private EditText sotien;
    private TextView tennhomchitieu;
    private Spinner loaichitieu;
    private EditText ghichu ;
    private ImageView anhhoadon;
    EditText ngaygiaodich;
    Button  btnLuuThongTin;
    private ProgressBar progressBar;

    private int mYear;
    private int mMonth;
    private int mDay;

    private List<loaigiaodich> List_LoaiGiaoDich = new ArrayList<>();
    private loaigiaodich LoaiGiaoDich = new loaigiaodich();
    private giaodich current_giaodich;
    private giaodich new_giaodich;
    private int maloaigiaodich;


    public void GetComponentByID(){
        create_by = (TextView) findViewById(R.id.tvTenNguoiNhap);
//        TextView sotien = (TextView) findViewById(R.id.tvTongTien);
        sotien = (EditText) findViewById(R.id.txtTongtien) ;
//        TextView ngaygiaodich = (TextView) findViewById(R.id.tvNgayGiaoDich);
        ngaygiaodich = (EditText) findViewById(R.id.txtNgayGiaoDich);
        tennhomchitieu = (TextView) findViewById(R.id.tvTenNhomChiTieu);
        loaichitieu = (Spinner) findViewById(R.id.spLoaiGiaoDich) ;
//        TextView tenloaichitieu = (TextView) findViewById(R.id.tvLoaiChiTieu);
        ghichu = (EditText) findViewById(R.id.txtGhichu);
        anhhoadon = (ImageView) findViewById(R.id.imgHoaDon);
        btnLuuThongTin = (Button) findViewById(R.id.btnLuuThongTin);
        progressBar = (ProgressBar) findViewById(R.id.proBarChiTietHoaDon);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        GetComponentByID();
        addControl();
        Intent i= getIntent();

        Gson gson = new Gson();
        current_giaodich = gson.fromJson(i.getStringExtra("giaodich"),giaodich.class);
        Log.d("CURRENT",current_giaodich.toString());

        Date ngay = (Date) i.getSerializableExtra("ngaygiaodich");

        create_by.setText(current_giaodich.getTaikhoan().getTennguoidung());
        sotien.setText(String.valueOf(current_giaodich.getSotien()));
        ngaygiaodich.setText(util.DateStringByFormat(current_giaodich.getNgaygiaodich(),"dd/MM/yyyy"));
        tennhomchitieu.setText(current_giaodich.getNhomchitieu().getTennhomchitieu());

        maloaigiaodich = i.getIntExtra(String.valueOf(current_giaodich.getLoaigiaodich().getMaloaigiaodich()),0);
        ghichu.setText(current_giaodich.getGhichu());
        util.getImageByURL(getApplicationContext(),current_giaodich.getAnhhoadon(),anhhoadon);

        new getLoaiGiaoDichTask().execute();
        EditMode(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit:
                EditMode(true);
                return true;
            case R.id.remove:
                new deleteGiaoDichTask().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addControl(){
        progressBar.setVisibility(View.GONE);
        ngaygiaodich.setInputType(InputType.TYPE_NULL);
        ngaygiaodich.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        }); //TODO:Set an OnClickListener on the Change The Date Button

        ngaygiaodich.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new updateGiaoDichTask().execute();
            }
        });


        // Display the current date
        updateDisplay();

    }


    //TODO:Get information GiaoDich to insert
    public void GetInputGiaoDich() throws ParseException {
        Intent i = getIntent();
        new_giaodich = new giaodich();
        taikhoan create_by = util.getUserLocalStorage(getApplicationContext());
        new_giaodich = new giaodich(util.StringToDate(ngaygiaodich.getText().toString(),"dd/MM/yyyy"),
                Double.parseDouble(sotien.getText().toString()),
                ghichu.getText().toString(),
                i.getStringExtra("anhhoadon"),
                current_giaodich.getNhomchitieu(),
                current_giaodich.getTaikhoan(),
                LoaiGiaoDich,
                current_giaodich.getKychitieu());
        new_giaodich.setMagiaodich(current_giaodich.getMagiaodich());
    }

    //TODO: Update the date in the TextView
    private void updateDisplay() {
        ngaygiaodich.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append("/").append(mMonth + 1).append("/")
                .append(mYear).append(" "));
    }

    //TODO: Create and return DatePickerDialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    //TODO: The callback received when the user "sets" the date in the Dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };



    public void EditMode(boolean bool){
        loaichitieu.setEnabled(bool);
        sotien.setEnabled(bool);
        ghichu.setEnabled(bool);
        ngaygiaodich.setEnabled(bool);
        if(bool){
            btnLuuThongTin.setVisibility(View.VISIBLE);
        }
        else{
            btnLuuThongTin.setVisibility(View.INVISIBLE);
        }

    }

    public void setSpinnerAdapter(){
        final SpinnerLoaiGiaoDich_Adapter spinnerAdapter = new SpinnerLoaiGiaoDich_Adapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,List_LoaiGiaoDich);
        loaichitieu.setAdapter(spinnerAdapter);
        loaichitieu.setSelection(spinnerAdapter.getPositionById(maloaigiaodich));
        loaichitieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                LoaiGiaoDich = spinnerAdapter.getItem(position);//TODO: event click to selected item from spinner
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    //TODO: Get list loaigiaodich to bind for spinner
    public class  getLoaiGiaoDichTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Boolean  doInBackground(String... params) {
            try {
                LoaiGiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(LoaiGiaoDich_Service.class);
                Call<List<loaigiaodich>> call = service.getAllKyChiTieu();
                call.enqueue(new Callback<List<loaigiaodich>>() {
                    @Override
                    public void onResponse(Call<List<loaigiaodich>> call, Response<List<loaigiaodich>> response) {
                        StatusCode = response.code();
                        if(StatusCode== 200){
                            List_LoaiGiaoDich = response.body();
                            setSpinnerAdapter();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<loaigiaodich>> call, Throwable t) {
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


    //TODO: insert new Giao dich
    public class  updateGiaoDichTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

            try {
                showProgress(true);
                GetInputGiaoDich();
                Log.d("TEST",new_giaodich.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            // TODO: register the new account here.
            try {
//                Thread.sleep(2000);
                GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
                Call<Void> call = service.update(new_giaodich);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        StatusCode = response.code();
                        if(StatusCode == 200){
                            showProgress(false);
//                            clearInput();
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            finish();
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
                    public void onFailure(Call<Void> call, Throwable t) {
                        showProgress(false);
                        Log.d("ERR",t.getMessage());
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }
            finally {
                util.setFlagNewGiaoDich(getApplicationContext(),true, current_giaodich.getKychitieu().getMakychitieu());
            }
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



    //TODO: insert new Giao dich
    public class  deleteGiaoDichTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            // TODO: register the new account here.
            try {
//                Thread.sleep(2000);
                GiaoDich_Service service = RetrofitClientInstance.getRetrofitInstance().create(GiaoDich_Service.class);
                Call<Void> call = service.delete(current_giaodich.getMagiaodich());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        StatusCode = response.code();
                        if(StatusCode == 200){
                            showProgress(false);
//                            clearInput();
                            Toast.makeText(getApplicationContext(), "Đã xóa !", Toast.LENGTH_SHORT).show();
                            finish();
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
                            Toast.makeText(getApplicationContext(), "Xóa thất bại !", Toast.LENGTH_SHORT).show();
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
            finally {
                util.setFlagNewGiaoDich(getApplicationContext(),true, current_giaodich.getKychitieu().getMakychitieu());
            }
            return (StatusCode == 200)? true : false;
        }

    }

}
