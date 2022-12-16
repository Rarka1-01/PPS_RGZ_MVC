package com.example.rgr_pps;

public abstract class Controller {

    public abstract void getEvent();

    protected Event ev;
    protected PotModel pm;

    public void setModelAndEvent(PotModel pm)
    {
        this.pm = pm;
        this.ev = this.pm.getEventObj();
        this.ev.addSub(this);
    }
}
