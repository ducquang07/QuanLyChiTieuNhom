package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.NhomChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Services.ThanhVienNhom_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;

public class ThemNhomChiTieu extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    private Button btnThemthanhvien;
    private Button btnThemNhom;
    private ProgressBar progressBar;
    private Integer soThanhVien = 0;
    private String viewValue;
    private nhomchitieu new_nhomchitieu;
    private EditText txtTenNhom;
    Util util = new Util();
    nhomchitieu NhomChiTieu = new nhomchitieu();
    int StatusCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhom_chi_tieu);
        GetComponentByID();
        addControl();

    }

    private void GetComponentByID() {
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        btnThemthanhvien = findViewById(R.id.btnThemThanhVien);
        btnThemNhom = findViewById(R.id.btnThemNhom);
        txtTenNhom = findViewById(R.id.txtTenNhom);
        progressBar = findViewById(R.id.progressBar_ThemNhom);
    }

    private void addControl() {
        progressBar.setVisibility(GONE);
        btnThemthanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soThanhVien < 3)//gioi han so thanh vien nhom
                {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.fragment_ten_thanh_vien, null);
                    // Add the new row before the add field button.
                    parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());//add rowView vao "parentLinearLayout" tai vi tri parentLinearLayout.getChildCount()
                    soThanhVien++;
                }
            }
        });

        btnThemNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new insertNhomChiTieuTask().execute();

            }
        });
    }

    public class insertNhomChiTieuTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            try {
                showProgress(true);
                GetInputNhomChiTieu();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                final taikhoan user = util.getUserLocalStorage(getApplicationContext());
                NhomChiTieu_Service service = RetrofitClientInstance.getRetrofitInstance().create(NhomChiTieu_Service.class);
                Call<nhomchitieu> call = service.insert(new_nhomchitieu);
                call.enqueue(new Callback<nhomchitieu>() {
                    @Override
                    public void onResponse(Call<nhomchitieu> call, Response<nhomchitieu> response) {
                        StatusCode = response.code();
                        NhomChiTieu = response.body();
                        if(StatusCode == 200){
                            showProgress(false);
                            //them thanh vien vao nhom
                            try {
                                ThanhVienNhom_Service thanhVienNhomService = RetrofitClientInstance.getRetrofitInstance().create(ThanhVienNhom_Service.class);
                                Call<Void> call1 = thanhVienNhomService.insert(NhomChiTieu.getManhomchitieu(),user.getTentaikhoan());
                                call1.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        StatusCode = response.code();
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("ERR",t.getMessage());
                                    }
                                });
                                int count = parentLinearLayout.getChildCount();//lay ra so con cua layout "parentLinearLayout"
                                for (int i = 0; i < count; i++) {
                                    View view = parentLinearLayout.getChildAt(i);//lay ra con thu i cua "parentLinearLayout"
                                    if (view instanceof EditText) {// kiem tra xem co phai la edit text
                                        viewValue = ((EditText) view).getText().toString();//lay value tu edit text
//                                        Log.i("Value Edit",viewValue);
                                        Call<Void> call2 = thanhVienNhomService.insert(NhomChiTieu.getManhomchitieu(),viewValue);// lap lai viẹc them cho den khi het field edit text
                                        call2.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                StatusCode = response.code();
                                            }
                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Log.d("ERR",t.getMessage());
                                            }
                                        });
                                    }
                                }
                            }catch (Exception e) {
                                Log.d("ERROR", e.toString());
                            }

                            Toast.makeText(getApplicationContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        }else{
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
                            Toast.makeText(getApplicationContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<nhomchitieu> call, Throwable t) {
                        showProgress(false);
                        Log.d("ERR",t.getMessage());
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e) {
                Log.d("ERROR", e.toString());
            }
            return (StatusCode == 200)? true : false;
        }
    }

    public void GetInputNhomChiTieu() throws ParseException {
//        new_nhomchitieu = new nhomchitieu();
//        taikhoan create_by = util.getUserLocalStorage(getApplicationContext());
        new_nhomchitieu = new nhomchitieu(txtTenNhom.getText().toString(),0.0);
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
