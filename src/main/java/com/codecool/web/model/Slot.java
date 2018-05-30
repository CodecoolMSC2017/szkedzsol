package com.codecool.web.model;

public class Slot extends AbstractModel {
    private int col_id;
    private int start;

    //CONSTRUCTOR
    public Slot(int id, int col_id, int start) {
        super(id);
        this.col_id = col_id;
        this.start = start;
    }


    //GETTERS
    public int getCol_id() {
        return col_id;
    }

    public int getStart() {
        return start;
    }
}
