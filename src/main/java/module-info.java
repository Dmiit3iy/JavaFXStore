module com.example.javafx20230528 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires javafx.graphics;
    opens com.dmiit3iy.javafxStore.domain;

    exports com.dmiit3iy.javafxStore;
    opens com.dmiit3iy.javafxStore;
    exports com.dmiit3iy.javafxStore.domain;
}

