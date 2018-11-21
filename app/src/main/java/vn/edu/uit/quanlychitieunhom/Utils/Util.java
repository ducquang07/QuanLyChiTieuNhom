package vn.edu.uit.quanlychitieunhom.Utils;



import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.list_giaodich;

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

    public String DoubleToStringByFormat(Double num, String format){
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

}
