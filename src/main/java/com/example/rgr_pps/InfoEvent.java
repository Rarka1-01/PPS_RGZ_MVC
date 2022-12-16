package com.example.rgr_pps;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class InfoEvent {
    @FXML
    private Label L_t_wat;
    @FXML
    private Label L_v_wat;
    @FXML
    private ToggleButton B_test;
    @FXML
    private AnchorPane LayoutSecond;

    @FXML
    protected void onButtonTest()
    {
        PotApplication.PE.buttonTest();
        InfoController.buttonTest(LayoutSecond, B_test);
    }

    public void started()
    {
        InfoController.startUpdateInfo(L_t_wat, L_v_wat);
    }

}
