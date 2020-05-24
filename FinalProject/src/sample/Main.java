package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        {
            try {
                CurrentInventory.log = new Log("log.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("fxml/current.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //////
        CurrentInventory.log.logger.setLevel(Level.SEVERE);
        CurrentInventory.log.logger.severe("**** Connect To Database Successfully ****");
        //////
    }


    public static void main(String[] args) {
        launch(args);
    }
}
