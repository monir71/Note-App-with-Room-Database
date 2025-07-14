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
