package vn.edu.uit.quanlychitieunhom.Models;

public class list_thongke {

    private String tennhom;
    private String tongtienchi;

    public list_thongke(String tennhom, String tongtienchi) {
        this.tennhom = tennhom;
        this.tongtienchi = tongtienchi;
    }

    public String getTennhom() {
        return tennhom;
    }

    public void setTennhom(String tennhom) {
        this.tennhom = tennhom;
    }

    public String getTongtienchi() {
        return tongtienchi;
    }

    public void setTongtienchi(String tongtienchi) {
        this.tongtienchi = tongtienchi;
    }
}
