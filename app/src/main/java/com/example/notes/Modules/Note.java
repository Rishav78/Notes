package com.example.notes.Modules;

public class Note {

    private String note, updatedAt;
    private int id;

    public Note(int id, String note, String updatedAt) {
        this.id = id;
        this.note = note;
        this.updatedAt = updatedAt;
    }

    public String getNote() {
        return this.note;
    }

    public int getId() {
        return this.id;
    }

    public String getUpdatedAt() {
        return  this.updatedAt;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
