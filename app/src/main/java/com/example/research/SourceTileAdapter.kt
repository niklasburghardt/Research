package com.example.research

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class SourceTileAdapter(context:Context):BaseAdapter(){
    private val inflater = LayoutInflater.from(context)
    //hardcoded values that will be replaced by a relational database
    private val titles = arrayListOf("Architektur", "Politik", "Wirtschaft", "Recht", "Informatik", "Ingenieurswesen")
    private val links = arrayListOf("https://www.google.com/search?q=architektur", "https://www.google.com/search?q=Politik",
                "https://www.google.com/search?q=Wirtschaft", "https://www.google.com/search?q=Recht", "https://www.google.com/search?q=Informatik","" +
                "https://www.google.com/search?q=Ingenieurswesen")
    private val dates = arrayListOf("13.05", "13.05", "13.05", "13.05", "13.05", "13.05")
    private val listTiles = ArrayList<SourceData>()

    init {
        for(i:Int in 0 until titles.size){
            listTiles.add(SourceData(titles[i], links[i], dates[0]))
        }
    }

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
}