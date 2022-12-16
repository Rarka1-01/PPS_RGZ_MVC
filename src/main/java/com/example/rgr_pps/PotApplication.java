package com.example.rgr_pps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PotApplication extends Application {
    static public PotModel pm = new PotModel();
    static public PotEvent PE = null;
    static public InfoEvent IE = null;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PotApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        PE = fxmlLoader.getController();
        stage.setTitle("Pot");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader1 = new FXMLLoader(PotApplication.class.getResource("info-view.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());
        IE = fxmlLoader1.getController();
        Stage st1 = new Stage();
        st1.setTitle("Info");
        st1.setResizable(false);
        st1.setScene(scene1);
        st1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}