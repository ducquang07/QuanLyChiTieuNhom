package vn.edu.uit.quanlychitieunhom.Utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.uit.quanlychitieunhom.Adapters.SimpleFragmentPagerAdapter;
import vn.edu.uit.quanlychitieunhom.ClientConfig.RetrofitClientInstance;
import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.list_giaodich;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;
import vn.edu.uit.quanlychitieunhom.Services.Image_Service;

public class Util {
    SimpleDateFormat dateFormat;
    NumberFormat numberFormat = new DecimalFormat("#,###,###");
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public String DateStringByFormat(Date date,String format ){
        dateFormat = new SimpleDateFormat(format);
        String temp =  dateFormat.format(date);
        if(temp.isEmpty()){
            return "";
        }
        return temp;
    }

    public Date StringToDate(String dateString, String format ) throws ParseException {
        return new SimpleDateFormat(format).parse(dateString);
    }

    public String DoubleToStringByFormat(Double num, String format){
        numberFormat = new DecimalFormat(format);
        String temp = numberFormat.format(num);
        if(temp.isEmpty()){
            return "null";
        }
        return temp;
    }

    public String IntegerToStringByFormat(int num, String format){
        numberFormat = new DecimalFormat(format);
        String temp = numberFormat.format(num);
        if(temp.isEmpty()){
            return "null";
        }
        return temp;
    }



    public List<list_giaodich> deployKyChiTieu(List<giaodich> inputGiaoDich){
        List<list_giaodich> GiaoDichTheoNgay = new ArrayList<>();
        List<Date> label_Ngay = new ArrayList<>();
        for (giaodich item: inputGiaoDich) {
            label_Ngay.add(item.getNgaygiaodich());
        }

        Set<Date> unique = new HashSet<Date>(label_Ngay);


        for (Date ngay: unique) {
            list_giaodich temp = new list_giaodich(ngay);
            GiaoDichTheoNgay.add(temp);
        }

        for (list_giaodich e: GiaoDichTheoNgay) {
            for (giaodich item: inputGiaoDich) {
                if(e.getNgaygiaodich().equals( item.getNgaygiaodich())){
                    e.addToDanhsachchitieu(item);
                }
            }
        }
        sortNgayGiaoDich(GiaoDichTheoNgay);
        return GiaoDichTheoNgay;
    }

    public void sortNgayGiaoDich(List<list_giaodich> GiaoDichTheoNgay){
        for (list_giaodich obj1: GiaoDichTheoNgay) {
            for (list_giaodich obj2:GiaoDichTheoNgay) {
                if(obj1.getNgaygiaodich().compareTo(obj2.getNgaygiaodich()) > 0){
                    list_giaodich temp = new list_giaodich(obj2.getNgaygiaodich(),obj2.getDanhsachchitieu());
                    obj2.parse(obj1);
                    obj1.parse(temp);
                }
            }
        }
    }


    public boolean checkMailFormat(String email){
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    public taikhoan getUserLocalStorage(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String exist_user = sharedPref.getString("user","");
        Gson gson = new Gson();
        return gson.fromJson(exist_user, taikhoan.class);
    }

    public void setUserLocalStorage(Context context,taikhoan user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // taikhoan - instance of taikhoan
        editor.putString("user",json);
        editor.apply();
    }

    public void setFlagNewGiaoDich(Context context,boolean flag,int makychitieu){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(new FlagNewGiaoDich(flag,makychitieu));
        editor.putString("new_giaodich_flag",json);
        editor.apply();
    }

    public FlagNewGiaoDich getFlagNewGiaoDich(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String flag = sharedPref.getString("new_giaodich_flag","");
        if(flag.equals("")) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(flag, FlagNewGiaoDich.class);
    }



    public class FlagNewGiaoDich{
        boolean flag;
        int makychitieu;

        public FlagNewGiaoDich(boolean flag, int makychitieu) {
            this.flag = flag;
            this.makychitieu = makychitieu;
        }

        public boolean isFlag() { return flag; }
        public void setFlag(boolean flag) { this.flag = flag; }
        public int getMakychitieu() { return makychitieu; }
        public void setMakychitieu(int makychitieu) { this.makychitieu = makychitieu; }
    }





    public void setFlagNewKyChiTieu(Context context,boolean flag,int manhom){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(new FlagNewKyChiTieu(flag,manhom));
        editor.putString("new_kychitieu_flag",json);
        editor.apply();
    }

    public FlagNewKyChiTieu getFlagNewKyChiTieu(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String flag = sharedPref.getString("new_kychitieu_flag","");
        if(flag.equals("")) {
            return new FlagNewKyChiTieu();
        }
        Gson gson = new Gson();
        return gson.fromJson(flag, FlagNewKyChiTieu.class);
    }



    public class FlagNewKyChiTieu{
        boolean flag;
        int manhom;

        public FlagNewKyChiTieu(){
            this.flag = false;
            this.manhom =0;
        }

        public FlagNewKyChiTieu(boolean flag, int manhom) {
            this.flag = flag;
            this.manhom = manhom;
        }

        public boolean isFlag() {
            return flag;
        }
        public void setFlag(boolean flag) {this.flag = flag;}
        public int getManhom() {return manhom;}
        public void setManhom(int manhom) {this.manhom = manhom;}

        @Override
        public String toString() {
            return "FlagNewKyChiTieu{" +
                    "flag=" + flag +
                    ", manhom=" + manhom +
                    '}';
        }
    }


    public void getImageByURL(final Context context, String url, final ImageView imageView){
        try {
            Image_Service service = RetrofitClientInstance.getRetrofitInstance().create(Image_Service.class);
            Call<ResponseBody> call = service.getImage(url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //TODO:display the image data in a ImageView or save it
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            imageView.setImageBitmap(bmp);

                        } else {
                            imageView.setImageBitmap(null);
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    imageView.setImageBitmap(null);
//                    Toast.makeText(context,"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            imageView.setImageBitmap(null);
            Log.d("Test", "Exception");
        }
    }


    public void getImageUser(final Context context, String url){
        try {
            Image_Service service = RetrofitClientInstance.getRetrofitInstance().create(Image_Service.class);
            Call<ResponseBody> call = service.getImage(url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            // display the image data in a ImageView or save it
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            setImageUserLocalStorage(context,bmp);
                        } else {
                            // TODO: GET IMAGE DEFAULT
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context,"Có lỗi xảy ra. Vui lòng thao tác lại sau!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Test", "Exception");
        }
    }


    public void setImageUserLocalStorage(Context context,Bitmap bitmap){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("image_user",encodeTobase64(bitmap));
        editor.apply();
    }


    //TODO: method for bitmap to base64
    public String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    //TODO: method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public Bitmap getImageUserLocalStorage(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String string_img_user = sharedPref.getString("image_user","");
        if(string_img_user.equals("")){
            return null;
        }
        return decodeBase64(string_img_user);
    }


    public void setFlagEditNhom(Context context,boolean bool){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("flag_edit_nhom",bool);
        editor.apply();
    }

    public boolean getFlagEditNhom(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean("flag_edit_nhom",false);
    }


}
