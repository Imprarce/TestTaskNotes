package com.imprarce.android.feature_notes.utils

import com.imprarce.android.local.note.NoteItem
import com.imprarce.android.local.note.room.NoteDbEntity

object Converter {
    fun convertToNotesItemList(noteDbList: List<NoteDbEntity>): List<NoteItem> {
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

    fun convertToNoteItem(noteDbEntity: NoteDbEntity): NoteItem {
        return NoteItem(
            note_id = noteDbEntity.noteId,
            title = noteDbEntity.title,
            description = noteDbEntity.description,
            priority = noteDbEntity.priority,
            date = noteDbEntity.date
        )
    }
}