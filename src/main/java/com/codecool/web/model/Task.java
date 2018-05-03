package com.codecool.web.model;

public class Task extends AbstractModel {
   private String description;
   private int user_id;

    public Task(int id, String description, int user_id) {
        super(id);
        this.description = description;
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public int getUser_id() {
        return user_id;
    }
}
