package com.imprarce.android.feature_notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.note.NoteItem
import com.imprarce.android.local.note.room.NoteDbEntity
import com.imprarce.android.local.note.room.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private var _noteList = MutableLiveData<List<NoteItem>>()
    val noteList: LiveData<List<NoteItem>> = _noteList

    private var _noteItem = MutableLiveData<NoteItem>()
    val noteItem: LiveData<NoteItem> = _noteItem

    init {
        getNotesList()
    }

    private fun getNotesList() {
        viewModelScope.launch {
            when (val response = noteRepository.getAllNotes()) {
                is ResponseRoom.Success -> {
                    val noteDbList = response.result
                    _noteList.value = convertToNotesItemList(noteDbList)
                }
                is ResponseRoom.Failure -> {
                    Log.e("NoteViewModel", "Failed to load notes: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    private fun convertToNotesItemList(noteDbList: List<NoteDbEntity>): List<NoteItem> {
        return noteDbList.map { noteDbEntity ->
            NoteItem(
                note_id = noteDbEntity.noteId,
                title = noteDbEntity.title,
                description = noteDbEntity.description,
                priority = noteDbEntity.priority,
                date = noteDbEntity.date
            )
        }
    }

    private fun convertToNoteItem(noteDbEntity: NoteDbEntity): NoteItem {
        return NoteItem(
                note_id = noteDbEntity.noteId,
                title = noteDbEntity.title,
                description = noteDbEntity.description,
                priority = noteDbEntity.priority,
                date = noteDbEntity.date
            )
        }

    fun addNewNote(title: String, description: String, priority: Int, currentTime: String) {
        val note = NoteDbEntity(
            title = title,
            description = description,
            priority = priority,
            date = currentTime
        )
        viewModelScope.launch {
            noteRepository.insertNote(note)
            getNotesList()
        }
    }

    fun deleteNote(noteItem: NoteItem) {
        val noteDbEntity = NoteDbEntity(
            noteId = noteItem.note_id,
            title = noteItem.title,
            description = noteItem.description,
            priority = noteItem.priority,
            date = noteItem.date
        )

        viewModelScope.launch {
            when (val response = noteRepository.deleteNote(noteDbEntity)) {
                is ResponseRoom.Success -> {
                    getNotesList()
                }
                is ResponseRoom.Failure -> {
                    Log.e("NoteViewModel", "Failed to delete note: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    fun updateNote(noteItem: NoteItem) {
        val noteDbEntity = NoteDbEntity(
            noteId = noteItem.note_id,
            title = noteItem.title,
            description = noteItem.description,
            priority = noteItem.priority,
            date = noteItem.date
        )

        viewModelScope.launch {
            when (val response = noteRepository.updateNote(noteDbEntity)) {
                is ResponseRoom.Success -> {
                    getNotesList()
                }
                is ResponseRoom.Failure -> {
                    Log.e("NoteViewModel", "Failed to update note: ${response.exception}")
                }
                is ResponseRoom.Loading -> {

                }
            }
        }
    }

    fun getNoteItem(noteId: Int){
        viewModelScope.launch {
            when(val response = noteRepository.getNoteById(noteId)){
                is ResponseRoom.Success -> {
                    val noteDb = response.result
                    if(noteDb != null){
                        _noteItem.value = convertToNoteItem(noteDb)
                    }
                }
                is ResponseRoom.Failure -> {
                    Log.e("MainViewModel", "Failed to load note: ${response.exception}")
                }
                else -> {}
            }
        }
    }
}