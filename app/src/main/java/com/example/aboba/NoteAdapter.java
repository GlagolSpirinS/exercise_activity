package com.example.aboba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aboba.Note;
import com.example.aboba.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private OnNoteClickListener onNoteClickListener;

    public NoteAdapter(List<Note> noteList, OnNoteClickListener onNoteClickListener) {
        this.noteList = noteList;
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.icon.setImageResource(note.getIcon());
        holder.date.setText(note.getDate());
        holder.exerciseTime.setText("Время: " + note.getExerciseTime() + " мин");
        holder.itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(note));
        holder.deleteIcon.setOnClickListener(v -> onNoteClickListener.onDeleteClick(note));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date, exerciseTime; // добавьте exerciseTime
        ImageView icon, deleteIcon;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);
            date = itemView.findViewById(R.id.note_date);
            exerciseTime = itemView.findViewById(R.id.note_exercise_time); // инициализируйте exerciseTime
            icon = itemView.findViewById(R.id.note_icon);
            deleteIcon = itemView.findViewById(R.id.noteDeleteIcon);
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
        void onDeleteClick(Note note);
    }
}
