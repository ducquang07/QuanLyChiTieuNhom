package vn.edu.uit.quanlychitieunhom.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


import vn.edu.uit.quanlychitieunhom.R;

public class ThongTinNguoiDung extends AppCompatActivity {

    private Button btnthaydoi;

    private TextView txtngaysinh;
    private TextView txtngaydangki;
    private TextView txttennguoidung;
    private TextView txttentaikhoan;
    private TextView txtgioitinh;
    private TextView txtsodienthoai;
    private String url = "http://192.168.1.103:8080/api/taikhoan/hoaithanh";
    //private Taikhoan taikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);
        addControls();
        // getData(url);



    }

    private void addControls() {

        btnthaydoi = findViewById(R.id.btn_doithongtin);
        btnthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ThayDoiThongTinNguoiDung.class);
                startActivity(i);
            }
        });
    }
    /*private void getData(String url)
     {
         txtgioitinh = findViewById(R.id.txtgioitinh);
         txtngaydangki = findViewById(R.id.txtngaydangki);
         txtngaysinh = findViewById(R.id.txtngaysinh);
         txtsodienthoai = findViewById(R.id.txtsodienthoai);
         txttennguoidung = findViewById(R.id.txttennguoidung);
         txttentaikhoan = findViewById(R.id.txttentaikhoan);
         RequestQueue requestQueue = Volley.newRequestQueue(this);
         JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                 new Response.Listener<JSONObject>(){

                     @Override
                     public void onResponse(JSONObject response) {

                         taikhoan = new Taikhoan(response.optString("tentaikhoan"),response.optString("tennguoidung"),response.optString("gioitinh"),response.optString("ngaysinh"),response.optString("sodienthoai"),response.optString("ngaydangki"),response.optString("email"));
                         txttentaikhoan.setText(taikhoan.getTentaikhoan());
                         txttennguoidung.setText(taikhoan.getTennguoidung());
                         txtsodienthoai.setText(taikhoan.getSodienthoai());
                         txtngaysinh.setText(getDay(taikhoan.getNgaysinh()));
                         txtngaydangki.setText(getDay(taikhoan.getNgaydangki()));
                         txtgioitinh.setText(taikhoan.getGioitinh());

                     }
                 },
                 new Response.ErrorListener()
                 {

                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(ThongTinNguoiDung.this,error.toString(), Toast.LENGTH_SHORT).show();
                     }
                 });
         requestQueue.add(jsonArrayRequest);

     }*/
   /* public interface VolleyCallback {
        void onSuccessResponse(Taikhoan result);
    }

    private class FetchData extends AsyncTask<String,Integer,Taikhoan>
    {
        private TextView txtgioitinh = findViewById(R.id.txtgioitinh);
        private TextView txtngaydangki = findViewById(R.id.txtngaydangki);
        private TextView txtngaysinh = findViewById(R.id.txtngaysinh);
        private TextView txtsodienthoai = findViewById(R.id.txtsodienthoai);
        private TextView txttennguoidung = findViewById(R.id.txttennguoidung);
        private TextView txttentaikhoan = findViewById(R.id.txttentaikhoan);
        private Taikhoan taikhoan;
        private final VolleyCallback callback;

        private FetchData(VolleyCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Taikhoan doInBackground(String... strings) {
            String url = strings[0];

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>(){

                        @Override
                        public void onResponse(JSONObject response) {

                            taikhoan = new Taikhoan(response.optString("tentaikhoan"),response.optString("tennguoidung"),response.optString("gioitinh"),response.optString("ngaysinh"),response.optString("sodienthoai"),response.optString("ngaydangki"),response.optString("email"));
                            callback.onSuccessResponse(taikhoan);
                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ThongTinNguoiDung.this,error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
            return  taikhoan;

        }

        @Override
        protected void onPostExecute(Taikhoan taikhoan) {
            super.onPostExecute(taikhoan);
            txttentaikhoan.setText(taikhoan.getTentaikhoan());
            txttennguoidung.setText(taikhoan.getTennguoidung());
            txtsodienthoai.setText(taikhoan.getSodienthoai());
            txtngaysinh.setText(getDay(taikhoan.getNgaysinh()));
            txtngaydangki.setText(getDay(taikhoan.getNgaydangki()));
            txtgioitinh.setText(taikhoan.getGioitinh());



        }

    }*/
    //Format Day (dd-mm-yyyy)
    private String getDay(String ngay)
    {
        String[] separated = ngay.split("-");
        String[] _separated = separated[2].split("T");
        return _separated[0] +"-" + separated[1] +"-" + separated[0];
    }

}
