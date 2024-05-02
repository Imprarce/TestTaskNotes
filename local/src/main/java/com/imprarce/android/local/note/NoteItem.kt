package com.imprarce.android.local.note

import java.util.*

data class NoteItem(
    val note_id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val date: String
)