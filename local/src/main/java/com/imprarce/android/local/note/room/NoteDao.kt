package com.imprarce.android.local.note.room

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteDbEntity>

    @Query("SELECT * FROM notes WHERE id_note = :id")
    suspend fun getNoteById(id: Int): NoteDbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteDbEntity)

    @Update
    suspend fun updateNote(note: NoteDbEntity)

    @Delete
    suspend fun deleteNote(note: NoteDbEntity)
}
