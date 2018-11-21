package vn.edu.uit.quanlychitieunhom.Models;

import java.util.Date;
import java.util.List;

public class list_giaodich {
    private Date ngaygiaodich;
    private List<giaodich> danhsachchitieu;

    public list_giaodich(Date ngaygiaodich, List<giaodich> danhsachchitieu) {
        this.ngaygiaodich = ngaygiaodich;
        this.danhsachchitieu = danhsachchitieu;
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



}
