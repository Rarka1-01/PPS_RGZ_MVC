package com.example.rgr_pps;

import java.util.ArrayList;

public class Event {

    public Event()
    {
        subs = new ArrayList<>();
    }

    public void addSub(Controller sub)
    {
        this.subs.add(sub);
    }

    public void callEvent()
    {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for(var i: subs)
                    i.getEvent();
            }
        });

        th.start();
    }


    private ArrayList<Controller> subs;
}
