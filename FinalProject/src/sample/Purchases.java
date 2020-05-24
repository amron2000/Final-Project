package sample;

import java.time.LocalDate;
import java.util.Date;

public class Purchases {
    private Integer id;
    private String supplier;
    private String productName;
    private Integer numberReceived;
    private Date date;

    public Purchases() {
    }

    public Purchases(Integer id, String supplier, String productName, Integer numberReceived,Date date) {
        this.id = id;
        this.supplier = supplier;
        this.productName = productName;
        this.numberReceived = numberReceived;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getNumberReceived() {
        return numberReceived;
    }

    public void setNumberReceived(Integer numberReceived) {
        this.numberReceived = numberReceived;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%-5s %-10s %-10s %8.2f", id, supplier, productName, numberReceived,date);
    }
}
