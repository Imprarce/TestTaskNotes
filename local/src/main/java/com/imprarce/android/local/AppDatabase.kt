package com.imprarce.android.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imprarce.android.local.note.room.NoteDao
import com.imprarce.android.local.note.room.NoteDbEntity

@Database(
    entities = [NoteDbEntity::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "note.db"
            )
                .build()
        }
    }
}