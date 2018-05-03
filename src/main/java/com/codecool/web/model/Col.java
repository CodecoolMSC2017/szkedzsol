package com.codecool.web.model;

public class Col extends AbstractModel {
    private String name;
    private int schedule_id;


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
}
