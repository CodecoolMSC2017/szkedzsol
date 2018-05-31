package com.codecool.web.model;

import java.util.List;

public class Col extends AbstractModel {
    private String name;
    private int schedule_id;
    private List<Slot> slots;


    //CONSTRUCTOR
    public Col(int id, String name, int schedule_id) {
        super(id);
        this.name = name;
        this.schedule_id = schedule_id;
    }


    //GETTERS
    public String getName() {
        return name;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}
