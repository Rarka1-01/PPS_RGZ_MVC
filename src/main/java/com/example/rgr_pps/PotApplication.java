package com.example.rgr_pps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PotApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        PotModel pm = new PotModel();

        FXMLLoader fxmlLoader = new FXMLLoader(PotApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        PotController pc = fxmlLoader.getController();
        pc.drawPotWater();
        pc.setModelAndEvent(pm);

        stage.setTitle("Pot");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader1 = new FXMLLoader(PotApplication.class.getResource("info-view.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());

        InfoController ic = fxmlLoader1.getController();
        ic.setModelAndEvent(pm);
        ic.setPC(pc);

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