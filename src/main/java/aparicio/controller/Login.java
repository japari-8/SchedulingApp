package aparicio.controller;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;


import static java.util.Locale.getDefault;

public class Login implements Initializable {

    public TextField userName;
    public TextField password;
    public Label loginLocation;
    public String tz;
    public Label enterLabel;
    public Label credentialsLabel;
    public Button logInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       tz = TimeZone.getDefault().getDisplayName();
       loginLocation.setText(tz);

        ResourceBundle rb = ResourceBundle.getBundle("/aparicio/view/Lan_fr", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            enterLabel.setText(rb.getString("Enter"));
            credentialsLabel.setText(rb.getString("Credentials"));
            userName.setPromptText(rb.getString("username"));
            password.setPromptText(rb.getString("password"));
            logInButton.setText(rb.getString("Login"));

        }


    }



    public void onLogin(ActionEvent actionEvent) throws IOException {

        //Use for testing. Delete when ready to run application.
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();


    /*    String uName = userName.getText();
        String pWord = password.getText();

        //Following tests credentials and translated error message but not tied to database yet.
        if (uName.equals("test") & pWord.equals("Admin")) {

            Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        else {
            ResourceBundle rb2 = ResourceBundle.getBundle("/aparicio/view/Lan_fr", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");

            if (Locale.getDefault().getLanguage().equals("fr")) {
                alert.setContentText(rb2.getString("username") + " " + rb2.getString("or") + " " +
                        rb2.getString("password") + " " + rb2.getString("incorrect"));
            }
            else {
                alert.setContentText("Incorrect username or password");
            }
            alert.showAndWait();
        }
    */
    }


}