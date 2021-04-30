package com.urealaden.taskmaster.models;

public class TaskItem {
    String name;
    String description;

    public TaskItem(String name,String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
