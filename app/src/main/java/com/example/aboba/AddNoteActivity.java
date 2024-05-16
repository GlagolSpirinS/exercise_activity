package com.example.aboba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private ImageView imageViewIcon;
    private int selectedIcon = R.drawable.ic_default_icon;

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

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewIcon = findViewById(R.id.imageViewIcon);

        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIconDialog();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabSave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", title);
                resultIntent.putExtra("description", description);
                resultIntent.putExtra("icon", selectedIcon);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
    private void showIconDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_icon_picker, null);
        builder.setView(dialogView);

        GridView gridView = dialogView.findViewById(R.id.gridView);
        IconAdapter iconAdapter = new IconAdapter(this, iconArray);
        gridView.setAdapter(iconAdapter);

        AlertDialog dialog = builder.create();
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIcon = iconArray[position];
            imageViewIcon.setImageResource(selectedIcon);
            dialog.dismiss();
        });

        dialog.show();
    }
}
