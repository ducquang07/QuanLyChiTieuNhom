package vn.edu.uit.quanlychitieunhom.Models;

public class loaigiaodich {
    private int maloaigiaodich;
    private String tenloaigiaodich;
    private String nhom;

    public loaigiaodich() {
    }

    public loaigiaodich(int maloaigiaodich, String tenloaigiaodich, String nhom) {
        this.maloaigiaodich = maloaigiaodich;
        this.tenloaigiaodich = tenloaigiaodich;
        this.nhom = nhom;
    }

    public int getMaloaigiaodich() {
        return maloaigiaodich;
    }

    public void setMaloaigiaodich(int maloaigiaodich) {
        this.maloaigiaodich = maloaigiaodich;
    }

    public String getTenloaigiaodich() {
        return tenloaigiaodich;
    }

    public void setTenloaigiaodich(String tenloaigiaodich) {
        this.tenloaigiaodich = tenloaigiaodich;
    }

    public String getNhom() {
        return nhom;
    }

    public void setNhom(String nhom) {
        this.nhom = nhom;
    }
}
