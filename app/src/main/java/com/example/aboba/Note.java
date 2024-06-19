package com.example.aboba;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;

    private int exerciseTime;
    private int icon;
    private String date;

    public Note(String title, String description, int icon, String date, int exerciseTime) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.date = date;
        this.exerciseTime = exerciseTime;
    }

    public int getId() { return id; }
    public int getExerciseTime() {
        return exerciseTime;
    }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getIcon() { return icon; }
    public void setIcon(int icon) { this.icon = icon; }

    public void setExerciseTime(int exerciseTime) {
        this.exerciseTime = exerciseTime;
    }
    public String getDate() {return date;}
    public void setDate(int date) {this.date = String.valueOf(date); }
}
