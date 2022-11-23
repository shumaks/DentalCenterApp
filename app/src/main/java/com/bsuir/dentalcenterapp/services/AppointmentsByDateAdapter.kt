package com.bsuir.dentalcenterapp.services

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Appointment
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.itexus.dentalcenterapp.R


class AppointmentsByDateAdapter(
    private val dataSet: List<Appointment>,
    private val onEditClickListener: OnEditClickListener,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<AppointmentsByDateAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullname: TextView
        val diagnosis: TextView
        val time: TextView
        val buttonEdit: ImageView
        val buttonDelete: ImageView
        val letterPatient: MaterialLetterIcon

        init {
            fullname = view.findViewById(R.id.fullname)
            diagnosis = view.findViewById(R.id.diagnosis)
            time = view.findViewById(R.id.time)
            buttonEdit = view.findViewById(R.id.buttonEdit)
            buttonDelete = view.findViewById(R.id.buttonDelete)
            letterPatient = view.findViewById(R.id.letterPatient)
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
        viewHolder.buttonEdit.setOnClickListener {
            onEditClickListener.onClick(dataSet[position])
        }
        viewHolder.buttonDelete.setOnClickListener {
            onDeleteClickListener.onClick(dataSet[position])
        }
        viewHolder.letterPatient.letter = viewHolder.fullname.text.toString().first().toString()
    }

    override fun getItemCount() = dataSet.size

    interface OnEditClickListener {
        fun onClick(appointment: Appointment)
    }

    interface OnDeleteClickListener {
        fun onClick(appointment: Appointment)
    }
}
