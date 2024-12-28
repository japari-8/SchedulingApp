package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/aparicio/view/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        //TimeZone tm = TimeZone.getTimeZone("America/Los_Angeles");
        //TimeZone.setDefault(tm);
        //System.out.println(tm);

        //Locale.setDefault(new Locale("fr"));

        System.out.println(LocalDateTime.now());

        JDBC.openConnection();
        launch();


        JDBC.closeConnection();

    }
}