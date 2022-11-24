package com.bsuir.dentalcenterapp.utils

import android.text.TextUtils

fun String.isToothNumberCorrect(): Boolean = when (this) {
    "11", "12", "13", "14", "15", "16", "17", "18", "21", "22", "23", "24", "25", "26", "27", "28",
    "31", "32", "33", "34", "35", "36", "37", "38", "41", "42", "43", "44", "45", "46", "47", "48" -> true
    else -> false
}

fun String.isDateCorrect(): Boolean = this.length == 10 && this[0].isDigit() && this[1].isDigit() && this[2] == '/' && this[3].isDigit() && this[4].isDigit() && this[5] == '/' && this[6].isDigit() && this[7].isDigit() && this[8].isDigit() && this[9].isDigit()

fun String.isTimeCorrect(): Boolean = this.length == 5 && this[0].isDigit() && this[1].isDigit() && this[2] == ':' && this[3].isDigit() && this[4].isDigit()

fun String.isLoginCorrect(): Boolean = !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
