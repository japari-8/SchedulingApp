package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println(System.getProperty("javafx.version"));
        //TimeZone tm = TimeZone.getTimeZone("America/Los_Angeles");
        //TimeZone.setDefault(tm);
        //System.out.println(tm);

        //Locale.setDefault(new Locale("fr"));

        String filename2 = "README.txt";
        //FileWriter appendFWriter2 = new FileWriter(filename2, true);
        PrintWriter outputFile = new PrintWriter(filename2);
        outputFile.println("Title: Scheduling App");
        outputFile.println("Purpose: GUI based scheduling desktop application that allows users to Add, update, and delete " +
                "customers and appointments.");
        outputFile.println("Author: Juan Aparicio   Contact: japari6@wgu.edu");
        outputFile.println("V 0.0.1   Date: 12-23-2024");
        outputFile.println("IntelliJ Community 2024.1");
        outputFile.println("Oracle OpenJDK 17.0,10");
        outputFile.println("JavaFX-SDK-17.0.6");
        outputFile.println("Directions: Launch Scheduling app, login, message will appear for upcoming appointment.");
        outputFile.println("In Dashboard top section displays customers and buttons to add/delete/update customer data.");
        outputFile.println("Add and delete buttons will redirect to forms. In Dashboard bottom sections displays appointments.");
        outputFile.println("User can filter appointments for the next 30 days or 7 days. Add and delete buttons redirect to forms.");
        outputFile.println("Run Reports button redirects to 3 different type of reports.");
        outputFile.println("Third report filters all appointments by Country.");
        outputFile.println();
        outputFile.close();

        JDBC.openConnection();
        launch();


        JDBC.closeConnection();

    }
}