/*
Faris Mohamed Amine
2 dam
*/


package com.example.conexion_retrofit_gson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 500);
        stage.setTitle("Tiempo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}