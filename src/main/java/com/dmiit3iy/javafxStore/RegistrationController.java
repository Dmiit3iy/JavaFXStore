package com.dmiit3iy.javafxStore;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.dmiit3iy.javafxStore.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationController {

    public Label registrationAlertLable;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button registratinPageBackToAuthPageButton;

    @FXML
    private TextField registratinPageNameInput;

    @FXML
    private TextField registratinPagePasswordInput1;

    @FXML
    private TextField registratinPagePasswordInput2;

    @FXML
    private Button registratinPageRegistrationButton;

    @FXML
    private TextField registratinPageUsernamInput;

    @FXML
    void initialize() {

        registratinPageRegistrationButton.setOnAction(x -> {
            String username = null;
            String password = null;
            String name = null;
            registrationAlertLable.setWrapText(true);
            try {
                if (!DataBaseHandler.isUniqueUserName(registratinPageUsernamInput.getText())) {
                    username = registratinPageUsernamInput.getText();
                } else {
                    registrationAlertLable.setText("Введнный логин уже занят");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (registratinPagePasswordInput1.getText().equals(registratinPagePasswordInput2.getText())) {
                password = registratinPagePasswordInput1.getText();
            } else {
                registrationAlertLable.setText("Введенные пароли не совпадают");
            }
            name = registratinPageNameInput.getText();
            try {
                if (username!=null&&(!password.equals(""))&&(!name.equals(""))){
                    DataBaseHandler.addUser(new User(username, password, name, "USER"));
                    registrationAlertLable.setText("Новый пользователь создан!");
                    clearAllText();
                } else {
                    registrationAlertLable.setText("Проверьте, что все поля заполнены правильно!");
                    clearAllText();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void openForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Authorization.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        stage.show();
        Stage stage1 = (Stage) registratinPageBackToAuthPageButton.getScene().getWindow();
        stage1.close();
    }

    public void clearAllText(){
        registratinPageUsernamInput.clear();
        registratinPagePasswordInput1.clear();
        registratinPagePasswordInput2.clear();
        registratinPageNameInput.clear();
    }

}
