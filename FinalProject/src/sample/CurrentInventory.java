package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;


public class CurrentInventory implements Initializable {

    @FXML
    private Pane rootpane;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> tbColName;

    @FXML
    private TableColumn<Product, Integer> tbColStarting;

    @FXML
    private TableColumn<Product, Integer> tbColReceived;

    @FXML
    private TableColumn<Product, Integer> tbColShipped;

    @FXML
    private TableColumn<Product, Integer> tbColOnHand;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonView;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField txFiName;

    @FXML
    private TextField txFiOnHand;

    @FXML
    private TextField txFiShipped;

    @FXML
    private TextField txFiReceived;

    @FXML
    private TextField txFiStarting;

    @FXML
    private Button pagePurchases;

    @FXML
    private Button pageOrders;

    @FXML
    private Button ExitButton;

    Statement statement;
   // private static final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String regex = "^[A-Za-z+_.-]+-[A-Za-z0-9.-]+$";

    public void initialize(URL url, ResourceBundle rb){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.
                            getConnection("jdbc:mysql://127.0.0.1:3306/inventory?serverTimezone=UTC",
                                    "root","");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbColName.setCellValueFactory(new PropertyValueFactory("ProductName"));
        tbColStarting.setCellValueFactory(new PropertyValueFactory("starting"));
        tbColReceived.setCellValueFactory(new PropertyValueFactory("received"));
        tbColShipped.setCellValueFactory(new PropertyValueFactory("shipped"));
        tbColOnHand.setCellValueFactory(new PropertyValueFactory("onHand"));
        tableView.getSelectionModel().selectedItemProperty().addListener(
                event-> showSelectedproducts());
    }

    private void showSelectedproducts(){
        Product product = tableView.getSelectionModel().getSelectedItem();
        if(product != null){
            txFiName.setText(product.getProductName());
            txFiStarting.setText(String.valueOf(product.getStarting()));
            txFiReceived.setText(String.valueOf(product.getReceived()));
            txFiShipped.setText(String.valueOf(product.getShipped()));
            txFiOnHand.setText(String.valueOf(product.getOnHand()));
        }
    }


    @FXML
    void buttonHandleAdd() throws Exception {
        String name = txFiName.getText();
        Integer start = Integer.parseInt(txFiStarting.getText());
        Integer received = Integer.parseInt(txFiReceived.getText());
        Integer shipped = Integer.parseInt(txFiShipped.getText());
        Integer hand = Integer.parseInt(txFiOnHand.getText());
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        matcher.matches();
        if(matcher.matches()){
                String sql = "INSERT INTO products (Product, StartingInventory, InventoryReceived, InventoryShipped, InventoryOnHand)" +
                " values(" + "'" + name + "'," +start + ","
                + received + "," + shipped + "," + hand + ")";
            this.statement.executeUpdate(sql);
            show();
        }else{
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("Product must meet the following pattern: Dell Server- XP 2000");
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.show();
        }



//        Parent root = FXMLLoader.load(getClass().getResource("fxml/AddProduct.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = new Stage(StageStyle.DECORATED);
//        stage.setScene(scene);
//        stage.show();
    }

    @FXML
    void buttonHandleEdit() throws Exception {
        String nameToChange = tableView.getSelectionModel().getSelectedItem().getProductName();
        String name = txFiName.getText();
        Integer starting = Integer.parseInt(txFiStarting.getText());
        Integer received = Integer.parseInt(txFiReceived.getText());
        Integer shipped = Integer.parseInt(txFiShipped.getText());
        Integer hand = Integer.parseInt(txFiOnHand.getText());
        String sql = "Update products Set Product='" + name + "', StartingInventory=" +
                starting + ", InventoryReceived=" + received + ", " +
                "InventoryShipped=" + shipped + ", " +
                "InventoryOnHand=" + hand +
                " Where product=" + nameToChange;
        this.statement.executeUpdate(sql);
        resetText();
        show();
    }
    private void resetText(){
        txFiName.setText("");
        txFiStarting.setText("");
        txFiReceived.setText("");
        txFiShipped.setText("");
        txFiOnHand.setText("");
    }

    @FXML
    void buttonHandleDelete() throws Exception {
        String name = tableView.getSelectionModel().getSelectedItem().getProductName();
        String sql = ("DELETE FROM products WHERE Product ='" + name +"'" );
        this.statement.executeUpdate(sql);
        resetText();
        show();

    }

    @FXML
    void buttonHandleView() throws Exception {
        show();
    }
    public void show() throws Exception{
        ResultSet rs = this.statement.executeQuery("Select * From Products");
        tableView.getItems().clear();
        while(rs.next()){
            Product product = new Product();
            product.setProductName(rs.getString("Product"));
            product.setStarting(rs.getInt("startingInventory"));
            product.setReceived(rs.getInt("InventoryReceived"));
            product.setShipped(rs.getInt("InventoryShipped"));
            product.setOnHand(rs.getInt("InventoryOnHand"));
            tableView.getItems().add(product);
        }
    }

    @FXML
    void buttonpageOrders() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("fxml/orders.fxml"));
        rootpane.getChildren().setAll(pane);
    }

    @FXML
    void buttonpagePurchases() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("fxml/purchase.fxml"));
        rootpane.getChildren().setAll(pane);

    }

    @FXML
    void ButtonExit() {
    System.exit(1);
    }

}

