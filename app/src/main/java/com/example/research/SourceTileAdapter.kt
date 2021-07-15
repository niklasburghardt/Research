package com.example.research

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.research.database.DatabaseOpenHelper
import kotlin.collections.ArrayList

class SourceTileAdapter(context:Context, val projectName: String):BaseAdapter(){
    private val inflater = LayoutInflater.from(context)

    private lateinit var listTiles: ArrayList<SourceData>
    private lateinit var db: DatabaseOpenHelper

    init {
        db = DatabaseOpenHelper(context)
        listTiles = ArrayList()
        viewData()
    }
    //hardcoded values that will be replaced by a relational database

    override fun getCount(): Int {
        return listTiles.size
    }

    override fun getItem(position: Int): Any {
        return listTiles.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var currentPosition = position
        val view: View
        val holder: ViewHolder
        if(convertView == null){
            view = inflater.inflate(R.layout.source_list_tile, parent, false)
            holder = ViewHolder()
            holder.name = view.findViewById(R.id.source_title)
            holder.link = view.findViewById(R.id.source_link)
            holder.date = view.findViewById(R.id.last_visited)
            view.tag = holder


        }else{
            holder = convertView.tag as ViewHolder
            view = convertView
        }
        var sourceTile = getItem(currentPosition) as SourceData
        holder.name.text = sourceTile.title
        holder.link.text = sourceTile.link
        holder.date.text = sourceTile.lastVisit
        return view

    }

    private class ViewHolder(){
        lateinit var name: TextView
        lateinit var link: TextView
        lateinit var date:TextView
    }

    private fun viewData():ArrayList<SourceData>{
        val cursor: Cursor = db.viewSourceData(projectName)
        if(cursor.count == 0){
            return ArrayList()
        }
        while(cursor.moveToNext()){
            listTiles.add(SourceData(cursor.getString(1), cursor.getString(3), "", cursor.getInt(0), cursor.getString(2)))
        }
        return listTiles
    }
}