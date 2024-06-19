package com.example.aboba;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient instance;

    private NoteDatabase noteDatabase;

    private DatabaseClient(Context context) {
        this.context = context;
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "NoteDB").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            try {
                instance = new DatabaseClient(context);
            } catch (Exception e) {
                Log.e("DatabaseClient", "Ошибка при создании базы данных: " + e.getMessage());
            }
        }
        return instance;
    }

    public NoteDatabase getNoteDatabase() {
        return noteDatabase;
    }
}
