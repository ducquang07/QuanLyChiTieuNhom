package vn.edu.uit.quanlychitieunhom;

import java.util.Date;

public class Taikhoan {

    private String tentaikhoan;
    private String tennguoidung;
    private String gioitinh;
    private String ngaysinh;
    private String sodienthoai;
    private String ngaydangki;
    private String email;

    public Taikhoan(String tentaikhoan, String tennguoidung, String gioitinh, String ngaysinh, String sodienthoai, String ngaydangki, String email) {
        this.tentaikhoan = tentaikhoan;
        this.tennguoidung = tennguoidung;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.sodienthoai = sodienthoai;
        this.ngaydangki = ngaydangki;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getNgaydangki() {
        return ngaydangki;
    }

    public void setNgaydangki(String ngaydangki) {
        this.ngaydangki = ngaydangki;
    }
}
