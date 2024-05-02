package com.imprarce.android.feature_notes.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtil {
    companion object {

        fun formatDate(inputDate: Date?): String {
            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(inputDate.toString())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return outputFormat.format(date)
        }

        fun getCurrentDate(): String {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }
    }
}