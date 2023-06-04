
package com.dmiit3iy.javafxStore;

        import java.math.BigDecimal;
        import java.net.URL;
        import java.util.ResourceBundle;

        import com.dmiit3iy.javafxStore.domain.Product;
        import com.dmiit3iy.javafxStore.domain.ProductCategory;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;

public class EditProductController {

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
    private TableView<?> editPageproductTable;

    @FXML
    private Label productListAlertLabel;

    @FXML
    void initialize() {


    }

}
