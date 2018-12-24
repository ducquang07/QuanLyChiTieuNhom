package vn.edu.uit.quanlychitieunhom.Views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.SimpleFragmentPagerAdapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.kychitieu;
import vn.edu.uit.quanlychitieunhom.Models.loaigiaodich;
import vn.edu.uit.quanlychitieunhom.Models.nhomchitieu;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.GiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Services.KyChiTieu_Service;
import vn.edu.uit.quanlychitieunhom.Services.LoaiGiaoDich_Service;
import vn.edu.uit.quanlychitieunhom.Services.TaiKhoan_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;
import static vn.edu.uit.quanlychitieunhom.Models.loaigiaodich.*;

public class ThemGiaoDich extends AppCompatActivity {
    int ERR = 0;
    Util util = new Util();
    int StatusCode;
    private ImageButton btnChooseImage;
    private ImageButton btnUploadImage;
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

    private ProgressBar progressBar;
    private Spinner spLoaiChiTieu;
    private EditText txtDate;
    private EditText txtSotien;
    private EditText txtGhichu;
    private Button btnThem;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Uri downloadUri;
    private Boolean Flag = true;//co xac dinh nhan upload hay chua (true da nhan upload anh va da upload xg)

    private kychitieu kychitieu;
    private List<loaigiaodich> List_LoaiGiaoDich = new ArrayList<>();
    private nhomchitieu NhomChiTieu = new nhomchitieu();
    private giaodich new_giaodich ;
    private loaigiaodich LoaiGiaoDich = new loaigiaodich();

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_giao_dich);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        GetComponentByID();
        addControls();
        GetNhomChiTieu();
        GetKyChiTieu();
    }


    public void GetNhomChiTieu(){
        Intent i = getIntent();
        Gson gson = new Gson();
        String json_nhomchitieu = i.getStringExtra("nhomchitieu");
        NhomChiTieu =  gson.fromJson(json_nhomchitieu, nhomchitieu.class);
    }

    public void GetKyChiTieu(){
        Intent i = getIntent();
        Gson gson = new Gson();
        String json_kychitieu = i.getStringExtra("kychitieu");
        kychitieu = gson.fromJson(json_kychitieu,kychitieu.class);
    }

    public void addControls(){
        progressBar.setVisibility(GONE);
        new getLoaiGiaoDichTask().execute();//TODO: Setting spinner Loai Giao Dich
        txtDate.setInputType(InputType.TYPE_NULL);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        }); //TODO:Set an OnClickListener on the Change The Date Button

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate("Số tiền",txtSotien);
                if(ERR == 0){
                    //TODO:
                    if (Flag){
                        new insertGiaoDichTask().execute();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Vui lòng nhấn upload ảnh hoặc chờ upload hoàn thành trước khi thêm giao dịch",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    ERR = 0;
                }
            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        // Display the current date
        updateDisplay();
    }

    //[BEGIN UPLOAD IMAGE]

    private void uploadImage() {
        Flag = false;//chac chan co là false. xac dinh chua upload xg
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString()+".jpg");
            UploadTask uploadTask = ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        progressDialog.dismiss();
                        Toast.makeText(ThemGiaoDich.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        Flag = true;//xac nhan nguoi dung da uload anh
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ThemGiaoDich.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }
    }

    //upload Image
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        Flag = false;// danh dau false neu ham duọc goi. de xac dinh nguoi dung co chon anh hay khong (false la da chon anh)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //[END UPLOAD IMAGE]

    public void GetComponentByID(){
        txtDate = (EditText) findViewById(R.id.txtDate);
        btnThem = findViewById(R.id.btnThem);
        spLoaiChiTieu = (Spinner) findViewById(R.id.spinner1);
        txtSotien = findViewById(R.id.txtSotien);
        txtGhichu = findViewById(R.id.txtGhichu);
        progressBar = (ProgressBar) findViewById(R.id.proBarThemGiaoDich);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        imageView = findViewById(R.id.viewImage);
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

    //TODO: Update the date in the TextView
    private void updateDisplay() {
        txtDate.setText(new StringBuilder()
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


    //TODO:Get information GiaoDich to insert
    public void GetInputGiaoDich() throws ParseException {
        String linkImage;
        if (downloadUri != null){
            linkImage = downloadUri.toString();
        }else {
            linkImage = null;
        }
        new_giaodich = new giaodich();
        taikhoan create_by = util.getUserLocalStorage(getApplicationContext());
        new_giaodich = new giaodich(util.StringToDate(txtDate.getText().toString(),"dd/MM/yyyy"),
                                    Double.parseDouble(txtSotien.getText().toString()),
                                    txtGhichu.getText().toString(),
                                    linkImage,
                                    NhomChiTieu,
                                    create_by,
                                    LoaiGiaoDich,
                                    kychitieu);
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

    //TODO: clear input after insert
    public void clearInput(){
        txtSotien.setText("");
        txtGhichu.setText("");
        imageView.setImageDrawable(null);
        filePath = null;
        downloadUri = null;
    }



    //TODO: insert new Giao dich
    public class  insertGiaoDichTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

            try {
                showProgress(true);
                GetInputGiaoDich();
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
                Call<Void> call = service.insert(new_giaodich);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        StatusCode = response.code();
                        if(StatusCode == 200){
                            showProgress(false);
                            clearInput();
                            Toast.makeText(getApplicationContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
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
                util.setFlagNewGiaoDich(getApplicationContext(),true, kychitieu.getMakychitieu());
            }
            return (StatusCode == 200)? true : false;
        }

    }

    public void setSpinnerAdapter(){
        final SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,List_LoaiGiaoDich);
        spLoaiChiTieu.setAdapter(spinnerAdapter);
        spLoaiChiTieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                LoaiGiaoDich = spinnerAdapter.getItem(position);//TODO: event click to selected item from spinner
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    public class SpinnerAdapter extends ArrayAdapter<loaigiaodich>{
        private Context context;
        private List<loaigiaodich> values = new ArrayList<>();
        public SpinnerAdapter(Context context, int textViewResourceId,List<loaigiaodich> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }


        @Override
        public int getCount() {
            return values.size();
        }


        @Override
        public loaigiaodich getItem(int position) {
            return values.get(position);
        }


        @Override
        public long getItemId(int position) {
            return values.get(position).getMaloaigiaodich();
        }

        @Override
        public View getView(int position, View convertView,ViewGroup parent) {
            TextView label = (TextView) super.getView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getTenloaigiaodich());
            return label;
        }

        @Override
        public View getDropDownView(int position,View convertView,ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getTenloaigiaodich());
            return label;
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
