package vn.edu.uit.quanlychitieunhom.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class giaodich {
    @SerializedName("magiaodich")
    private int magiaodich;
    @SerializedName("ngaygiaodich")
    private Date ngaygiaodich;
    @SerializedName("sotien")
    private Double sotien;
    @SerializedName("ghichu")
    private String ghichu;
    @SerializedName("anhhoadon")
    private String anhhoadon;
    @SerializedName("maloaigiaodich")
    private int maloaigiaodich;
    @SerializedName("tentaikhoan")
    private String tentaikhoan;
    @SerializedName("manhomchitieu")
    private int manhomchitieu;
    @SerializedName("nhomchitieu")
    private nhomchitieu nhomchitieu;
    @SerializedName("taikhoan")
    private taikhoan taikhoan;
    @SerializedName("loaigiaodich")
    private  loaigiaodich loaigiaodich;

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

    public giaodich(int magiaodich, Date ngaygiaodich, Double sotien, String ghichu, String anhhoadon, vn.edu.uit.quanlychitieunhom.Models.nhomchitieu nhomchitieu, vn.edu.uit.quanlychitieunhom.Models.taikhoan taikhoan, vn.edu.uit.quanlychitieunhom.Models.loaigiaodich loaigiaodich) {
        this.magiaodich = magiaodich;
        this.ngaygiaodich = ngaygiaodich;
        this.sotien = sotien;
        this.ghichu = ghichu;
        this.anhhoadon = anhhoadon;
        this.nhomchitieu = nhomchitieu;
        this.taikhoan = taikhoan;
        this.loaigiaodich = loaigiaodich;
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

    public vn.edu.uit.quanlychitieunhom.Models.nhomchitieu getNhomchitieu() {
        return nhomchitieu;
    }

    public vn.edu.uit.quanlychitieunhom.Models.taikhoan getTaikhoan() {
        return taikhoan;
    }

    public vn.edu.uit.quanlychitieunhom.Models.loaigiaodich getLoaigiaodich() {
        return loaigiaodich;
    }

    public void setNhomchitieu(vn.edu.uit.quanlychitieunhom.Models.nhomchitieu nhomchitieu) {
        this.nhomchitieu = nhomchitieu;
    }

    public void setTaikhoan(vn.edu.uit.quanlychitieunhom.Models.taikhoan taikhoan) {
        this.taikhoan = taikhoan;
    }

    public void setLoaigiaodich(vn.edu.uit.quanlychitieunhom.Models.loaigiaodich loaigiaodich) {
        this.loaigiaodich = loaigiaodich;
    }

    @Override
    public String toString() {
        String str = "";
        str =   "giaodich{" +
                "magiaodich=" + magiaodich +
                ", ngaygiaodich=" + ngaygiaodich +
                ", sotien=" + sotien +
                ", ghichu='" + ghichu + '\'' +
                ", anhhoadon='" + anhhoadon + '\'' +
                ", nhomchitieu=" + nhomchitieu.toString();
        if(this.taikhoan != null)
            str += "taikhoan='" + this.taikhoan.toString() + '\'';
        if(this.loaigiaodich != null)
            str += "loaigiaodich" + this.loaigiaodich.toString() +'\'';
        return str+'}';
    }
}
