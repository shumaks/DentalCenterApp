package com.bsuir.dentalcenterapp.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.AppointmentModel
import com.itexus.dentalcenterapp.R

class AppointmentsAdapter(
    private val dataSet: List<AppointmentModel>
) : RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView
        val fullname: TextView
        val diagnosis: TextView
        val time: TextView

        init {
            date = view.findViewById(R.id.date)
            fullname = view.findViewById(R.id.fullname)
            diagnosis = view.findViewById(R.id.diagnosis)
            time = view.findViewById(R.id.time)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.appointments_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.date.text = dataSet[position].date
        viewHolder.fullname.text = dataSet[position].fullname
        viewHolder.diagnosis.text = dataSet[position].diagnosis
        viewHolder.time.text = dataSet[position].time
    }

    override fun getItemCount() = dataSet.size
}
