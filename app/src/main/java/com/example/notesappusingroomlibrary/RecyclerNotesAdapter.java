package com.example.notesappusingroomlibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerNotesAdapter extends RecyclerView.Adapter<RecyclerNotesAdapter.ViewHolder> {

    Context context;
    ArrayList<Note> noteArrayList;

    NoteDatabaseHelper noteDatabaseHelper;

    public RecyclerNotesAdapter(Context context, ArrayList<Note> noteArrayList, NoteDatabaseHelper noteDatabaseHelper)
    {
        this.context = context;
        this.noteArrayList = noteArrayList;
        this.noteDatabaseHelper = noteDatabaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewTitleNoteLayout.setText(noteArrayList.get(position).getTitle());
        holder.textViewDescNoteLayout.setText(noteArrayList.get(position).getDesc());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.edit_note_layout, null);
                dialog.setView(view);
                EditText titleEditLayout = view.findViewById(R.id.titleEditLayout);
                EditText descriptionEditLayout = view.findViewById(R.id.descriptionEditLayout);

                int curPosition = holder.getAdapterPosition();
                if(curPosition != RecyclerView.NO_POSITION)
                {
                    titleEditLayout.setText(noteArrayList.get(curPosition).getTitle());
                    descriptionEditLayout.setText(noteArrayList.get(curPosition).getDesc());
                    int id = noteArrayList.get(curPosition).getId();

                    dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            noteDatabaseHelper.noteDao().updateNote(new Note(id, titleEditLayout.getText().toString(),
                                    descriptionEditLayout.getText().toString()));
                            ((MainActivity)context).showNotes();
                        }
                    });

                    dialog.setNegativeButton("Cancel", null);
                    dialog.show();
                }
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();

                if(currentPosition != RecyclerView.NO_POSITION)
                {
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("Are you sure want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    noteDatabaseHelper.noteDao().deleteNote(new Note(noteArrayList.get(currentPosition).getId(),
                                            noteArrayList.get(currentPosition).getTitle(), noteArrayList.get(currentPosition).getDesc()));
                                    ((MainActivity)context).showNotes();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitleNoteLayout, textViewDescNoteLayout;
        ImageView imgEdit, imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitleNoteLayout = itemView.findViewById(R.id.textViewTitleNoteLayout);
            textViewDescNoteLayout = itemView.findViewById(R.id.textViewDescNoteLayout);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
