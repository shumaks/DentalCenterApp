package com.bsuir.dentalcenterapp.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Patient
import com.itexus.dentalcenterapp.R

class PatientsAdapter(
    private val dataSet: MutableList<Patient>,
    private val onPatientClickListener: OnPatientClickListener
) : RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullname: TextView
        val phone: TextView

        init {
            fullname = view.findViewById(R.id.fullname_patient)
            phone = view.findViewById(R.id.phone_patient)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.patient_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.fullname.text = dataSet[position].fullName
        viewHolder.phone.text = dataSet[position].phone


        viewHolder.fullname.setOnClickListener {
            onPatientClickListener.onClick(dataSet[position])
        }

        viewHolder.phone.setOnClickListener {
            onPatientClickListener.onClick(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

    fun addData(data: List<Patient>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    interface OnPatientClickListener {
        fun onClick(patient: Patient)
    }
}
