package vn.edu.uit.quanlychitieunhom.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.uit.quanlychitieunhom.Models.giaodich;
import vn.edu.uit.quanlychitieunhom.Models.list_giaodich;

public class Util {
    SimpleDateFormat dateFormat;
    NumberFormat numberFormat = new DecimalFormat("#,###,###");

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
        List<String> label_Ngay = new ArrayList<>();
        label_Ngay = getArrLabel_Ngay(inputGiaoDich);

        for (String ngay: label_Ngay) {
            
        }

        return GiaoDichTheoNgay;
    }

    public List<String> getArrLabel_Ngay(List<giaodich> inputGiaoDich) {
        List<String> label_Ngay = new ArrayList<>();
        boolean exist;
        for (giaodich item : inputGiaoDich) {
            for (String label : label_Ngay) {
                if (DateStringByFormat(item.getNgaygiaodich(), "MM yyyy") == label) {
                    continue;
                }
                label_Ngay.add(DateStringByFormat(item.getNgaygiaodich(), "MM yyyy"));
                continue;
            }
        }
        return label_Ngay;
    }

}
