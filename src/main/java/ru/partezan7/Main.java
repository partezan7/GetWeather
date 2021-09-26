package main.java.ru.partezan7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.java.ru.partezan7.entity.Weatherlog;
import main.java.ru.partezan7.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../../resources/ru/partezan7/fxml/sample.fxml"));
        primaryStage.setTitle("GetWeather");
        primaryStage.setScene(new Scene(root, 360, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        HibernateUtil.closeSessionFactory();
    }
}
