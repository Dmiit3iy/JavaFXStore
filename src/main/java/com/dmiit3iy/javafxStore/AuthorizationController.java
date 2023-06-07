package com.dmiit3iy.javafxStore;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import com.dmiit3iy.javafxStore.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button mainPageAuthorizationButton;

    @FXML
    private PasswordField mainPageIPasswordnput;

    @FXML
    private TextField mainPageLoginInput;

    @FXML
    private Button mainPageRegistrationButton;

    @FXML
    private Label authorizationAlertLabel;

    @FXML
    void initialize() {
        mainPageAuthorizationButton.setOnAction(x -> {
            String login = mainPageLoginInput.getText();
            String password = mainPageIPasswordnput.getText();
            authorizationAlertLabel.setWrapText(true);
            try {
                if (DataBaseHandler.userIsExist(login, password) == null) {
                    authorizationAlertLabel.setText("Ошибка авторизации пользователя!!");

                } else if (DataBaseHandler.userIsExist(login, password).equals("ADMIN")) {
                    openPage1("addProduct.fxml");

                } else if (DataBaseHandler.userIsExist(login, password).equals("USER")) {

                    openPage("viewproduct.fxml");

                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        mainPageRegistrationButton.setOnAction(x -> {
            try {
                openPage1("Registration.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }


    public void openPage(String str) throws IOException, SQLException {
        mainPageAuthorizationButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(650);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        ProductController productController = fxmlLoader.getController();

        User user =DataBaseHandler.getUser(mainPageLoginInput.getText());
        productController.initUser(user);
        stage.show();
    }

    public void openPage1(String str) throws IOException, SQLException {
        mainPageAuthorizationButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.show();
    }
}
