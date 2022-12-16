package com.example.rgr_pps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class PotEvent {

    static public PotModel pm = PotApplication.pm;
    @FXML
    private ToggleButton tb;
    @FXML
    private Button B_up;
    @FXML
    private Button B_down;
    @FXML
    private Button b_update;
    @FXML
    private TextField TF_heat;
    @FXML
    private TextField TF_wat;
    @FXML
    private TextField TF_troom;
    @FXML
    private TextField TF_val;
    @FXML
    private ImageView im_v;
    @FXML
    private AnchorPane LayoutFirst;

    @FXML
    protected void onButtonUpdateAction()
    {
        PotController.updateValues(TF_heat, TF_wat, TF_troom, TF_val);
    }

    @FXML
    protected void onToggleButtonAction()
    {
        PotController.startStopHeat(tb, im_v, TF_val);
        PotApplication.IE.started();
    }

    @FXML
    protected void buttonUpMousePres()
    {
        PotController.buttonEffect(B_up, true);
    }

    @FXML
    protected void buttonUpMouseUnPress()
    {
        PotController.buttonEffect(B_up, false);
    }
    @FXML
    protected void buttonDownMousePres()
    {
        PotController.buttonEffect(B_down, true);
    }
    @FXML
    protected void buttonDownMouseUnPress()
    {
        PotController.buttonEffect(B_down, false);
    }

    @FXML
    protected void onButtonUpAction()
    {
        PotController.addWaterToPot(im_v);
    }

    @FXML
    protected void onButtonDownAction()
    {
        PotController.clearPot(im_v);
    }

    public void buttonTest()
    {
        PotController.buttonTest(LayoutFirst);
    }

}