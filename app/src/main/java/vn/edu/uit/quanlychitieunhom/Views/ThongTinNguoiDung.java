package vn.edu.uit.quanlychitieunhom.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.R;
import vn.edu.uit.quanlychitieunhom.Services.TaiKhoan_Service;
import vn.edu.uit.quanlychitieunhom.Utils.Util;

import static android.view.View.GONE;

public class ThongTinNguoiDung extends AppCompatActivity {

    Util util = new Util();
    private Button btnthaydoi;
    private Button btnChonAvatar;
    private Button btnLuuAvatar;
    private TextView txtngaysinh;
    private TextView txtngaydangki;
    private TextView txttennguoidung;
    private TextView txttentaikhoan;
    private TextView txtgioitinh;
    private TextView txtsodienthoai;
    private TextView txtEmail;
    private taikhoan user_admin;
    private ImageView imageViewUser;
    private Uri filePath;
    int StatusCode;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri downloadUri;

    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        this.GetComponentByID();
        addControls();
        this.GetInfoUser();

    }

    public void GetComponentByID(){
        txttennguoidung = (TextView) findViewById(R.id.txttennguoidung);
        txttentaikhoan = (TextView) findViewById(R.id.txttentaikhoan);
        txtgioitinh = (TextView) findViewById(R.id.txtgioitinh);
        txtngaysinh = (TextView) findViewById(R.id.txtngaysinh);
        txtsodienthoai = (TextView) findViewById(R.id.txtsodienthoai);
        txtngaydangki =(TextView) findViewById(R.id.txtngaydangki);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        imageViewUser = (ImageView) findViewById(R.id.ImageViewUser);
        btnChonAvatar = findViewById(R.id.btnChonAvatar);
        btnLuuAvatar = findViewById(R.id.btnLuuAvatar);
    }

    public void GetInfoUser(){
        user_admin = util.getUserLocalStorage(getApplicationContext());
        txttennguoidung.setText(user_admin.getTennguoidung());
        txttentaikhoan.setText(user_admin.getTentaikhoan());
        txtEmail.setText(user_admin.getEmail());
        txtgioitinh.setText((user_admin.getGioitinh() != null)?user_admin.getGioitinh():"Chưa cập nhật");
        txtngaysinh.setText((user_admin.getNgaysinh() != null )?util.DateStringByFormat(user_admin.getNgaysinh(),"dd/MM/yyyy"):"Chưa cập nhật");
        txtsodienthoai.setText((user_admin.getSodienthoai() != null)?user_admin.getSodienthoai():"Chưa cập nhật");
        txtngaydangki.setText((user_admin.getNgaydangky() != null)?util.DateStringByFormat(user_admin.getNgaydangky(),"dd/MM/yyyy"):"Chưa cập nhật");
        if (user_admin.getAvatar()!=null && filePath ==null)
        {
            util.getImageByURL(getApplicationContext(),user_admin.getAvatar(),imageViewUser);
        }
    }

    private void addControls() {
        btnthaydoi = findViewById(R.id.btn_doithongtin);
        btnthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tentaikhoan",user_admin.getTentaikhoan());
                bundle.putString("tennguoidung",user_admin.getTennguoidung());
                bundle.putString("sodienthoai",user_admin.getSodienthoai());
                bundle.putString("gioitinh",user_admin.getGioitinh());
                bundle.putSerializable("ngaysinh",user_admin.getNgaysinh());
                Intent i = new Intent(getApplicationContext(), ThayDoiThongTinNguoiDung.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btnLuuAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        btnChonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose();
            }
        });
    }

    private void Choose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void upload() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving...");
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
                        new updateUserTask().execute();
                        progressDialog.dismiss();
//                        Toast.makeText(ThongTinNguoiDung.this, "Đã upload ảnh đại diện", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ThongTinNguoiDung.this, "Upload thất bại "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Đang xử lý "+(int)progress+"%");
                }
            });
        }
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
                imageViewUser.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String getDay(String ngay)
    {
        String[] separated = ngay.split("-");
        String[] _separated = separated[2].split("T");
        return _separated[0] +"-" + separated[1] +"-" + separated[0];
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetInfoUser();
    }

    public class  updateUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
//            showProgress(true);
            if (downloadUri != null){
                user_admin.setAvatar(downloadUri.toString());
            }
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            // TODO: register the new account here.
            try {
//                Thread.sleep(2000);
                TaiKhoan_Service service = RetrofitClientInstance.getRetrofitInstance().create(TaiKhoan_Service.class);
                Call<taikhoan> call = service.update(user_admin.getTentaikhoan(),user_admin);
                call.enqueue(new Callback<taikhoan>() {
                    @Override
                    public void onResponse(Call<taikhoan> call, Response<taikhoan> response) {


                        StatusCode = response.code();
                        if(StatusCode == 200){
                            util.setUserLocalStorage(getApplicationContext(),response.body());
//                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
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
//                            showProgress(false);
                            Toast.makeText(getApplicationContext(), "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<taikhoan> call, Throwable t) {
//                        showProgress(false);
                        Log.d("ERR",t.getMessage());
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra. Vui lòng thao tác lại sau !", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }
            return (StatusCode == 200)? true : false;
        }
    }
}
