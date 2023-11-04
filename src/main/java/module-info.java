module com.hellocode.atm {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.hellocode.atm;
    opens com.hellocode.atm to javafx.fxml;
}