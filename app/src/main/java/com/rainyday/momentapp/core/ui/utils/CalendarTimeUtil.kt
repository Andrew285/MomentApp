package com.rainyday.momentapp.core.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(): String {
    val formatter = SimpleDateFormat("dd / MM / yyyy", Locale.forLanguageTag("uk-UA"))
    return formatter.format(Date(this))
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun Long.toUkrainianDate(): String {
//    val formatter = DateTimeFormatter
//        .ofPattern("d MMMM yyyy")
//        .withLocale(Locale.forLanguageTag("uk-UA"))
//    return Instant.ofEpochMilli(this)
//        .atZone(ZoneId.systemDefault())
//        .format(formatter)
//}