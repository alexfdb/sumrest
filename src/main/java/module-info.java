module com.sumrest {
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive java.sql;
    requires org.controlsfx.controls;
    requires transitive javafx.graphics;

    exports com.sumrest;
    exports com.sumrest.model;
    exports com.sumrest.controller;

    opens com.sumrest to javafx.fxml;
    opens com.sumrest.model to javafx.fxml;
    opens com.sumrest.controller to javafx.fxml;
}