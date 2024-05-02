package com.imprarce.android.local.note

import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.note.room.NoteDao
import com.imprarce.android.local.note.room.NoteDbEntity
import com.imprarce.android.local.note.room.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepository {
    override suspend fun getAllNotes(): ResponseRoom<List<NoteDbEntity>> {
        return try {
            val response = noteDao.getAllNotes()
            ResponseRoom.Success(response)
        } catch (e : Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun getNoteById(id: Int): ResponseRoom<NoteDbEntity?> {
        return try {
            val response = noteDao.getNoteById(id)
            ResponseRoom.Success(response)
        } catch (e : Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun insertNote(note: NoteDbEntity): ResponseRoom<Unit> {
        return try {
            val response = noteDao.insertNote(note)
            ResponseRoom.Success(response)
        } catch (e : Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun updateNote(note: NoteDbEntity): ResponseRoom<Unit> {
        return try {
            val response = noteDao.updateNote(note)
            ResponseRoom.Success(response)
        } catch (e : Exception){
            ResponseRoom.Failure(e)
        }
    }

    override suspend fun deleteNote(note: NoteDbEntity): ResponseRoom<Unit> {
        return try {
            val response = noteDao.deleteNote(note)
            ResponseRoom.Success(response)
        } catch (e : Exception){
            ResponseRoom.Failure(e)
        }
    }
}