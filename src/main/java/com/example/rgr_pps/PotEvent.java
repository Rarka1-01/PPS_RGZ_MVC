package com.example.rgr_pps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class Events {

    static public PotModel pm = new PotModel();
    @FXML
    public ToggleButton tb;
    @FXML
    public Button B_up;
    @FXML
    public Button B_down;
    @FXML
    public Button b_update;
    @FXML
    public TextField TF_heat;
    @FXML
    public TextField TF_wat;
    @FXML
    public TextField TF_troom;
    @FXML
    public TextField TF_val;
    @FXML
    public ImageView im_v;
    @FXML
    public AnchorPane LayoutFirst;
    @FXML
    public Label L_t_wat;
    @FXML
    public Label L_v_wat;
    @FXML
    public ToggleButton B_test;
    @FXML
    public AnchorPane LayoutSecond;

    @FXML
    protected void onButtonUpdateAction()
    {
        PotController.updateValues();
    }

    @FXML
    protected void onToggleButtonAction()
    {
        PotController.startStopHeat();
    }

    @FXML
    protected void buttonUpMousePres()
    {
        PotController.buttonEffect(1);
    }

    @FXML
    protected void buttonUpMouseUnPress()
    {
        PotController.buttonEffect(2);
    }
    @FXML
    protected void buttonDownMousePres()
    {
        PotController.buttonEffect(3);
    }
    @FXML
    protected void buttonDownMouseUnPress()
    {
        PotController.buttonEffect(4);
    }


    @FXML
    protected void onButtonUpAction()
    {
        PotController.addWaterToPot();
    }

    @FXML
    protected void onButtonDownAction()
    {
        PotController.clearPot();
    }

    @FXML
    protected void onButtonTest()
    {
       // PotController.buttonTest();
        //InfoController.buttonTest(LayoutSecond);
        LayoutFirst.setStyle("-fx-background-color:  #00ff00;");
    }
}