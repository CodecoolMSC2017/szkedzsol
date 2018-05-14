package com.codecool.web.model;

public class Schedule extends AbstractModel {
    private int user_id;
    private String name;


    //CONSTRUCTOR
    public Schedule(int id, int user_id, String name) {
        super(id);
        this.user_id = user_id;
        this.name = name;
    }


    //GETTERS
    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }


}
