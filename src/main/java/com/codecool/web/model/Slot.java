package com.codecool.web.model;

import java.util.List;

public class Slot extends AbstractModel {
    private int col_id;
    private int start;
    private List<Task> tasks;

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
