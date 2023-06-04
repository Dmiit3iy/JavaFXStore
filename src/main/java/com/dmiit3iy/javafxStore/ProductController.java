package com.dmiit3iy.javafxStore;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.dmiit3iy.javafxStore.domain.Product;
import com.dmiit3iy.javafxStore.domain.ProductCategory;
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

public class ProductController {
   private User user;

    @FXML
    private Button buyButton;
    private BigDecimal totalPr = new BigDecimal(0);
    public TableColumn productListIDColumnCart;
    public TableColumn productListNameColumnCart;
    public TableColumn productListCategoryColumnCart;
    public TableColumn productListPriceColumnCart;
    public Label productListAlertLabel;
    @FXML
    private Label labelTotalPrice;
    @FXML
    private TableView productListCart;
    @FXML
    private Button productListAddButton;
    private ObservableList<Product> observableList = FXCollections.observableArrayList();
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

    public void initUser(User user) {
        this.user=user;
    }
    @FXML
    void initialize() throws SQLException {


        ArrayList<Product> productArrayList = DataBaseHandler.getAllProducts();
        ObservableList<Product> cartList = FXCollections.observableArrayList();
        observableList.addAll(productArrayList);


        productListIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productListNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productListCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, ProductCategory>("category"));
        productListPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
        productTable.setItems(observableList);
        productListIDColumnCart.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productListNameColumnCart.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productListCategoryColumnCart.setCellValueFactory(new PropertyValueFactory<Product, ProductCategory>("category"));
        productListPriceColumnCart.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));

        productListAddButton.setOnAction(x -> {
            productListAlertLabel.setText("");
            if (productTable.getSelectionModel().getSelectedItem() != null) {

                Product product = productTable.getSelectionModel().getSelectedItem();
                cartList.add(product);
                productListCart.setItems(cartList);
                System.out.println(totalPr.add(product.getPrice()).toString());
                totalPr =totalPr.add(product.getPrice());
               labelTotalPrice.setText(totalPr.toString());

            } else {
                productListAlertLabel.setWrapText(true);
                productListAlertLabel.setText("Для добавлению нужно выбрать позицию");
            }
        });

        productListExitButton.setOnAction(x -> {
            try {
                openPage("Authorization.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buyButton.setOnAction(x->{
            try {
                System.out.println(user);
              long idCart =  DataBaseHandler.addCartList(user);
             ObservableList<Product> productObservableList =productListCart.getItems();
             DataBaseHandler.addCartProductList(idCart, productObservableList);
                productListCart.getItems().clear();
                totalPr=new BigDecimal(0);
                labelTotalPrice.setText("0");
                productListAlertLabel.setText("Покупки оплачены");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });



    }

    public void openPage(String str) throws IOException {
        productListExitButton.getScene().getWindow().hide();
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
