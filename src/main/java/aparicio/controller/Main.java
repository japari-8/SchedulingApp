package aparicio.controller;

import dao.CustomerDAO;
import dao.UserDAO;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

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

        //Locale.setDefault(new Locale("fr"));

        JDBC.openConnection();
        //launch();


        int rowsaffected = CustomerDAO.updateCust(7,  "Jay Three",  "333 Seeless Ave.",  "99999",  "333-333-3333",
         5);

        if (rowsaffected > 0) {
            System.out.println("Update Successful!");
        }
        else {
            System.out.println("Update Failed!");
        }


        JDBC.closeConnection();

    }
}