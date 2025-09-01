package com.appfx.nedimator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    public static Stage stage = null;

    @Override
    public void start(Stage p_stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Maerchenbuch.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
