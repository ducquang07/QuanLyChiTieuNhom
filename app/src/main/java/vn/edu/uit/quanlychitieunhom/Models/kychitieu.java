package vn.edu.uit.quanlychitieunhom.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class kychitieu {
    @SerializedName("makychitieu")
    private int makychitieu;
    @SerializedName("tenkichitieu")
    private String tenkychitieu;
    @SerializedName("tungay")
    private Date tungay;
    @SerializedName("denngay")
    private Date denngay;
    @SerializedName("hanmucchitieu")
    private Double hanmucchitieu;
    @SerializedName("manhomchitieu")
    private int manhomchitieu;
    @SerializedName("nhomchitieu")
    private nhomchitieu nhomchitieu;


    public kychitieu() {
    }

    public kychitieu(int makychitieu, String tenkychitieu, Date tungay, Date denngay, Double hanmucchitieu, int manhomchitieu) {
        this.makychitieu = makychitieu;
        this.tenkychitieu = tenkychitieu;
        this.tungay = tungay;
        this.denngay = denngay;
        this.hanmucchitieu = hanmucchitieu;
        this.manhomchitieu = manhomchitieu;
    }

    public kychitieu(String tenkychitieu, Date tungay, Date denngay, Double hanmucchitieu, vn.edu.uit.quanlychitieunhom.Models.nhomchitieu nhomchitieu) {
        this.tenkychitieu = tenkychitieu;
        this.tungay = tungay;
        this.denngay = denngay;
        this.hanmucchitieu = hanmucchitieu;
        this.nhomchitieu = nhomchitieu;
        this.manhomchitieu = nhomchitieu.getManhomchitieu();
    }

    public int getMakychitieu() {
        return makychitieu;
    }

    public void setMakychitieu(int makychitieu) {
        this.makychitieu = makychitieu;
    }

    public String getTenkychitieu() {
        return tenkychitieu;
    }

    public void setTenkychitieu(String tenkychitieu) {
        this.tenkychitieu = tenkychitieu;
    }

    public Date getTungay() {
        return tungay;
    }

    public void setTungay(Date tungay) {
        this.tungay = tungay;
    }

    public Date getDenngay() {
        return denngay;
    }

    public void setDenngay(Date denngay) {
        this.denngay = denngay;
    }

    public Double getHanmucchitieu() {
        return hanmucchitieu;
    }

    public void setHanmucchitieu(Double hanmucchitieu) {
        this.hanmucchitieu = hanmucchitieu;
    }

    public int getManhomchitieu() {
        return manhomchitieu;
    }

    public void setManhomchitieu(int manhomchitieu) {
        this.manhomchitieu = manhomchitieu;
    }

    public nhomchitieu getNhomchitieu() {
        return nhomchitieu;
    }

    public void setNhomchitieu(nhomchitieu nhomchitieu) {
        this.nhomchitieu = nhomchitieu;
    }


    @Override
    public String toString() {
        return "kychitieu{" +
                "makychitieu=" + makychitieu +
                ", tenkychitieu='" + tenkychitieu + '\'' +
                ", tungay=" + tungay +
                ", denngay=" + denngay +
                ", hanmucchitieu=" + hanmucchitieu +
                ", manhomchitieu=" + manhomchitieu +
                ", nhomchitieu=" + nhomchitieu +
                '}';
    }
}
