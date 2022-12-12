package com.example.rgr_pps;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PotController {

    private PotModel pm = new PotModel();

    @FXML
    private ToggleButton tb;
    private boolean tb_f = false;
    private boolean tmr = true;
    @FXML
    private Button B_up;
    @FXML
    private Button B_down;
    @FXML
    private Button b_update;
    @FXML
    private Label L_t_wat;
    @FXML
    private Label L_v_wat;
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
    private Image im;
    private int pw = 430, ph = 558;

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

    private void bodyTimer()
    {
        pm.step(tb_f);

        L_t_wat.setText("Темература воды: " + String.format("%.2f", pm.getCurrWater()));
        L_v_wat.setText("Количество воды в кастрюле: " + String.format("%.2f", pm.getCurrVal()));

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

    @FXML
    protected void onButtonUpdateAction()
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

    @FXML
    protected void onToggleButtonAction()
    {
        if(tb_f)
        {
            tb.setText("Включить нагрев");

            tb_f = !tb_f;
        }
        else
        {
            tb.setText("Выключить нагрев");

            if(tmr)
            {
                try {
                    pm.setCurrVal(Float.parseFloat(TF_val.getText()));
                }catch (Exception e) {};
                Timer timer = new Timer();
                Timer timer1 = new Timer();
                drawPotWater();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(()->
                        {
                            bodyTimer();
                        });
                    }
                }, 500, 500);

                timer1.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(()->
                        {
                            drawUpdatePot();
                        });
                    }
                }, 500, 500);

                tmr = false;
            }

            tb_f = !tb_f;
        }
    }

    @FXML
    protected void buttonUpMousePres()
    {
        B_up.setEffect(new DropShadow());
    }

    @FXML
    protected void buttonUpMouseUnPress()
    {
        B_up.setEffect(null);
    }
    @FXML
    protected void buttonDownMousePres()
    {
        B_down.setEffect(new DropShadow());
    }
    @FXML
    protected void buttonDownMouseUnPress()
    {
        B_down.setEffect(null);
    }


    @FXML
    protected void onButtonUpAction()
    {
        try
        {
            pm.addWater();
            addWaterPot();

        }catch (Exception e)
        {
            messageErrorAdd("Ошибка добавления воды, катрюля будет переполнена");
        }
    }

    @FXML
    protected void onButtonDownAction()
    {
        try
        {
            pm.clrWater();
            clrWaterPot();

        }catch (Exception e)
        {
            messageErrorAdd("Ошибка опусташения кастрюли, кастрюля и так пуста!");
        }
    }

}