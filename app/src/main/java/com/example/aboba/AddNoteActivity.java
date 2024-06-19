package com.example.aboba;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private ImageView imageViewIcon;
    private int selectedIcon = R.drawable.ic_default_icon;
    private int noteId = -1;
    private int icon = R.drawable.ic_date;
    private TextView textViewDate;

    private NumberPicker numberPickerExerciseTime;

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
        textViewDate.setOnClickListener(v -> showDatePickerDialog());
        String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        textViewDate.setText(currentDate);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewIcon = findViewById(R.id.imageViewIcon);

        imageViewIcon.setOnClickListener(v -> showIconDialog());

        FloatingActionButton fab = findViewById(R.id.fabSave);
        fab.setOnClickListener(v -> saveNote());

        numberPickerExerciseTime = findViewById(R.id.numberPickerExerciseTime);
        numberPickerExerciseTime.setMinValue(0);
        numberPickerExerciseTime.setMaxValue(120);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            noteId = intent.getIntExtra("id", -1);
            editTextTitle.setText(intent.getStringExtra("title"));
            editTextDescription.setText(intent.getStringExtra("description"));
            selectedIcon = intent.getIntExtra("icon", R.drawable.ic_default_icon);
            imageViewIcon.setImageResource(selectedIcon);
        }
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddNoteActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", selectedDay, selectedMonth + 1, selectedYear);
                    textViewDate.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = textViewDate.getText().toString();
        int exerciseTime = numberPickerExerciseTime.getValue();

        Note note = new Note(title, description, selectedIcon, date, exerciseTime);

        Log.d("AddNoteActivity", "Сохранение заметки: " + note.toString());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("id", noteId);
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("description", description);
        resultIntent.putExtra("icon", selectedIcon);
        resultIntent.putExtra("date", date);
        resultIntent.putExtra("exerciseTime", exerciseTime);
        setResult(RESULT_OK, resultIntent);
        finish();
        Log.d("AddNoteActivity", "Заметка успешно сохранена!");
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
            builder.create().dismiss();
        });

        builder.create().show();
    }
}
