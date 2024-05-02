package com.imprarce.android.local.note.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDbEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_note") val noteId : Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "priority") val priority: Int
)