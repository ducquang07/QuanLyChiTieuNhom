package vn.edu.uit.quanlychitieunhom.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class list_giaodich {
    private Date ngaygiaodich;
    private List<giaodich> danhsachchitieu;

    public list_giaodich(Date ngaygiaodich, List<giaodich> danhsachchitieu) {
        this.ngaygiaodich = ngaygiaodich;
        this.danhsachchitieu = danhsachchitieu;
    }

    public list_giaodich(Date ngaygiaodich) {
        this.ngaygiaodich = ngaygiaodich;
        this.danhsachchitieu = new ArrayList<>();
    }

    public Date getNgaygiaodich() {
        return ngaygiaodich;
    }

    public void setNgaygiaodich(Date ngaygiaodich) {
        this.ngaygiaodich = ngaygiaodich;
    }

    public List<giaodich> getDanhsachchitieu() {
        return danhsachchitieu;
    }

    public void setDanhsachchitieu(List<giaodich> danhsachchitieu) {
        this.danhsachchitieu = danhsachchitieu;
    }

    public int TongTien (){
        int tongtien = 0;
        for (giaodich e : danhsachchitieu) {
            tongtien += e.getSotien();
        }
        return tongtien;
    }

    public void addToDanhsachchitieu(giaodich giaodich){
        this.danhsachchitieu.add(giaodich);
    }

    @Override
    public String toString() {
        return "list_giaodich{" +
                "ngaygiaodich=" + ngaygiaodich +
                ", danhsachchitieu=" + danhsachchitieu +
                '}';
    }

    public void parse ( list_giaodich other ){
        this.ngaygiaodich = other.getNgaygiaodich();
        this.danhsachchitieu = other.getDanhsachchitieu();
    }


}
