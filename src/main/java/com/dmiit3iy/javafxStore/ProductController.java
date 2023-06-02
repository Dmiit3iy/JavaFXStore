package com.dmiit3iy.javafxStore;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.dmiit3iy.javafxStore.domain.Product;
import com.dmiit3iy.javafxStore.domain.ProductCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController {
        private ObservableList<Product>  observableList = FXCollections.observableArrayList();
        @FXML
        private TableView<Product> productTable;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableColumn<Product, ProductCategory> productListCategoryColumn;


        @FXML
        private Button productListExitButton;

        @FXML
        private TableColumn<Product, Integer> productListIDColumn;

        @FXML
        private TableColumn<Product, String> productListNameColumn;

        @FXML
        private TableColumn<Product, BigDecimal> productListPriceColumn;



        @FXML
        void initialize() throws SQLException {
                ArrayList<Product> productArrayList = DataBaseHandler.getAllProducts();
                System.out.println(productArrayList);
                observableList.addAll(productArrayList);


                productListIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
                productListNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
                productListCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, ProductCategory>("category"));
                productListPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
                productTable.setItems(observableList);



        }

}