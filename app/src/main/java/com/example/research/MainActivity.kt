package com.example.research

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var newSourceMain: FloatingActionButton

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var testOpen: Button
    
    private var adapter: RecyclerAdapter? = null
    private lateinit var recycler_view: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view = findViewById(R.id.project_card_list)

        layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recycler_view.adapter = adapter

        testOpen = findViewById(R.id.test_open)
        testOpen.setOnClickListener(fun(_:View){
            intent = Intent(this, Project::class.java)
            intent.putExtra("NAME", "Studiengänge")
            startActivity(intent)
        })




        newSourceMain = findViewById(R.id.newSourceMain)


        newSourceMain.setOnClickListener(fun(_:View) {
            intent = Intent(this, AddNewProject::class.java)
            intent.putExtra("FAVORITE", false)
            intent.putExtra("PROJECT", "")
            startActivity(intent)
        })


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