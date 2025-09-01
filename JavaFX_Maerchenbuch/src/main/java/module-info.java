module com.appfx.nedimator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.appfx.nedimator to javafx.fxml;
    exports com.appfx.nedimator;
}