package com.example.rgr_pps;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PotController1 {

    static public PotModel pm = new PotModel();

    @FXML
    static private ToggleButton tb;
    static private boolean tb_f = false;
    static private boolean tmr = true;
    @FXML
    static private Button B_up;
    @FXML
    static private Button B_down;
    @FXML
    static private Button b_update;
    @FXML
    static private TextField TF_heat;
    @FXML
    static private TextField TF_wat;
    @FXML
    static private TextField TF_troom;
    @FXML
    static private TextField TF_val;
    @FXML
    static private ImageView im_v;
    static private Image im;
    static private int pw = 430, ph = 558;

    private void drawPotWater()
    {
        WritableImage img = new WritableImage(pw, ph);
        PixelWriter ppw = img.getPixelWriter();

        for(int i = 0; i < ph; i++)
            for(int j = 0; j < pw; j++)
            {
                ppw.setColor(j, i, Color.BLACK);

                if (i >= (ph / 2) - 5)
                    if(j <= 15 || j >= pw - 15)
                        ppw.setColor(j, i, Color.GRAY);
                    else
                        ppw.setColor(j,i,Color.BLUE);

                if(i >= ph - 15)
                    ppw.setColor(j, i, Color.GRAY);
            }

        im_v.setImage(img);

    }

    private void drawUpdatePot()
    {
        PixelReader pr = im_v.getImage().getPixelReader();
        WritableImage img = new WritableImage(pw, ph);
        PixelWriter ppw = img.getPixelWriter();

        for(int i = 0; i < ph; i++)
            for(int j = 0; j < pw; j++)
                ppw.setColor(j, i, pr.getColor(j, i));

        int u = (ph / 2) - 1;

        while(!Objects.equals(pr.getColor(16, u).toString(), Color.BLUE.toString()) && u < ph - 15)
            u++;

        int u1 = ph - 15 - (int)(pm.getPercent() * 274);

        if(pm.getCurrWater() >= 100)
        {
            for(int i = u; i < u1; i++)
                for(int j = 16; j < pw - 15; j++)
                    if(j >= 18 && j < pw - 17)
                    {
                        if (Math.random() * 101 <= 5)
                            ppw.setColor(j, i, Color.BLUE);
                        else
                            ppw.setColor(j, i, Color.BLACK);
                    }
                    else
                        ppw.setColor(j, i, Color.BLACK);
        }

        pr = img.getPixelReader();


        for(int i = 0; i < u1; i++)
            for(int j = 18; j < pw - 17; j++)
                if(Objects.equals(pr.getColor(j, i).toString(), Color.BLUE.toString()))
                {
                    if(i != 0)
                        ppw.setColor(j, i -1, Color.BLUE);
                    ppw.setColor(j, i, Color.BLACK);
                }

        im_v.setImage(img);

    }

    private void addWaterPot()
    {
        PixelReader pr = im_v.getImage().getPixelReader();
        WritableImage img = new WritableImage(pw, ph);
        PixelWriter ppw = img.getPixelWriter();

        for(int i = 0; i < ph; i++)
            for(int j = 0; j < pw; j++)
                ppw.setColor(j, i, pr.getColor(j, i));

        int u1 = ph - 13 - (int)(pm.getPercent() * 274);

        for(int i = u1; i < ph - 15; i++)
            for(int j = 16; j < pw - 15; j++)
                ppw.setColor(j, i, Color.BLUE);

        im_v.setImage(img);
    }

    private void clrWaterPot()
    {
        PixelReader pr = im_v.getImage().getPixelReader();
        WritableImage img = new WritableImage(pw, ph);
        PixelWriter ppw = img.getPixelWriter();

        for(int i = 0; i < ph; i++)
            for(int j = 0; j < pw; j++)
                ppw.setColor(j, i, pr.getColor(j, i));

        for(int i = ph / 2; i < ph - 15; i++)
            for(int j = 16; j < pw - 15; j++)
                ppw.setColor(j, i, Color.BLACK);

        im_v.setImage(img);
    }

    private void messageError(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setContentText("Неверный ввод даных поля: " + s + "\nЗначение выставлено по умолчанию");
        alert.showAndWait();
    }

    private void messageErrorAdd(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(s);
        alert.showAndWait();
    }

}
