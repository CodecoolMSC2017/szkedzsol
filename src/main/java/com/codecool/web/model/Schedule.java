package com.codecool.web.model;

import java.util.List;

public class Schedule extends AbstractModel {
    private int user_id;
    private String name;
    private List<Col> cols;


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

    public List<Col> getCols() {
        return cols;
    }

    public void setCols(List<Col> cols) {
        this.cols = cols;
    }
}
