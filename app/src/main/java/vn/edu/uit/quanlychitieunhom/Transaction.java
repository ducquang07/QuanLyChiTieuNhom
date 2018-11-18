package vn.edu.uit.quanlychitieunhom;

public class Transaction {
    private int id;
    private String date;
    private String transaction_name;
    private int charge;

    public Transaction(int id, String date, String transaction_name, int charge) {
        this.id = id;
        this.date = date;
        this.transaction_name = transaction_name;
        this.charge = charge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction_name() {
        return transaction_name;
    }

    public void setTransaction_name(String transaction_name) {
        this.transaction_name = transaction_name;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }
}
