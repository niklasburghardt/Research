package com.example.research

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewSource : AppCompatActivity() {
    private lateinit var addButton: FloatingActionButton
    private lateinit var titleView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_source)

        titleView = findViewById(R.id.titleView)

        addButton = findViewById(R.id.addCreatedSource)
        addButton.setOnClickListener(fun(_:View){
            title = titleView.text.toString()

            intent = Intent(this, Project::class.java)
            intent.putExtra("TITLE", title)
        })
    }
}