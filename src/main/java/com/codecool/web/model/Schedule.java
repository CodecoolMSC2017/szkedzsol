package com.codecool.web.model;

public class Schedule extends AbstractModel {
    private int user_id;
    private String name;
    private int col_id;


    //CONSTRUCTOR
    public Schedule(int id, int user_id, String name, int col_id) {
        super(id);
        this.user_id = user_id;
        this.name = name;
        this.col_id = col_id;
    }


    //GETTERS
    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public int getCol_id() {
        return col_id;
    }
}
