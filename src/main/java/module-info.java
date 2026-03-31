module com.sge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.sql.rowset;

    opens com.sge to javafx.fxml;
    opens com.sge.model to javafx.base;

    exports com.sge;
}