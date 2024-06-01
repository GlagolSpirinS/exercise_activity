package com.example.aboba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private ImageView imageViewIcon;
    private int selectedIcon = R.drawable.ic_default_icon;
    private int noteId = -1;
    private TextView textViewDate;

    private int[] iconArray = {
            R.drawable.meditation,
            R.drawable.runing,
            R.drawable.siting,
            R.drawable.swimming,
            R.drawable.walking,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        textViewDate = findViewById(R.id.textViewDate);
        String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        textViewDate.setText(currentDate);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewIcon = findViewById(R.id.imageViewIcon);

        imageViewIcon.setOnClickListener(v -> showIconDialog());

        FloatingActionButton fab = findViewById(R.id.fabSave);
        fab.setOnClickListener(v -> saveNote());

        // Получение данных из Intent для редактирования
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            noteId = intent.getIntExtra("id", -1);
            editTextTitle.setText(intent.getStringExtra("title"));
            editTextDescription.setText(intent.getStringExtra("description"));
            selectedIcon = intent.getIntExtra("icon", R.drawable.ic_default_icon);
            imageViewIcon.setImageResource(selectedIcon);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            // Покажите сообщение об ошибке, например, с помощью Toast
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return; // Прекратите сохранение, если поля пустые
        }

        String date = textViewDate.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("id", noteId);
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("description", description);
        resultIntent.putExtra("icon", selectedIcon);
        resultIntent.putExtra("date", date);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void showIconDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_icon_picker, null);
        builder.setView(dialogView);

        GridView gridView = dialogView.findViewById(R.id.gridView);
        IconAdapter iconAdapter = new IconAdapter(this, iconArray);
        gridView.setAdapter(iconAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIcon = iconArray[position];
            imageViewIcon.setImageResource(selectedIcon);
            // Закрытие диалога после выбора иконки
            builder.create().dismiss();
        });

        builder.create().show();
    }
}
