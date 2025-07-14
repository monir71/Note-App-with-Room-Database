package com.example.notesappusingroomlibrary;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCreate;
    RecyclerView recyclerView;
    LinearLayout linearLayoutEmptyNotes;
    FloatingActionButton btnFab;

    NoteDatabaseHelper noteDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initVar();

        showNotes();

        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.input_note_layout);

                EditText titleEditText, descriptionEditText;
                Button btnAdd;
                titleEditText = dialog.findViewById(R.id.titleEditText);
                descriptionEditText = dialog.findViewById(R.id.descriptionEditText);
                btnAdd = dialog.findViewById(R.id.btnAdd);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = titleEditText.getText().toString();
                        String desc = descriptionEditText.getText().toString();
                        if(!desc.isEmpty())
                        {
                            noteDatabaseHelper.noteDao().insertNote(new Note(title, desc));
                            showNotes();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Description should not be empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFab.performClick();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showNotes()
    {
        ArrayList<Note> notes = (ArrayList<Note>) noteDatabaseHelper.noteDao().getAllNotes();

        if(notes.size() > 0)
        {
            linearLayoutEmptyNotes.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new RecyclerNotesAdapter(this, notes, noteDatabaseHelper));
        }
        else
        {
            linearLayoutEmptyNotes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void initVar()
    {
        btnCreate = findViewById(R.id.btnCreate);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutEmptyNotes = findViewById(R.id.linearLayoutEmptyNotes);
        btnFab = findViewById(R.id.btnFab);
        noteDatabaseHelper = NoteDatabaseHelper.getDB(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}