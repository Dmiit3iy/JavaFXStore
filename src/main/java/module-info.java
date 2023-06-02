module com.example.javafx20230528 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires javafx.graphics;
    opens com.dmiit3iy.javafxStore.domain;

    opens com.dmiit3iy.javafxStore to javafx.fxml;
    exports com.dmiit3iy.javafxStore;
}

