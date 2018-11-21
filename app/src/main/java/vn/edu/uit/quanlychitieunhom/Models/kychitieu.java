package vn.edu.uit.quanlychitieunhom.Models;

import java.util.Date;

public class kychitieu {
    private int makychitieu;
    private String tenkychitieu;
    private Date tungay;
    private Date denngay;
    private Double hanmucchitieu;
    private int manhomchitieu;

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
}
