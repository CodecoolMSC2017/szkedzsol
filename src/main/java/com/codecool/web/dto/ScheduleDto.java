package com.codecool.web.dto;

import com.codecool.web.model.Col;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Slot;
import com.codecool.web.model.Task;

public class ScheduleDto {

    private Schedule schedule;
    private Col col;
    private Slot slot;
    private Task task;

    public ScheduleDto(Schedule schedule, Col col, Slot slot, Task task) {
        this.schedule = schedule;
        this.col = col;
        this.slot = slot;
        this.task = task;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Col getCol() {
        return col;
    }

    public Slot getSlot() {
        return slot;
    }

    public Task getTask() {
        return task;
    }
}
