module com.example.task2_goi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.google.zxing.javase;
    requires com.google.zxing;


    opens com.example.task2_goi to javafx.fxml;
    exports com.example.task2_goi;
}