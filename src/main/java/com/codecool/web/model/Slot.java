package com.codecool.web.model;

public class Slot extends AbstractModel {
    private int col_id;
    private int start;
    private int stop;

    //CONSTRUCTOR
    public Slot(int id, int col_id, int start, int stop) {
        super(id);
        this.col_id = col_id;
        this.start = start;
        this.stop = stop;
    }


    //GETTERS
    public int getCol_id() {
        return col_id;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }
}
