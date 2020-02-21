package com.example.notes.Modules;

public class Note {

    private String note;
    private int id;

    public Note(int id, String note) {
        this.id = id;
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public int getId() {
        return this.id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

}
