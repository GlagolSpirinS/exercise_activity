package com.example.aboba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aboba.AddNoteActivity;
import com.example.aboba.DatabaseClient;
import com.example.aboba.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        loadNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            int icon = data.getIntExtra("icon", R.drawable.ic_default_icon);

            Note note = new Note(title, description, icon);
            saveNoteToDatabase(note);
        }
    }

    private void loadNotes() {
        Executors.newSingleThreadExecutor().execute(() -> {
            noteList = DatabaseClient.getInstance(getApplicationContext()).getNoteDatabase().noteDao().getAllNotes();
            runOnUiThread(() -> {
                adapter = new NoteAdapter(noteList);
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
}
