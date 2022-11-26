package com.bsuir.dentalcenterapp.screens.appointment.calendar

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.itexus.dentalcenterapp.R
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val cardView = view.findViewById<CardView>(R.id.cardView)
    val textView = view.findViewById<TextView>(R.id.calendarDayText)
    val textView2 = view.findViewById<TextView>(R.id.calendarDayText2)
    lateinit var day: CalendarDay
}
