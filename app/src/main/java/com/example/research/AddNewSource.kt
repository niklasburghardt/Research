package com.example.research

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewSource : AppCompatActivity() {
    private lateinit var addButton: FloatingActionButton
    private lateinit var titleInput: TextView
    private lateinit var linkInput: TextView
    private lateinit var isFavorite: Switch
    private lateinit var selectProject: Spinner
    private lateinit var openHelper: DatabaseOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_source)

        selectProject = findViewById(R.id.source_select_project)
        val projects = arrayOf(
            "Rom", "Paris", "Berlin", "Madrid"
        )
        if (selectProject != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, projects
            )
            selectProject.adapter = adapter
        }

        titleInput = findViewById(R.id.source_title_input)
        linkInput = findViewById(R.id.source_link_input)
        isFavorite = findViewById(R.id.source_is_favorite)


        openHelper = DatabaseOpenHelper(this)
        addButton = findViewById(R.id.addCreatedSource)
        addButton.setOnClickListener(fun(_: View) {
            openHelper.insertSource(titleInput.text.toString(), "", linkInput.text.toString(), isFavorite.isChecked, selectProject.selectedItem.toString())
            finish()
        })
    }
    override fun onPause() {
        super.onPause()
        openHelper.close()
    }
}
