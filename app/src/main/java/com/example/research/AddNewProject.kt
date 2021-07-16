package com.example.research

import android.app.DatePickerDialog
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.research.Dialogs.DatePickerFragment
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewProject : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var datePickerFragment: DatePickerFragment
    private lateinit var buttonDatePicker: Button
    private lateinit var createProject: FloatingActionButton
    private lateinit var titleInput: TextView
    private lateinit var detailsInput: TextView

    private lateinit var openHelper: DatabaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_project)

        val onlyEdit = intent.getBooleanExtra("EDIT", false)
        val id = intent.getStringExtra("ID")

        datePickerFragment = DatePickerFragment()
        buttonDatePicker = findViewById(R.id.button_date_picker)
        buttonDatePicker.setOnClickListener{
            datePickerFragment.show(supportFragmentManager, DatePickerFragment.TAG)
        }
        openHelper = DatabaseOpenHelper(this)

        titleInput = findViewById(R.id.project_title_input)
        detailsInput = findViewById(R.id.project_details_input)
        createProject = findViewById(R.id.addCreatedProject)
        if(onlyEdit){
            if (id != null) {
                val cursor: Cursor = openHelper.getProjectById(id.toInt())
                if(cursor.count == 0){
                    println("not existing with that id")
                }
                while(cursor.moveToNext()){
                    titleInput.text = cursor.getString(1)
                    detailsInput.text = cursor.getString(2)
                    buttonDatePicker.text = cursor.getString(3)
                }
            }
        }
        createProject.setOnClickListener(fun(_:View){
            if(!onlyEdit) {
                openHelper.insertProject(
                    titleInput.text.toString(),
                    detailsInput.text.toString(),
                    buttonDatePicker.text.toString()
                )
            }else{
                if (id != null) {
                    openHelper.updateEditedProject(id.toInt(), titleInput.text.toString(), detailsInput.text.toString(), buttonDatePicker.text.toString())
                }
            }
            finish()
        })



    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        buttonDatePicker.text = "$dayOfMonth.$month.$year"
    }

    override fun onPause() {
        super.onPause()
        openHelper.close()
    }
    override fun onStart() {
        super.onStart()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.source_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> finishApp()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun finishApp(): Boolean{
        finish()
        return true
    }
}