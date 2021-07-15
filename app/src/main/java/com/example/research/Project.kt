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
    private lateinit var appName:String
    private lateinit var projectId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        appName = intent.getStringExtra("NAME").toString()
        projectId = intent.getStringExtra("PROJECT_ID").toString()
        setTitle(appName)

        createList()


        addNewSource = findViewById(R.id.newSourceProject)

        addNewSource.setOnClickListener(fun(_:View){
            intent = Intent(this, AddNewSource::class.java)
            intent.putExtra("FAVORITE", false)
            intent.putExtra("PROJECT", appName)
            startActivity(intent)
        })

    }

    override fun onRestart() {
        super.onRestart()
        createList()
    }

    private fun createList(){

        adapter = SourceTileAdapter(this, appName)

        val list = findViewById<ListView>(R.id.sources_list)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val source = adapter.getItem(position) as SourceData
            val title = source.title
            val link = source.link
            val id = source.id.toString()
            val notes = source.notes
            intent = Intent(this, Source::class.java)
            intent.putExtra("NAME", title)
            intent.putExtra("LINK", link)
            intent.putExtra("ID", id)
            intent.putExtra("NOTES", notes)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            R.id.delete_project -> deleteCurrentProject()
            android.R.id.home -> stopApp()
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
    private fun stopApp(): Boolean {
        finish()
        return true
    }
    private fun deleteCurrentProject():Boolean{
        val db = DatabaseOpenHelper(this)
        db.deleteProject(projectId.toInt())
        finish()
        return true
    }


}