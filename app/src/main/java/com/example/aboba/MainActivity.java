package com.example.aboba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;
    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        });

        loadNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            int icon = data.getIntExtra("icon", R.drawable.ic_default_icon);
            String date = data.getStringExtra("date");

            if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
                Note note = new Note(title, description, icon, date);
                saveNoteToDatabase(note);
            } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
                int id = data.getIntExtra("id", -1);
                if (id != -1) {
                    Note note = new Note(title, description, icon, date);
                    note.setId(id);
                    updateNoteInDatabase(note);
                }
            }
        }
    }

    private void loadNotes() {
        Executors.newSingleThreadExecutor().execute(() -> {
            noteList = DatabaseClient.getInstance(getApplicationContext()).getNoteDatabase().noteDao().getAllNotes();
            runOnUiThread(() -> {
                adapter = new NoteAdapter(noteList, this);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    private void saveNoteToDatabase(Note note) {
        Executors.newSingleThreadExecutor().execute(() -> {
            DatabaseClient.getInstance(getApplicationContext()).getNoteDatabase().noteDao().insert(note);
            runOnUiThread(this::loadNotes);
        });
    }

    private void updateNoteInDatabase(Note note) {
        Executors.newSingleThreadExecutor().execute(() -> {
            DatabaseClient.getInstance(getApplicationContext()).getNoteDatabase().noteDao().update(note);
            runOnUiThread(this::loadNotes);
        });
    }

    private void deleteNoteFromDatabase(Note note) {
        Executors.newSingleThreadExecutor().execute(() -> {
            DatabaseClient.getInstance(getApplicationContext()).getNoteDatabase().noteDao().delete(note);
            runOnUiThread(this::loadNotes);
        });
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("description", note.getDescription());
        intent.putExtra("icon", note.getIcon());
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
    }

    @Override
    public void onDeleteClick(Note note) {
        deleteNoteFromDatabase(note);
    }
}

