package com.dmiit3iy.javafxStore;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.dmiit3iy.javafxStore.domain.Product;
import com.dmiit3iy.javafxStore.domain.ProductCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController {
    public Button adminPanelExitButton;
    public Button adminPanelEditProductButton;
    private ObservableList<ProductCategory> observableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adminPaneAddProductButton;

    @FXML
    private ComboBox<ProductCategory> productCategoryComboBox;

    @FXML
    private TextField adminPanePriceInput;

    @FXML
    private TextField adminPanelNameInput;

    @FXML
    private Label adminPanelAlertLabel;



    @FXML
    void initialize() {
        observableList.addAll(ProductCategory.values());
        productCategoryComboBox.setItems(observableList);
        adminPaneAddProductButton.setOnAction(x->{
            try {
                Product product = new Product(adminPanelNameInput.getText(),productCategoryComboBox.getValue(),
                        new BigDecimal(adminPanePriceInput.getText()));
               if( DataBaseHandler.isUniqueProductName(product.getName())){
                   DataBaseHandler.addProduct(product);
                   clearAllText();
                   adminPanelAlertLabel.setText("Продукт успешно добавлен");
               } else { adminPanelAlertLabel.setText("Продукт уже существует");

               }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        adminPanelExitButton.setOnAction(x->{
            try {
                openPage("Authorization.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        adminPanelEditProductButton.setOnAction(x->{
            try {
                openPage("editProduct.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public  void openPage(String str) throws IOException {
        adminPanelExitButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root=fxmlLoader.getRoot();
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Метод для очистки полей
     */
    public void clearAllText(){
        adminPanePriceInput.clear();
        adminPanelNameInput.clear();
        productCategoryComboBox.getItems().clear();
    }

}
