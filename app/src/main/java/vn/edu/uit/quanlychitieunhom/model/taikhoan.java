package vn.edu.uit.quanlychitieunhom.model;

import java.util.Date;

public class taikhoan {
    private String tentaikhoan;
    private String matkhau;
    private String tennguoidung;
    private Date ngaysinh;
    private String gioitinh;
    private String sodienthoai;
    private String email;
    private Date ngaydangky;
    private String avatar;

    public taikhoan() {
    }

    public taikhoan(String tentaikhoan, String matkhau, String tennguoidung, Date ngaysinh, String gioitinh, String sodienthoai, String email, Date ngaydangky, String avatar) {
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
        this.tennguoidung = tennguoidung;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.sodienthoai = sodienthoai;
        this.email = email;
        this.ngaydangky = ngaydangky;
        this.avatar = avatar;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaydangky() {
        return ngaydangky;
    }

    public void setNgaydangky(Date ngaydangky) {
        this.ngaydangky = ngaydangky;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
