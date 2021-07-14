package com.example.research


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    //hardcoded values will be replaced by database later

    private val titles = arrayOf("Rom", "Studiengänge", "Geschichte")

    private val details = arrayOf("13 Quellen", "6 Quellen", "4 Quellen")

    private val dueDate = arrayOf("bis 13.05", "bis 09.02", "bis 03.05")

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.project_card, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.projectTitle.text = titles[i]
        viewHolder.projectDetail.text = details[i]
        viewHolder.dueDate.text = dueDate[i]
    }
    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var projectTitle: TextView
        var projectDetail: TextView
        var dueDate: TextView

        init {

            projectTitle = itemView.findViewById(R.id.project_title)
            projectDetail = itemView.findViewById(R.id.project_detail)
            dueDate = itemView.findViewById(R.id.project_due_date)
        }
    }



}

