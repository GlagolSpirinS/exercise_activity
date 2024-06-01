package com.example.aboba;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query")
    List<Note> searchNotes(String query);

    @Query("SELECT * FROM notes WHERE date = :date")
    List<Note> filterNotesByDate(String date);

    @Query("SELECT * FROM notes WHERE (title LIKE :query OR description LIKE :query) AND (:date IS NULL OR date = :date)")
    List<Note> searchNotesWithDate(String query, String date);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}