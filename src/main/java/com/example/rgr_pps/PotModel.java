package com.example.rgr_pps;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;


public class PotModel
{
    public PotModel()
    {

    }
    public void start()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->
                {
                    step(tb_f);
                });
            }
        }, 500, 500);


    }

    public boolean getTBF()
    {
        return this.tb_f;
    }

    public void revTBF()
    {
        this.tb_f = !this.tb_f;
    }

    public void setProperties(double speed, double temprTap, double temprCold, double maxVal)
    {
        this.speed = speed;
        this.temprTap = temprTap;
        this.temprCold = temprCold;
        this.maxVal = maxVal;
    }

    public void setCurrVal(double currVal)
    {
        this.currVal = currVal;
    }

    public void step(boolean heater)
    {
        if(this.currVal > 0)
        {
            if (heater) {
                this.currWater += (this.speed) * 0.1 / (this.currVal * 0.001 * this.c);
            }

            this.currWater += (this.temprCold - this.currWater) * 0.05;
        }
        else
            this.currWater = this.temprCold;


        if(this.currWater >= 100 && this.currVal > 0)
        {
            this.currVal -= 0.05;

            this.boil = true;

            if(this.currVal < 0)
                this.currVal = 0;
        }
        else this.boil = false;
    }

    public double getCurrWater()
    {
        return this.currWater;
    }

    public double getCurrVal()
    {
        return this.currVal;
    }

    public double getPercent()
    {
        return this.currVal  / this.maxVal;
    }

    public void addWater() throws Exception
    {
        if(this.currVal + 1 > this.maxVal)
            throw new Exception("Max Water");

        this.currVal += 1;
        this.currWater += (this.temprTap - this.currWater * this.currVal * 0.1);

        if(this.currWater <= this.temprTap)
            this.currWater = this.temprTap;
    }

    public void clrWater() throws Exception
    {
        if(this.currVal == 0)
            throw new Exception("Empty pot");

        this.currVal = 0;
    }

    private boolean tb_f = false;

    private double c = 4200;

    private double speed = 15;


    private double maxVal = 5;
    private double currVal = 5;
    private double currWater = 37;
    private double temprWater = 37;
    private double temprCold = 26;

    private boolean boil = false;
    private double temprTap = 37;
}
