package com.example.notes.Modules;

public class Task {

    private String task, updatedAt;
    private int id, activate;

    public Task(int id, String note, int activate, String updatedAt) {
        this.id = id;
        this.task = note;
        this.activate = activate;
        this.updatedAt = updatedAt;
    }

    public String getTask() {
        return this.task;
    }

    public int getId() {
        return this.id;
    }

    public int getActivate() { return  this.activate; }

    public String getUpdatedAt() {
        return  this.updatedAt;
    }

    public void setTask(String note) {
        this.task = note;
    }

    public void setActivate(int activate){ this.activate = activate; }

    public void setId(int id) {
        this.id = id;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
