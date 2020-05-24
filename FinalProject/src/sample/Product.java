package sample;

public class Product {
    private String ProductName;
    private Integer starting;
    private Integer received;
    private Integer shipped;
    private Integer onHand;

    public Product() {
    }

    public Product(String ProductName, Integer starting, Integer received,Integer shipped ,Integer onHand) {
        this.ProductName = ProductName;
        this.starting = starting;
        this.received = received;
        this.shipped = shipped;
        this.onHand = onHand;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public Integer getStarting() {
        return starting;
    }

    public void setStarting(Integer starting) {
        this.starting = starting;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Integer getShipped() {
        return shipped;
    }

    public void setShipped(Integer shipped) {
        this.shipped = shipped;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
    }

    @Override
    public String toString() {
        return String.format("%-5s %-10s %-10s %8.2f", ProductName, starting, received,shipped,onHand);
    }
}
