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
import androidx.fragment.app.ListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var newSourceMain: FloatingActionButton
    private lateinit var projectList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        newSourceMain = findViewById(R.id.newSourceMain)


        newSourceMain.setOnClickListener(fun(_:View) {
            intent = Intent(this, AddNewSource::class.java)
            intent.putExtra("FAVORITE", false)
            intent.putExtra("PROJECT", "")
            startActivity(intent)
        })

        projectList = findViewById(R.id.projects_list)
        val arrayAdapter: ArrayAdapter<*>
        val testTitles = arrayOf(
            "Rom", "Paris", "Berlin", "Madrid"
        )

        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, testTitles)
        projectList.adapter = arrayAdapter

        projectList.setOnItemClickListener { parent, view, position, id ->
            openProject(testTitles.get(position))
        }

    }


    private fun openProject(projectName: String){
        intent = Intent(this, Project::class.java)
        intent.putExtra("NAME", projectName)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}