package com.example.research

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.research.Dialogs.AlertFragment
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Project : AppCompatActivity() {
    private lateinit var sources: ListView
    private lateinit var addNewSource: FloatingActionButton
    private lateinit var adapter: SourceTileAdapter
    private lateinit var alertFragment: AlertFragment
    private lateinit var appName:String
    private lateinit var projectId: String
    private var sortBy: String = "_id"
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

        adapter = SourceTileAdapter(this, appName, sortBy)

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
            R.id.sort_source_items -> showBottomSheetDialog()
            R.id.edit_project -> editCurrentProject()
            R.id.export_sources -> exportCurrentProjectSources()
            android.R.id.home -> stopApp()
            else -> return true
        }
    }

    private fun exportCurrentProjectSources(): Boolean {
        val intent = Intent(this, ExportSourcesForProject::class.java)
        intent.putExtra("ID", projectId)
        intent.putExtra("PROJECT", appName)
        startActivity(intent)
        return true

    }

    private fun editCurrentProject(): Boolean{
        val intent = Intent(this, AddNewProject::class.java)
        intent.putExtra("EDIT", true)
        intent.putExtra("ID", projectId)
        startActivity(intent)
        return true
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
    @SuppressLint("ResourceAsColor")
    private fun showBottomSheetDialog(): Boolean {
        val btnsheet = layoutInflater.inflate(R.layout.source_sort_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(btnsheet)
        val createdDateUp = btnsheet.findViewById<LinearLayout>(R.id.created_date_up)
        val createdDateDown = btnsheet.findViewById<LinearLayout>(R.id.created_at_down)
        val alphabetTitle = btnsheet.findViewById<LinearLayout>(R.id.alphabet_title)
        val alphabetLink = btnsheet.findViewById<LinearLayout>(R.id.alphabet_link)
        createdDateUp.setOnClickListener(fun(_:View){
            changeSortBy("_id")
            dialog.dismiss()
        })
        createdDateDown.setOnClickListener(fun(_:View){
            changeSortBy("_id DESC")
            dialog.dismiss()
        })
        alphabetTitle.setOnClickListener(fun(_:View){
            changeSortBy("title COLLATE NOCASE ASC")
            dialog.dismiss()
        })
        alphabetLink.setOnClickListener(fun(_:View){
            changeSortBy("link COLLATE NOCASE ASC")
            dialog.dismiss()
        })
        when(sortBy){
            "_id" -> createdDateUp.setBackgroundColor(R.color.grey)
            "_id DESC" -> createdDateDown.setBackgroundColor(R.color.grey)
            "title COLLATE NOCASE ASC" -> alphabetTitle.setBackgroundColor(R.color.grey)
            "link COLLATE NOCASE ASC" -> alphabetLink.setBackgroundColor(R.color.grey)
            else -> return true
        }
        dialog.show()
        dialog.show()
        dialog.setCancelable(true)
        dialog.show()

        return true
    }

    private fun changeSortBy(sort: String){
        sortBy = sort
        createList()
    }


}