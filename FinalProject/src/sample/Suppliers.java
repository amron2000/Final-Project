package sample;

public class Suppliers {
    private Integer id;
    private String supplier;

    public Suppliers() {
    }

    public Suppliers(Integer id, String supplier) {
        this.id = id;
        this.supplier = supplier;
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

    @Override
    public String toString() {
        return String.format("%-5s %-10s %-10s %8.2f", id, supplier);
    }
}
