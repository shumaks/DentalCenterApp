package com.bsuir.dentalcenterapp.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Appointment
import com.itexus.dentalcenterapp.R
import org.w3c.dom.Text

class AppointmentsByDateAdapter(
    private val dataSet: List<Appointment>
) : RecyclerView.Adapter<AppointmentsByDateAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullname: TextView
        val diagnosis: TextView
        val time: TextView


        init {
            fullname = view.findViewById(R.id.fullname)
            diagnosis = view.findViewById(R.id.diagnosis)
            time = view.findViewById(R.id.time)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.appointments_by_date_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.fullname.text = dataSet[position].patient.fullName
        viewHolder.diagnosis.text = dataSet[position].diagnosis
        viewHolder.time.text = dataSet[position].time
    }

    override fun getItemCount() = dataSet.size
}
