package com.example.rgr_pps;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class InfoController extends Controller {
    @FXML
    private Label L_t_wat;
    @FXML
    private Label L_v_wat;
    @FXML
    private ToggleButton B_test;
    @FXML
    private AnchorPane LayoutSecond;

    private boolean tbs = true;
    private PotController pc;

    public void setPC(PotController pc)
    {
        this.pc = pc;
    }

    @FXML
    protected void onButtonTest()
    {
        pc.buttonTest();
        buttonTest();
    }

    private void updateInfo()
    {
        L_t_wat.setText("Темература воды: " + String.format("%.2f", pm.getCurrWater()));
        L_v_wat.setText("Количество воды в кастрюле: " + String.format("%.2f", pm.getCurrVal()));

    }

    public void buttonTest()
    {
        if(tbs) {
            LayoutSecond.setStyle("-fx-background-color:  #7f7e7e;");
            B_test.setText("Установить светлую тему");
        }
        else {
            LayoutSecond.setStyle("-fx-background-color:  #ffffff;");
            B_test.setText("Установить тёмную тему");
        }

        tbs = ! tbs;
    }

    @Override
    public void getEvent() {
        Thread th = new Thread(() -> Platform.runLater(this::updateInfo));
        th.start();
    }
}
