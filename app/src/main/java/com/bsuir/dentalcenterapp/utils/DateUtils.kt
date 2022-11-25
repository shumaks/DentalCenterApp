package com.bsuir.dentalcenterapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun isDatePast(date: String): Boolean {
    val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    val calendar = Calendar.getInstance()
    val currentDate = format.format(calendar.time)

    return date.toUpdateDate() < currentDate
}
