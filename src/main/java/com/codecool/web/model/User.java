package com.codecool.web.model;

import java.util.ArrayList;
import java.util.Objects;

public final class User extends AbstractModel {

    private final String name;
    private final String email;
    private Role role;
    private ArrayList<Schedule> schedules;

    //CONSTRUCTOR
    public User(int id, String name, String email, Role role) {
        super(id);
        this.name = name;
        this.email = email;
        this.role = role;
    }


    //GETTERS
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
            Objects.equals(email, user.email) &&
            role == user.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, email, role);
    }
}




