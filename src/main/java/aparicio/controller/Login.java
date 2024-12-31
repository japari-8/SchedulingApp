package aparicio.controller;

import aparicio.dao.UserDAO;
import aparicio.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.util.Locale.getDefault;

/** This class creates the Login page for the Scheduling Application.
 * Contains user and password textfields, authenticates, and redirects to Dashboard.*/
public class Login implements Initializable {

    public TextField userName;
    public TextField password;
    public Label loginLocation;
    public String tz;
    public Label enterLabel;
    public Label credentialsLabel;
    public Button logInButton;

    /**This method initializes the login page.
     * It uses a ResourceBundle to translate the Login page to French if the user's default language is set to French.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId z = ZoneId.systemDefault();
        String z1 = z.toString();
        loginLocation.setText(z1);

        ResourceBundle rb = ResourceBundle.getBundle("/aparicio/view/Lan_fr", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            enterLabel.setText(rb.getString("Enter"));
            credentialsLabel.setText(rb.getString("Credentials"));
            userName.setPromptText(rb.getString("username"));
            password.setPromptText(rb.getString("password"));
            logInButton.setText(rb.getString("Login"));
        }
    }


    /**This method authenticates the user and creates a login attempt.
     * It also displays error messages if credentials are incorrect*/
    public void onLogin(ActionEvent actionEvent) throws IOException {

        //LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        ZonedDateTime utcNow = zdt.withZoneSameInstant(ZoneId.of("UTC"));

        //String date = String.valueOf(ldtToUtc);
        //String s = String.valueOf(ldtToUtc);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnn z");
        String instToString  = formatter.format(utcNow);



        String uName = userName.getText();
        String pWord = password.getText();
        boolean auth = false;

        String filename = "login_activity.txt";
        FileWriter appendFWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(appendFWriter);

        Iterator<User> iterator = UserDAO.getAllUsers().iterator();
        while (iterator.hasNext()) {
            User userN = iterator.next();
            String userNDB = userN.getUserName();
            String userPDB = userN.getPassword();

            if (uName.equals(userNDB) && pWord.equals(userPDB)) {

                outputFile.println("User " + uName + " successfully logged in at " + instToString);
                outputFile.close();

                User userFromDB = UserDAO.getUserLogedIn(uName, pWord);
                Dashboard.passLogedUser(userFromDB);

                Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 750);
                stage.setTitle("Dashboard");
                stage.setScene(scene);
                stage.show();

                auth = true;
            }
        }
            if (auth == false) {

                outputFile.println("User " + uName + " gave invalid log in at " + instToString);
                outputFile.close();

                ResourceBundle rb2 = ResourceBundle.getBundle("/aparicio/view/Lan_fr", Locale.getDefault());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");

                if (Locale.getDefault().getLanguage().equals("fr")) {
                    alert.setContentText(rb2.getString("username") + " " + rb2.getString("or") + " " +
                            rb2.getString("password") + " " + rb2.getString("incorrect"));
                } else {
                    alert.setContentText("Incorrect username or password");
                }
                alert.showAndWait();
            }

    }


}