package com.bsuir.dentalcenterapp.services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Appointment
import com.bsuir.dentalcenterapp.models.Patient
import com.itexus.dentalcenterapp.R

class PatientsAppointmentsAdapter(
    private val dataSet: List<Appointment>
) : RecyclerView.Adapter<PatientsAppointmentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val toothNumber: TextView
        val diagnosis: TextView
        val dateTime: TextView
        val price: TextView

        init {
            toothNumber = view.findViewById(R.id.tooth_number)
            diagnosis = view.findViewById(R.id.diagnosis)
            dateTime = view.findViewById(R.id.date_time)
            price = view.findViewById(R.id.price)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.patient_appointments_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.toothNumber.text = "${viewHolder.toothNumber.text}${dataSet[position].dentNumber}"
        viewHolder.diagnosis.text = "${viewHolder.diagnosis.text}${dataSet[position].diagnosis}"
        viewHolder.dateTime.text = "${dataSet[position].date} - ${dataSet[position].time}"
        viewHolder.price.text = "${dataSet[position].price} BYN"
    }

    override fun getItemCount() = dataSet.size
}
