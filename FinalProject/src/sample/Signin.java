package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Signin implements Initializable {

    @FXML
    private Pane rootpane;

    @FXML
    private TextField txfiuser;

    @FXML
    private TextField txfipass;

    @FXML
    private Label msg;

    Statement statement;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                    DriverManager.
                            getConnection("jdbc:mysql://127.0.0.1:3306/inventory?serverTimezone=UTC",
                                    "root", "");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("**** Cannot connect To Database ****");
            a.setAlertType(Alert.AlertType.WARNING);
            a.showAndWait();
            System.exit(1);
        }
    }


        @FXML
        void buttonPass () throws IOException {

        }

        @FXML
        void buttonSign () throws IOException {
        String username = "amron2000";
        String password = "amron1234567";
        String user = txfiuser.getText();
        String pass = txfipass.getText();
        if(username.equals(user) && password.equals(pass))  {
            Pane pane = FXMLLoader.load(getClass().getResource("fxml/Current.fxml"));
            rootpane.getChildren().setAll(pane);
        }else{
        msg.setText("Username or Password is wrong");
        msg.setTextFill(Color.RED);
  //      msg.setTooltip(new Tooltip("Username or Password is wrong"));
        }
        }

        @FXML
        void buttonUser () {

        }
    }

