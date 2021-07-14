package com.example.research

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.research.Dialogs.DatePickerFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewProject : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var datePickerFragment: DatePickerFragment
    private lateinit var buttonDatePicker: Button
    private lateinit var createProject: FloatingActionButton
    private lateinit var titleInput: TextView
    private lateinit var detailsInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_project)
        datePickerFragment = DatePickerFragment()
        buttonDatePicker = findViewById(R.id.button_date_picker)
        buttonDatePicker.setOnClickListener{
            datePickerFragment.show(supportFragmentManager, DatePickerFragment.TAG)
        }
        createProject = findViewById(R.id.addCreatedProject)
        createProject.setOnClickListener(fun(_:View){
            //add to database
            finish()
        })

        titleInput = findViewById(R.id.project_title_input)
        detailsInput = findViewById(R.id.project_details_input)



    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        buttonDatePicker.text = "$dayOfMonth.$month.$year"
    }
}