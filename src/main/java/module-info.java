module com.squidxtv.msc {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.squidxtv.msc to javafx.fxml;
    opens com.squidxtv.msc.util to javafx.fxml;
    opens com.squidxtv.msc.controller to javafx.fxml;
    exports com.squidxtv.msc;
}