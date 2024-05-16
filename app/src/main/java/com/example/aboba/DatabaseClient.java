package com.example.aboba;

import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient instance;

    private NoteDatabase noteDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        // Создаем объект базы данных
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "NoteDB").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public NoteDatabase getNoteDatabase() {
        return noteDatabase;
    }
}
