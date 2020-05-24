package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;


public class IncomingPurchases implements Initializable {

    @FXML
    private Pane rootpane;

    @FXML
    private TableView<Purchases> tableView;

    @FXML
    private TableColumn<Purchases, Integer> tbColId;

    @FXML
    private TableColumn<Purchases, String> tbColProduct;

    @FXML
    private TableColumn<Purchases, Integer> tbColReceived;

    @FXML
    private TableColumn<Purchases, Date> tbColDate;

    @FXML
    private TableColumn<Purchases, String> tbColSupplier;

    @FXML
    private Button buttonCurrent;

    @FXML
    private Button buttonOrders;

    @FXML
    private TextField txFiReceived;

    @FXML
    private ChoiceBox<String> txFiSupplier;

    @FXML
    private DatePicker txFiDate;

    @FXML
    private ChoiceBox<String> txFiProduct;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonView;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button ExitButton;

    Statement statement;
    Integer idUpdate;


    public void initialize(URL url, ResourceBundle rb){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.
                            getConnection("jdbc:mysql://127.0.0.1:3306/inventory?serverTimezone=UTC",
                                    "root","");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error in DataBase Connection");
        }
        tbColId.setCellValueFactory(new PropertyValueFactory("id"));
        tbColDate.setCellValueFactory(new PropertyValueFactory("date"));
        tbColProduct.setCellValueFactory(new PropertyValueFactory("productName"));
        tbColReceived.setCellValueFactory(new PropertyValueFactory("numberReceived"));
        tbColSupplier.setCellValueFactory(new PropertyValueFactory("supplier"));
        try {
            productgetName();
            suppliergetName();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableView.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedproducts());

    }

    private void productgetName() throws SQLException {
        ResultSet rs2 = this.statement.executeQuery("Select Product From Products");
        List<String> list = new ArrayList<>();
        while(rs2.next()){
            Product product = new Product();
            product.setProductName(rs2.getString("Product"));
            String save = product.getProductName();
            list.add(save);
        }
        txFiProduct.setItems(FXCollections.observableArrayList(list));
    }

    private void suppliergetName() throws SQLException {
        ResultSet rs2 = this.statement.executeQuery("Select supplier From suppliers");
        List<String> list = new ArrayList<>();
        while(rs2.next()){
            Suppliers Supplier = new Suppliers();
            Supplier.setSupplier(rs2.getString("supplier"));
            String save = Supplier.getSupplier();
            list.add(save);
        }
        txFiSupplier.setItems(FXCollections.observableArrayList(list));
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void showSelectedproducts(){
        Purchases purchases = tableView.getSelectionModel().getSelectedItem();
        if(purchases != null){
            idUpdate = purchases.getId();
            txFiReceived.setText(String.valueOf(purchases.getNumberReceived()));
            txFiProduct.setValue(purchases.getProductName());
            txFiSupplier.setValue(purchases.getSupplier());
            Date newdate = purchases.getDate();
            LocalDate localdate =  convertToLocalDateViaInstant(newdate);
            txFiDate.setValue(localdate);
        }
    }



    @FXML
    void buttonHandleAdd() throws Exception {
        String supplier = String.valueOf(txFiSupplier.getValue());
        String product = String.valueOf(txFiProduct.getValue());
        Integer received = Integer.parseInt(txFiReceived.getText());
        String date = String.valueOf(txFiDate.getValue());
        String sql = "INSERT INTO purchases (ProductName, SupplierName ,NumberReceived , PurchaseDate)" +
                " values(" + "'" + product + "'," + "'" + supplier + "',"
                + received + ",'" + date +  "')";
        this.statement.executeUpdate(sql);
        show();
    }

    @FXML
    void buttonHandleDelete() throws Exception {
        int id = tableView.getSelectionModel().getSelectedItem().getId();
        String sql = ("DELETE FROM purchases WHERE id =" + id );
        this.statement.executeUpdate(sql);
        resetText();
        show();
    }

    @FXML
    void buttonHandleEdit() throws Exception {
        String date = String.valueOf(txFiDate.getValue());
        String product = String.valueOf(txFiProduct.getValue());
        Integer received = Integer.parseInt(txFiReceived.getText());
        String supplier = String.valueOf(txFiSupplier.getValue());
        String sql = "Update purchases Set ProductName='" + product + "', SupplierName=" + "'" +
                supplier + "', NumberReceived=" + received + ", " +
                "PurchaseDate=" + "'" + date +
                "' Where id=" + idUpdate;
        this.statement.executeUpdate(sql);
        resetText();
        show();
    }
    private void resetText(){
      //  txFiDate.;
        txFiProduct.setValue("");
        txFiReceived.setText("");
        txFiSupplier.setValue("");
    }

    @FXML
    void buttonHandleView() throws Exception {
        show();
//        show2();
    }
    public void show() throws Exception{
        ResultSet rs = this.statement.executeQuery("Select * From purchases");
        tableView.getItems().clear();
        while(rs.next()) {
            Purchases purchase = new Purchases();
            purchase.setId(rs.getInt("id"));
            purchase.setProductName(rs.getString("ProductName"));
            purchase.setSupplier(rs.getString("SupplierName"));
            purchase.setNumberReceived(rs.getInt("NumberReceived"));
            purchase.setDate(rs.getDate("PurchaseDate"));
            tableView.getItems().add(purchase);
        }
    }

//    public void show2() throws Exception {
//        ResultSet rs2 = this.statement.executeQuery("Select ProductName From Products");
//        Purchases purchase = new Purchases();
//        tableView.getItems().clear();
//        while (rs2.next()) {
//            purchase.setProductName(rs2.getString("ProductName"));
//            tableView.getItems().add(purchase);
//        }
//          }

    @FXML
    void buttonHandleCurrent() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("fxml/current.fxml"));
        rootpane.getChildren().setAll(pane);
    }

    @FXML
    void buttonHandleOrders() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("fxml/orders.fxml"));
        rootpane.getChildren().setAll(pane);
    }

    @FXML
    void ButtonExit() {
        System.exit(1);
    }


    }


