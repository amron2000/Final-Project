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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OutgoingOrders implements Initializable {

    @FXML
    private Pane rootpane;

    @FXML
    private TableView<Orders> tableView;

    @FXML
    private TableColumn<Orders, Integer> tbColId;

    @FXML
    private TableColumn<Orders, Date> tbColDate;

    @FXML
    private TableColumn<Orders, String> tbColProduct;

    @FXML
    private TableColumn<Orders, Integer> tbColShipped;

    @FXML
    private TableColumn<Orders, String> tbColCustomer;

    @FXML
    private Button buttonCurrent;

    @FXML
    private Button buttonPurchases;

    @FXML
    private TextField txFiShipped;

    @FXML
    private ChoiceBox<String> txFiCustomer;

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
        tbColShipped.setCellValueFactory(new PropertyValueFactory("numberShipped"));
        tbColCustomer.setCellValueFactory(new PropertyValueFactory("customer"));
        try {
            productgetName();
            customergetName();
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
            product.setProductName(rs2.getString("ProductName"));
            String save = product.getProductName();
            list.add(save);
        }
        txFiProduct.setItems(FXCollections.observableArrayList(list));
    }

    private void customergetName() throws SQLException {
        ResultSet rs2 = this.statement.executeQuery("Select customer From orders");
        List<String> list = new ArrayList<>();
        while(rs2.next()){
            Orders order = new Orders();
            order.setCustomer(rs2.getString("customer"));
            String save = order.getCustomer();
            list.add(save);
        }
        txFiCustomer.setItems(FXCollections.observableArrayList(list));
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void showSelectedproducts(){
        Orders orders = tableView.getSelectionModel().getSelectedItem();
        if(orders != null){
            idUpdate = orders.getId();
            txFiShipped.setText(String.valueOf(orders.getNumberShipped()));
            txFiProduct.setValue(orders.getProductName());
            txFiCustomer.setValue(orders.getCustomer());
            Date newdate = orders.getDate();
            LocalDate localdate =  convertToLocalDateViaInstant(newdate);
            txFiDate.setValue(localdate);
        }
    }



    @FXML
    void buttonHandleAdd() throws Exception {
        String customer = String.valueOf(txFiCustomer.getValue());
        String product = String.valueOf(txFiProduct.getValue());
        Integer shipped = Integer.parseInt(txFiShipped.getText());
        String date = String.valueOf(txFiDate.getValue());
        String sql = "INSERT INTO orders (ProductName, customer ,NumberShipped , OrderDate)" +
                " values(" + "'" + product + "','" +  customer + "',"
                 + shipped + ",'" + date +  "')";
        this.statement.executeUpdate(sql);
        show();
    }

    @FXML
    void buttonHandleDelete() throws Exception {
        int id = tableView.getSelectionModel().getSelectedItem().getId();
        String sql = ("DELETE FROM orders WHERE id =" + id );
        this.statement.executeUpdate(sql);
        resetText();
        show();
    }
    private void resetText(){
        //  txFiDate.;
        txFiProduct.setValue("");
        txFiShipped.setText("");
        txFiCustomer.setValue("");
    }

    @FXML
    void buttonHandleEdit() throws Exception {
        String date = String.valueOf(txFiDate.getValue());
        String product = String.valueOf(txFiProduct.getValue());
        Integer shipped = Integer.parseInt(txFiShipped.getText());
        String customer = String.valueOf(txFiCustomer.getValue());
        String sql = "Update orders Set ProductName='" + product +
                "' customer=" + "'" +  customer + "', NumberShipped=" +
                shipped + ", OrderDate= '" + date +
                "' Where id=" + idUpdate;
        this.statement.executeUpdate(sql);
        resetText();
        show();
    }

    @FXML
    void buttonHandleView() throws Exception {
        show();
    }
    public void show() throws Exception{
        ResultSet rs = this.statement.executeQuery("Select * From orders");
        tableView.getItems().clear();
        while(rs.next()) {
            Orders orders = new Orders();
            orders.setId(rs.getInt("id"));
            orders.setCustomer(rs.getString("customer"));
            orders.setProductName(rs.getString("ProductName"));
            orders.setNumberShipped(rs.getInt("NumberShipped"));
            orders.setDate(rs.getDate("OrderDate"));
            tableView.getItems().add(orders);
        }
    }

    @FXML
    void buttonHandleCurrent() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("fxml/current.fxml"));
        rootpane.getChildren().setAll(pane);
    }

    @FXML
    void buttonHandlePurchases() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("fxml/purchase.fxml"));
        rootpane.getChildren().setAll(pane);
    }

    @FXML
    void ButtonExit() {
        System.exit(1);
    }

}
