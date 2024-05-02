package com.imprarce.android.feature_notes.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtil {
    companion object {

        fun getCurrentDate(): String {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }
    }
}