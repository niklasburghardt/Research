package com.example.research

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.research.Dialogs.AlertFragment
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Project : AppCompatActivity() {
    private lateinit var sources: ListView
    private lateinit var addNewSource: FloatingActionButton
    private lateinit var adapter: SourceTileAdapter
    private lateinit var alertFragment: AlertFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        val appName: String = intent.getStringExtra("NAME").toString()
        setTitle(appName)
        adapter = SourceTileAdapter(this, appName)

        val list = findViewById<ListView>(R.id.sources_list)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val source = adapter.getItem(position) as SourceData
            val title = source.title
            val link = source.link
            intent = Intent(this, Source::class.java)
            intent.putExtra("NAME", title)
            intent.putExtra("LINK", link)
            startActivity(intent)
        }



        addNewSource = findViewById(R.id.newSourceProject)

        addNewSource.setOnClickListener(fun(_:View){
            intent = Intent(this, AddNewSource::class.java)
            intent.putExtra("FAVORITE", false)
            intent.putExtra("PROJECT", "projectna")
            startActivity(intent)
        })
        alertFragment = AlertFragment()
        list.onItemLongClickListener = AdapterView.OnItemLongClickListener {_, _, position, _ ->
            alertFragment.show(supportFragmentManager, AlertFragment.TAG)
            return@OnItemLongClickListener true
        }

    }

    private fun openSourcePage(sourceTitle:String) {
        intent = Intent(this, Source::class.java)
        intent.putExtra("NAME", sourceTitle)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.details -> openDetailsPage()
            R.id.delete_project -> return true
            else -> return true
        }
    }

    override fun onSearchRequested(): Boolean {
        return super.onSearchRequested()
    }

    private fun openDetailsPage(): Boolean {
        intent = Intent(this, ProjectDetails::class.java)
        startActivity(intent)
        return true
    }


}