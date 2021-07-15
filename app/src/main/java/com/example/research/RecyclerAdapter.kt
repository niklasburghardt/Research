package com.example.research


import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.research.database.DatabaseOpenHelper
import kotlin.coroutines.coroutineContext


class RecyclerAdapter(context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    //hardcoded values will be replaced by database later

    private var mCon: Context = context
    private lateinit var listTiles: ArrayList<ProjectData>
    private lateinit var db: DatabaseOpenHelper
    init {
        db = DatabaseOpenHelper(context)
        listTiles = ArrayList()
        viewData()
        getAmountSources()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.project_card, viewGroup, false)
        val vh = ViewHolder(v)
        v.setOnClickListener {
            val intent = Intent(mCon, Project::class.java)
            intent.putExtra("NAME", vh.projectTitle.text)
            intent.putExtra("PROJECT_ID", vh.id.toString())
            startActivity(mCon, intent, null)

        }


        return vh
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.projectTitle.text = listTiles[i].title
        viewHolder.projectDetail.text = listTiles[i].details
        viewHolder.dueDate.text = listTiles[i].dueDate
        viewHolder.id = listTiles[i].id
    }
    override fun getItemCount(): Int {
        return listTiles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var projectTitle: TextView
        var projectDetail: TextView
        var dueDate: TextView
        var id: Int = 0

        init {

            projectTitle = itemView.findViewById(R.id.project_title)
            projectDetail = itemView.findViewById(R.id.project_detail)
            dueDate = itemView.findViewById(R.id.project_due_date)
        }
    }
    private fun viewData():ArrayList<ProjectData>{
        val cursor: Cursor = db.viewProjectData()
        if(cursor.count == 0){
            return ArrayList()
        }
        while(cursor.moveToNext()){
            listTiles.add(ProjectData(cursor.getString(1), "13 Quellenangaben", cursor.getString(3), cursor.getInt(0)))
        }
        return listTiles
    }

    private fun getAmountSources(): ArrayList<ProjectData>{
        for(i: Int in 0 until listTiles.size) {
            val cursor: Cursor = db.getAmountOfSources(listTiles[i].id)
            if (cursor.count == 0) {
                return listTiles
            }
            while (cursor.moveToNext()) {
                listTiles[i].details = cursor.getString(0) +" Quellenangaben"
            }
        }
        return listTiles
    }



}

