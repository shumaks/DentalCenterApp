package com.bsuir.dentalcenterapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.App.Companion.context
import com.bsuir.dentalcenterapp.models.appointment.AppointmentResponseData
import com.itexus.dentalcenterapp.R

class AppointmentsAdapter(
    private val dataSet: List<AppointmentResponseData>,
    private val onEditClickListener: AppointmentsByDateAdapter.OnEditClickListener,
    private val onDeleteClickListener: AppointmentsByDateAdapter.OnDeleteClickListener
) : RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView
        val recyclerView: RecyclerView

        init {
            date = view.findViewById(R.id.date)
            recyclerView = view.findViewById(R.id.appointments_recycler_view_by_date)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.appointments_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.date.text = dataSet[position].title
        viewHolder.recyclerView.layoutManager = LinearLayoutManager(context)
        val appointments = dataSet[position].data
        viewHolder.recyclerView.adapter = AppointmentsByDateAdapter(appointments, onEditClickListener, onDeleteClickListener)
    }

    override fun getItemCount() = dataSet.size
}
