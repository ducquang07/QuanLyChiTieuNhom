package vn.edu.uit.quanlychitieunhom.Models;

public class thanhviennhom {
    private String tentaikhoan;
    private int manhomchitieu;

    public thanhviennhom() {
    }

    public thanhviennhom(String tentaikhoan, int manhomchitieu) {
        this.tentaikhoan = tentaikhoan;
        this.manhomchitieu = manhomchitieu;
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
