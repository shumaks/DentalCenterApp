package com.bsuir.dentalcenterapp.screens.appointment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.App
import com.bsuir.dentalcenterapp.models.appointment.Appointment
import com.bsuir.dentalcenterapp.models.appointment.AppointmentResponseData
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.adapters.AppointmentsAdapter
import com.bsuir.dentalcenterapp.adapters.AppointmentsByDateAdapter
import com.bsuir.dentalcenterapp.screens.appointment.calendar.DayViewContainer
import com.bsuir.dentalcenterapp.screens.appointment.calendar.MonthViewContainer
import com.itexus.dentalcenterapp.R
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class AppointmentsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var calendarView: CalendarView
    private lateinit var monthTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_appointments, container, false) as ViewGroup
        val toolbar: Toolbar = root.findViewById(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.appointments_log)
        (activity as AppointmentActivity).setSupportActionBar(toolbar)
        (activity as AppointmentActivity).supportActionBar?.show()

        recyclerView = root.findViewById(R.id.appointments_recycler_view)
        calendarView = root.findViewById(R.id.calendarView)
        monthTextView = root.findViewById(R.id.monthTextView)

        return root
    }

    override fun onResume() {
        super.onResume()

        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
            }
        }

        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                if (container.titlesContianer.tag == null) {
                    container.titlesContianer.tag = data.yearMonth
                    container.titlesContianer.children.map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek()[index]
                            val locale = if (getString(R.string.lang) == "RU") {
                                Locale.getDefault()
                            } else {
                                Locale.ENGLISH
                            }
                            val title = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                            textView.text = title
                        }
                }
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val daysOfWeek = daysOfWeek()
        calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        calendarView.scrollToMonth(currentMonth)

        calendarView.monthScrollListener = {
            monthTextView.text = if (getString(R.string.lang) == "RU") {
                it.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()).updateMonth() + " " + it.yearMonth.year
            } else {
                it.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + it.yearMonth.year
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        MainViewModel.observeAppointments()

        val onEditClickListener: AppointmentsByDateAdapter.OnEditClickListener =
            object : AppointmentsByDateAdapter.OnEditClickListener {
                override fun onClick(appointment: Appointment) {
                    val intent = Intent(activity, EditAppointmentActivity::class.java)
                    intent.putExtra("id", appointment.id)
                    startActivity(intent, null)
                }
            }
        val onDeleteClickListener: AppointmentsByDateAdapter.OnDeleteClickListener =
            object : AppointmentsByDateAdapter.OnDeleteClickListener {
                override fun onClick(appointment: Appointment) {
                    MainViewModel.deleteAppointment(appointment.id)
                    MainViewModel.observeAppointments()
                }
            }

        MainViewModel.appointmentsLiveData.observe(this) {
            (activity as AppointmentActivity).supportActionBar?.show()
            val filteredAppointments = mutableListOf<AppointmentResponseData>()
            it.sortByDate().forEach { response ->
                response.copy(data = response.data.filter {
                    it.doctor == App.currentDoctor.id
                }).let {
                    if (it.data.isNotEmpty()) {
                        filteredAppointments.add(response)
                    }
                }
            }
            recyclerView.adapter = AppointmentsAdapter(
                filteredAppointments,
                onEditClickListener,
                onDeleteClickListener
            )

            var selectedDate: LocalDate? = null

            calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
                override fun create(view: View) = DayViewContainer(view).also { container ->
                    view.setOnClickListener {
                        if (container.day.position == DayPosition.MonthDate) {
                            val currentSelection = selectedDate
                            if (currentSelection == container.day.date) {
                                selectedDate = null
                                recyclerView.adapter = AppointmentsAdapter(
                                    filteredAppointments,
                                    onEditClickListener,
                                    onDeleteClickListener
                                )
                                calendarView.notifyDateChanged(currentSelection)
                            } else {
                                selectedDate = container.day.date
                                calendarView.notifyDateChanged(container.day.date)
                                if (currentSelection != null) {
                                    calendarView.notifyDateChanged(currentSelection)
                                }
                            }
                        }
                    }
                }
                override fun bind(container: DayViewContainer, data: CalendarDay) {
                    container.day = data
                    container.textView.text = data.date.dayOfMonth.toString()
                    container.textView2.visibility = View.GONE
                    if (data.position == DayPosition.MonthDate) {
                        val day = data.date.dayOfMonth.toString()
                        val month = data.date.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
                        val year = data.date.year
                        val list = filteredAppointments.filter {

                            if (it.title.takeLast(4) != year.toString()) return@filter false

                            if (month.length == 1) {
                                if (it.title[3] != '0' || it.title[4].toString() != month) return@filter false
                            } else {
                                if(it.title.drop(3).take(2) != month) return@filter false
                            }

                            if (day.length == 1) {
                                if (it.title[0] != '0' || it.title[1].toString() != day) return@filter false
                            } else {
                                if(it.title.take(2) != day) return@filter false
                            }

                            return@filter true
                        }

                        if (data.date == selectedDate) {
                            container.textView.setTextColor(Color.WHITE)
                            container.cardView.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_blue))
                            recyclerView.adapter = AppointmentsAdapter(
                                list,
                                onEditClickListener,
                                onDeleteClickListener
                            )
                        } else {
                            container.textView.setTextColor(Color.BLACK)
                            container.cardView.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_grey))
                        }


                        container.textView2.visibility = if (list.isNotEmpty()) {
                            View.VISIBLE
                        } else {
                            View.INVISIBLE
                        }
                    } else {
                        container.textView.setTextColor(Color.GRAY)
                        container.textView2.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun String.updateMonth(): String = when (this) {
        "января" -> "Январь"
        "февраля" -> "Февраль"
        "марта" -> "Март"
        "апреля" -> "Апрель"
        "мая" -> "Май"
        "июня" -> "Июнь"
        "июля" -> "Июль"
        "августа" -> "Август"
        "сентября" -> "Сентябрь"
        "октября" -> "Октябрь"
        "ноября" -> "Ноябрь"
        else -> "Декабрь"
    }

    private fun List<AppointmentResponseData>.sortByDate(): List<AppointmentResponseData> =
        this.sortedByDescending {
            val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(it.title, dateTimeFormatter)
        }
}