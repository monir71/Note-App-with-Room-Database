# 📝 Note App with Room Database

A simple yet powerful **Note-Taking Android Application** built with **Java**, leveraging the **Room Persistence Library** to manage local data efficiently and reliably.
Create, Read, Update, and Delete notes — all in a clean, responsive UI.

---

## 🚀 Features

* ✅ Create notes with a **title** and **multi-line description**
* ✏️ Edit notes via intuitive **dialog boxes**
* 🗑️ Delete notes with confirmation
* 📦 Uses **Room Database** for offline data persistence
* 🧠 Auto-refreshes note list after each change
* 🪄 Displays empty-state UI when no notes are available
* 🎨 Material Design: FAB, CardView, Dialogs

---

## 📸 UI

<img width="373" height="659" alt="empty_view" src="https://github.com/user-attachments/assets/fbba940b-eaea-4f2a-aac3-4ba1c1febe97" />
<img width="371" height="832" alt="notes" src="https://github.com/user-attachments/assets/9c5491b7-7c52-464f-96bc-a4ae65743602" />
<img width="372" height="831" alt="add_note" src="https://github.com/user-attachments/assets/84e46269-3f9f-4e96-b09c-329786a4ebdd" />
<img width="376" height="822" alt="edit_notes" src="https://github.com/user-attachments/assets/36cd700e-f266-4782-b267-7915281b12dc" />
<img width="369" height="827" alt="delete_note" src="https://github.com/user-attachments/assets/019b370d-eeec-4e75-9bfb-5b61af127fb9" />

---

## 🏗 Tech Stack

* **Language**: Java
* **Database**: Room (`room-runtime`, `room-compiler`)
* **UI Components**: RecyclerView, CardView, FAB, AlertDialog
* **Architecture**: Minimal, single-activity + adapter

---

## 📦 Dependencies

```gradle
implementation "androidx.room:room-runtime:2.7.2"
annotationProcessor "androidx.room:room-compiler:2.7.2"
```

Or if using Gradle Version Catalog:

```gradle
implementation(libs.room.runtime)
annotationProcessor(libs.room.compiler)
```

---

## 🛠 Project Structure

```
com.example.notesappusingroomlibrary
├── MainActivity.java         // App entry point and logic
├── Note.java                 // Room Entity
├── NoteDao.java              // Room DAO Interface
├── NoteDatabaseHelper.java   // Room DB Singleton
├── RecyclerNotesAdapter.java // RecyclerView Adapter
```

---

## 🧪 Core Functionalities

### 📋 Add New Note

* Floating Action Button (FAB) opens a full-width dialog
* User inputs title & description
* Saves to Room DB

### 🖊 Edit Existing Note

* Tap the ✏️ icon on a note card
* Opens pre-filled dialog
* Updates Room entry

### 🧹 Delete Note

* Tap the 🗑 icon
* Confirms via AlertDialog
* Deletes from DB

---

## 🧩 UI Layouts

* `activity_main.xml` – RecyclerView + FAB + empty view fallback
* `note_layout.xml` – CardView layout for each note
* `input_note_layout.xml` – Dialog layout for creating a note
* `edit_note_layout.xml` – Dialog layout for editing a note

---

## 🔥 How to Run

1. Clone this repo

   ```bash
   git clone https://github.com/monir71/Note-App-with-Room-Database.git
   ```

2. Open in **Android Studio**

3. Click **Run**

---

## 💡 Future Improvements (optional)

* Add search functionality 🔍
* Add note timestamps 🕒
* Migrate to MVVM with LiveData and ViewModel 🔄
* Export/import notes to local storage 📂

---

## 🙌 Acknowledgments

Huge thanks to:

* Android Room team for making local storage fun again
* RecyclerView for making lists scroll like butter
* You — for reading this far ❤️

---

## 📃 License

This project is open-source and free to use for educational or personal projects.

---

Details code here:

Note App
with Room Database

```
implementation("androidx.room:room-runtime:2.7.2")
annotationProcessor("androidx.room:room-compiler:2.7.2")
```
OR
```
implementation(libs.room.runtime)
annotationProcessor(libs.room.compiler)
```

activity_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:visibility="gone"/>

    <LinearLayout
        android:id="@+id/linearLayoutEmptyNotes"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:gravity="center"
        android:layout_centerInParent="true">

        <TextView
            android:id="@+id/emptyNotesView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/empty_notes"
            android:textStyle="bold"
            android:textSize="25sp"/>

        <TextView
            android:id="@+id/nothingFoundView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/nothing_found"/>

        <Button
            android:id="@+id/btnCreate"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/btn_create"
            android:textSize="20sp"
            app:backgroundTint="#127E89"/>

    </LinearLayout>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/btnFab"
        android:contentDescription="@string/create_new_note_fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentEnd="true"
        app:tint="#FFFFFF"
        app:backgroundTint="#127E89"
        app:srcCompat="@drawable/svg_plus"
        android:layout_margin="5sp"/>

</RelativeLayout>
```

MainActivity.java

```
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
		
		//Custom method for initializing all the needful
        initVar();
		
		//Custom method for showing/reloading notes
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
				
				//Setting the dialog with params MATCH_PARENT
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				//btnCreate will perform the same job as btnFab
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
```

Note.java (Entity)

```
package com.example.notesappusingroomlibrary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")

public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="desc")
    private String desc;

    Note(int id, String title, String desc)
    {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    @Ignore
    Note(String title, String desc)
    {
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

```

NoteDao.java

```
package com.example.notesappusingroomlibrary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

}

```

NoteDatabaseHelper.java

```
package com.example.notesappusingroomlibrary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, exportSchema = false, version = 1)
public abstract class NoteDatabaseHelper extends RoomDatabase {

    private static final String DATABASE_NAME = "NoteDB";
    private static NoteDatabaseHelper instance;

    public static synchronized NoteDatabaseHelper getDB(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context, NoteDatabaseHelper.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();

}

```

RecyclerNotesAdapter.java

```
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
				
				//Do not treat position as fixed - only use immediately and call holder.getAdapterPosition() to look it up later
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
			
				//Do not treat position as fixed - only use immediately and call holder.getAdapterPosition() to look it up later
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

```

note_layout.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">
    
    <androidx.cardview.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="200sp"
        app:cardElevation="7sp"
        app:cardCornerRadius="20sp"
        app:cardUseCompatPadding="true">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            android:padding="10sp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:layout_gravity="end"
                android:gravity="end"
                android:background="#127E89"
                android:layout_marginBottom="15sp">

                <ImageView
                    android:id="@+id/imgEdit"
                    android:layout_width="20sp"
                    android:layout_height="20sp"
                    android:src="@drawable/svg_edit"
                    android:contentDescription="edit"
                    android:layout_marginEnd="10sp"
                    app:tint="#FFF"/>

                <ImageView
                    android:id="@+id/imgDelete"
                    android:layout_width="20sp"
                    android:layout_height="20sp"
                    android:src="@drawable/baseline_delete_forever_24"
                    android:contentDescription="delete"
                    app:tint="#FFF"/>

            </LinearLayout>

            <TextView
                android:id="@+id/textViewTitleNoteLayout"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textStyle="bold"
                android:layout_marginBottom="5sp"
                android:textAlignment="center"/>

            <TextView
                android:id="@+id/textViewDescNoteLayout"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>

        </LinearLayout>

    </androidx.cardview.widget.CardView>

</LinearLayout>
```

input_note_layout.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/insert_notes"
        android:textStyle="bold"
        android:textSize="25sp"
        android:textAlignment="center"
        android:layout_gravity="center"
        android:layout_marginBottom="10sp"
        android:background="#127E89"
        android:textColor="#FFF"/>

    <EditText
        android:id="@+id/titleEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/note_title"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="5sp"
        android:textSize="20sp"/>

    <EditText
        android:id="@+id/descriptionEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/note_description"
        android:background="@drawable/edittext_background"
        android:inputType="textMultiLine"
        android:lines="5"
        android:maxLines="10"
        android:minLines="5"
        android:scrollbars="vertical"
        android:overScrollMode="always"
        android:textSize="20sp"
        android:layout_marginBottom="20sp"/>

    <Button
        android:id="@+id/btnAdd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/add_note"
        android:layout_gravity="center"/>

</LinearLayout>
```

edit_note_layout.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="5sp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Edit Note"
        android:textStyle="bold"
        android:textSize="25sp"
        android:textAlignment="center"
        android:layout_gravity="center"
        android:layout_marginBottom="10sp"
        android:background="#127E89"
        android:textColor="#FFF"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Title"
        android:textSize="20sp"
        android:layout_marginBottom="10sp"/>

    <EditText
        android:id="@+id/titleEditLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/note_title"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="10sp"
        android:textSize="20sp"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Description"
        android:textSize="20sp"
        android:layout_marginBottom="10sp"/>

    <EditText
        android:id="@+id/descriptionEditLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/note_description"
        android:background="@drawable/edittext_background"
        android:inputType="textMultiLine"
        android:lines="5"
        android:maxLines="10"
        android:minLines="5"
        android:scrollbars="vertical"
        android:overScrollMode="always"
        android:textSize="20sp"
        android:layout_marginBottom="20sp"/>

</LinearLayout>
```
