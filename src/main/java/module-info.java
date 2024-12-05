module com.example.conexion_retrofit_gson {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires com.google.gson;
    requires okhttp3;
    requires java.desktop;

    opens com.example.conexion_retrofit_gson to javafx.fxml, com.google.gson;
    exports com.example.conexion_retrofit_gson;
}
