package vn.edu.uit.quanlychitieunhom.Utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

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

import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.list_giaodich;
import vn.edu.uit.quanlychitieunhom.Models.taikhoan;

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

}
