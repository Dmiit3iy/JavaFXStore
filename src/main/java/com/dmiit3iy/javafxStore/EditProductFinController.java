
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductFinController {
        public Button editFinConfirmButton;
        private ObservableList<ProductCategory> observableList = FXCollections.observableArrayList();
        private Product product;

        public void initProduct(Product product){
            this.product=product;
        }

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField editPanelNameInput;

        @FXML
        private TextField editPriceInput;

        @FXML
        private ComboBox<ProductCategory> editproductCategoryComboBox;

        @FXML
        void initialize() {
            //int id = product.getId();
            product=EditProductController.productForEdit;
            editPanelNameInput.setText(product.getName());
            observableList.addAll(ProductCategory.values());
            editproductCategoryComboBox.setItems(observableList);

            editproductCategoryComboBox.getSelectionModel().select(product.getCategory());
            editPriceInput.setText(String.valueOf(product.getPrice()));

            editFinConfirmButton.setOnAction(x->{
                try {
                    DataBaseHandler.updateProduct(new Product(product.getId(),editPanelNameInput.getText(),
                            editproductCategoryComboBox.getValue(),new BigDecimal(editPriceInput.getText())));
                    openPage("editProduct.fxml");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }

    public void openPage(String str) throws IOException {
        editFinConfirmButton.getScene().getWindow().hide();
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
