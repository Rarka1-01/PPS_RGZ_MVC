package com.example.rgr_pps;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;
import java.util.TimerTask;

public class InfoController
{
    static private PotModel pm = PotEvent.pm;
    static private boolean tbs = true;

    static public void startUpdateInfo(final Label L_t_wat, final Label L_v_wat)
    {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    bodyTimer(L_t_wat, L_v_wat);
                });
            }
        }, 100, 100);
    }

    private static void bodyTimer(final Label L_t_wat, final Label L_v_wat)
    {

        L_t_wat.setText("Темература воды: " + String.format("%.2f", pm.getCurrWater()));
        L_v_wat.setText("Количество воды в кастрюле: " + String.format("%.2f", pm.getCurrVal()));

    }

    public static void buttonTest(final AnchorPane LayoutSecond, final ToggleButton B_test)
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
}
