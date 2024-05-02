package com.imprarce.android.local.note.room

import com.imprarce.android.local.ResponseRoom

interface NoteRepository {

    suspend fun getAllNotes(): ResponseRoom<List<NoteDbEntity>>

    suspend fun getNoteById(id: Int): ResponseRoom<NoteDbEntity?>

    suspend fun insertNote(note: NoteDbEntity): ResponseRoom<Unit>

    suspend fun updateNote(note: NoteDbEntity): ResponseRoom<Unit>

    suspend fun deleteNote(note: NoteDbEntity): ResponseRoom<Unit>
}