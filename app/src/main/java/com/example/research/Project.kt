package com.example.research

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Project : AppCompatActivity() {
    private lateinit var sources: ListView
    private lateinit var addNewSource: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        val appName: String = intent.getStringExtra("NAME").toString()
        setTitle(appName)


        sources = findViewById(R.id.sources_list)
        val arrayAdapter: ArrayAdapter<*>
        val testTitles = arrayOf(
            "Rom", "Paris", "Berlin", "Madrid"
        )

        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, testTitles)
        sources.adapter = arrayAdapter

        sources.setOnItemClickListener { parent, view, position, id ->
            openSourcePage(testTitles.get(position))
        }
        addNewSource = findViewById(R.id.newSourceProject)

        addNewSource.setOnClickListener(fun(_:View){
            intent = Intent(this, AddNewSource::class.java)
            intent.putExtra("FAVORITE", false)
            intent.putExtra("PROJECT", "projectna")
            startActivity(intent)
        })


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