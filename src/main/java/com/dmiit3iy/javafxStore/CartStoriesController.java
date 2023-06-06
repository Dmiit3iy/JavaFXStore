package com.dmiit3iy.javafxStore;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.dmiit3iy.javafxStore.domain.ProductCategory;
import com.dmiit3iy.javafxStore.domain.ShoppingCart;
import com.dmiit3iy.javafxStore.domain.ShoppingCarts;
import com.dmiit3iy.javafxStore.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CartStoriesController {

    @FXML
    private Label totalPriceLabel;
    private User user;
    private ArrayList<ShoppingCarts>shoppingCarts = new ArrayList<>();
    public void initUsertoCart(User user){
        this.user=user;
        try {
            shoppingCarts= DataBaseHandler.getAllCarts(user);
            observableList.addAll(shoppingCarts);
            totalPriceLabel.setText(String.valueOf(shoppingCarts.stream().map(x->x.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ShoppingCarts, ProductCategory> cartStoriesCategoryColumn;

    @FXML
    private TableColumn<ShoppingCarts, LocalDateTime> cartStoriesDate;

    @FXML
    private Button cartStoriesExitButton;

    @FXML
    private TableColumn<ShoppingCarts, String> cartStoriesNameColumn;

    @FXML
    private TableColumn<ShoppingCarts, BigDecimal> cartStoriesPriceColumn;

    @FXML
    private TableView<ShoppingCarts> productTable;
    ObservableList<ShoppingCarts> observableList = FXCollections.observableArrayList();
    @FXML
    void initialize() {

        cartStoriesDate.setCellValueFactory(new PropertyValueFactory<ShoppingCarts,LocalDateTime>("localDateTime"));
        cartStoriesNameColumn.setCellValueFactory(new PropertyValueFactory<ShoppingCarts, String>("name"));
        cartStoriesCategoryColumn.setCellValueFactory(new PropertyValueFactory<ShoppingCarts, ProductCategory>("category"));
        cartStoriesPriceColumn.setCellValueFactory(new PropertyValueFactory<ShoppingCarts, BigDecimal>("price"));
        productTable.setItems(observableList);


        cartStoriesExitButton.setOnAction(x->{
            try {
                openPage("viewproduct.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public void openPage(String str) throws IOException {
        cartStoriesExitButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        ProductController productController = fxmlLoader.getController();
        productController.initUser(user);
        stage.show();
    }
}
