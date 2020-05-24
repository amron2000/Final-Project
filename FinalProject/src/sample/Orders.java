package sample;

import java.util.Date;

public class Orders {
    private Integer id;
    private String customer;
    private String productName;
    private Integer numberShipped;
    private Date date;

    public Orders() {
    }

    public Orders(Integer id, String customer, String productName, Integer numberShipped,Date date) {
        this.id = id;
        this.customer = customer;
        this.productName = productName;
        this.numberShipped = numberShipped;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getNumberShipped() {
        return numberShipped;
    }

    public void setNumberShipped(Integer numberShipped) {
        this.numberShipped = numberShipped;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%-5s %-10s %-10s %8.2f", id, customer, productName, numberShipped,date);
    }
}
