package com.example.research

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewSource : AppCompatActivity() {
    private lateinit var addButton: FloatingActionButton
    private lateinit var titleView: TextView
    private lateinit var selectProject: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_source)

        selectProject = findViewById(R.id.spinner2)
        val projects = arrayOf(
            "Rom", "Paris", "Berlin", "Madrid"
        )
        if (selectProject != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, projects
            )
            selectProject.adapter = adapter

            addButton = findViewById(R.id.addCreatedSource)
            addButton.setOnClickListener(fun(_: View) {
                title = titleView.text.toString()

                intent = Intent(this, Project::class.java)
                intent.putExtra("TITLE", title)
            })
        }
    }}