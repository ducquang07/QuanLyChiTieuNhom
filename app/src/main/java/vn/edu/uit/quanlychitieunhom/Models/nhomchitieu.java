package vn.edu.uit.quanlychitieunhom.Models;

import com.google.gson.annotations.SerializedName;

public class nhomchitieu {

    @SerializedName("manhomchitieu")
    private Integer manhomchitieu;
    @SerializedName("tennhomchitieu")
    private String tennhomchitieu;
    @SerializedName("quy")
    private Double quy;

    public nhomchitieu() {
    }

    public nhomchitieu(Integer manhomchitieu, String tennhomchitieu, Double quy) {
        this.manhomchitieu = manhomchitieu;
        this.tennhomchitieu = tennhomchitieu;
        this.quy = quy;
    }

    public nhomchitieu(String tennhomchitieu, Double quy) {
        this.tennhomchitieu = tennhomchitieu;
        this.quy = quy;
    }

    public Integer getManhomchitieu() {
        return manhomchitieu;
    }

    public void setManhomchitieu(Integer manhomchitieu) {
        this.manhomchitieu = manhomchitieu;
    }

    public String getTennhomchitieu() {
        return tennhomchitieu;
    }

    public void setTennhomchitieu(String tennhomchitieu) {
        this.tennhomchitieu = tennhomchitieu;
    }

    public Double getQuy() {
        return quy;
    }

    public void setQuy(Double quy) {
        this.quy = quy;
    }

    @Override
    public String toString() {
        return "nhomchitieu{" +
                "manhomchitieu=" + manhomchitieu +
                ", tennhomchitieu='" + tennhomchitieu + '\'' +
                ", quy=" + quy +
                '}';
    }
}
