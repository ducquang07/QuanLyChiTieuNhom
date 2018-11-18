package vn.edu.uit.quanlychitieunhom.model;

import java.util.Date;

public class giaodich {
    private int magiaodich;
    private Date ngaygiaodich;
    private Double sotien;
    private String ghichu;
    private String anhhoadon;
    private int maloaigiaodich;
    private String tentaikhoan;
    private int manhomchitieu;

    public giaodich() {
    }

    public giaodich(int magiaodich, Date ngaygiaodich, Double sotien, String ghichu, String anhhoadon, int maloaigiaodich, String tentaikhoan, int manhomchitieu) {
        this.magiaodich = magiaodich;
        this.ngaygiaodich = ngaygiaodich;
        this.sotien = sotien;
        this.ghichu = ghichu;
        this.anhhoadon = anhhoadon;
        this.maloaigiaodich = maloaigiaodich;
        this.tentaikhoan = tentaikhoan;
        this.manhomchitieu = manhomchitieu;
    }

    public int getMagiaodich() {
        return magiaodich;
    }

    public void setMagiaodich(int magiaodich) {
        this.magiaodich = magiaodich;
    }

    public Date getNgaygiaodich() {
        return ngaygiaodich;
    }

    public void setNgaygiaodich(Date ngaygiaodich) {
        this.ngaygiaodich = ngaygiaodich;
    }

    public Double getSotien() {
        return sotien;
    }

    public void setSotien(Double sotien) {
        this.sotien = sotien;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getAnhhoadon() {
        return anhhoadon;
    }

    public void setAnhhoadon(String anhhoadon) {
        this.anhhoadon = anhhoadon;
    }

    public int getMaloaigiaodich() {
        return maloaigiaodich;
    }

    public void setMaloaigiaodich(int maloaigiaodich) {
        this.maloaigiaodich = maloaigiaodich;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public int getManhomchitieu() {
        return manhomchitieu;
    }

    public void setManhomchitieu(int manhomchitieu) {
        this.manhomchitieu = manhomchitieu;
    }

}
