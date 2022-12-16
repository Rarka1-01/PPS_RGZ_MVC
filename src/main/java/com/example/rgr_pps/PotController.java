package com.example.rgr_pps;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PotController {

    static private PotModel pm = PotEvent.pm;
    static private boolean tmr = true;
    static private boolean tbs = true;
    static private int pw = 430, ph = 558;

    static private void drawPotWater(final ImageView im_v)
    {
        WritableImage img = new WritableImage(pw, ph);
        PixelWriter ppw = img.getPixelWriter();

        for(int i = 0; i < ph; i++)
            for(int j = 0; j < pw; j++)
            {
                ppw.setColor(j, i, Color.BLACK);

                if (i >= (ph / 2) - 5)
                    if(j <= 15 || j >= pw - 15)
                        ppw.setColor(j, i, Color.RED);
                    else
                        ppw.setColor(j,i,Color.BLUE);

                if(i >= ph - 15)
                    ppw.setColor(j, i, Color.RED);
            }

        im_v.setImage(img);

    }

    static private void drawUpdatePot(final ImageView im_v)
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

    static private void addWaterPot(final ImageView im_v)
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

    static private void clrWaterPot(final ImageView im_v)
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

    static private void messageError(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setContentText("Неверный ввод даных поля: " + s + "\nЗначение выставлено по умолчанию");
        alert.showAndWait();
    }

    static private void messageErrorAdd(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(s);
        alert.showAndWait();
    }

    static public void updateValues(final TextField TF_heat, final TextField TF_wat,
                                    final TextField TF_troom, final TextField TF_val)
    {
        double h, w, t, v;

        try
        {
            h = Float.parseFloat(TF_heat.getText());

            if(h <= 0)
                throw new Exception("Error minus");

        }catch (Exception e)
        {
            h = 15;
            TF_heat.setText(Double.toString(h));
            messageError("отдача тепла за секунуду");
        }

        try
        {
            w = Float.parseFloat(TF_wat.getText());
        }catch(Exception e)
        {
            w = 37;
            TF_wat.setText(Double.toString(w));
            messageError("температура воды из-под крана");
        }

        try
        {
            t = Float.parseFloat(TF_troom.getText());
        }catch (Exception e)
        {
            t = 26;
            TF_troom.setText(Double.toString(t));
            messageError("температура комнаты");
        }

        try
        {
            v = Float.parseFloat(TF_val.getText());

            if(v < 1 || v < pm.getCurrVal())
                throw new Exception("Error minus");

        }catch (Exception e)
        {
            v = 5;
            pm.setCurrVal(v);
            TF_val.setText(Double.toString(v));
            messageError("максимальная ёмкость кастрюли");
        }

        pm.setProperties(h, w, t, v);
    }

    static public void startStopHeat(final ToggleButton tb, final ImageView im_v,
                                     final TextField TF_val)
    {
        if(pm.getTBF())
        {
            tb.setText("Включить нагрев");

            pm.revTBF();
        }
        else
        {
            tb.setText("Выключить нагрев");

            if(tmr)
            {
                try {
                    pm.setCurrVal(Float.parseFloat(TF_val.getText()));
                }catch (Exception ignored) {};

               pm.start();

                Timer timer1 = new Timer();
                drawPotWater(im_v);

                timer1.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            drawUpdatePot(im_v);
                        });
                    }
                }, 500, 500);

                tmr = false;
            }

            pm.revTBF();
        }
    }

    static public void addWaterToPot(final ImageView im_v)
    {
        try
        {
            pm.addWater();
            addWaterPot(im_v);

        }catch (Exception e)
        {
            messageErrorAdd("Ошибка добавления воды, катрюля будет переполнена");
        }
    }

    static public void clearPot(final ImageView im_v)
    {
        try
        {
            pm.clrWater();
            clrWaterPot(im_v);

        }catch (Exception e)
        {
            messageErrorAdd("Ошибка опусташения кастрюли, кастрюля и так пуста!");
        }
    }

    static public void buttonEffect(final Button button, boolean a)
    {
        if(a)
            button.setEffect(new DropShadow());
        else
            button.setEffect(null);
    }

    static public void buttonTest(final AnchorPane LayoutFirst)
    {
        if(tbs)
            LayoutFirst.setStyle("-fx-background-color:  #7f7e7e;");
        else
            LayoutFirst.setStyle("-fx-background-color:  #ffffff;");

        tbs = ! tbs;
    }
}
