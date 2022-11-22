package com.bsuir.dentalcenterapp.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Patient
import com.itexus.dentalcenterapp.R
import java.lang.Character.toLowerCase
import java.util.*
import kotlin.collections.ArrayList

class PatientsAdapter(
    private val dataSet: MutableList<Patient>,
    private val onPatientClickListener: OnPatientClickListener
) : RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {

    val initialDataSet = ArrayList<Patient>().apply {
        addAll(dataSet)
    }

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

    fun getFilter(): Filter {
        return patientsFilter
    }

    private val patientsFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Patient> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                initialDataSet.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().toLowerCase()
                initialDataSet.forEach {
                    if (it.fullName.toLowerCase(Locale.ROOT).contains(query) || it.phone.contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                dataSet.clear()
                dataSet.addAll(results.values as Collection<Patient>)
                notifyDataSetChanged()
            }
        }
    }

    fun addData(data: List<Patient>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    interface OnPatientClickListener {
        fun onClick(patient: Patient)
    }
}
