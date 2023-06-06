
package com.dmiit3iy.javafxStore;

import com.dmiit3iy.javafxStore.domain.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import com.dmiit3iy.javafxStore.domain.ProductCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class EditProductController {
   public static Product productForEdit;
    private ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, ProductCategory> editPageproductListCategoryColumn;

    @FXML
    private Button editPageproductListExitButton;

    @FXML
    private TableColumn<Product, Integer> editPageproductListIDColumn;

    @FXML
    private TableColumn<Product, String> editPageproductListNameColumn;

    @FXML
    private TableColumn<Product, BigDecimal> editPageproductListPriceColumn;

    @FXML
    private TableView<Product> editPageproductTable;

    @FXML
    private Label productListAlertLabel;

    @FXML
    void initialize() {
        editPageproductListIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        editPageproductListNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        editPageproductListCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, ProductCategory>("category"));
        editPageproductListPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));

        try {
            productObservableList.addAll(DataBaseHandler.getAllProducts());
            editPageproductTable.setItems(productObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addButtonToTable();
    }

    private void addButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            productForEdit=product;

                            try {
                                openPage("editProductFin.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        editPageproductTable.getColumns().add(colBtn);

    }

    public void openPage(String str) throws IOException {
        editPageproductListExitButton.getScene().getWindow().hide();
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
