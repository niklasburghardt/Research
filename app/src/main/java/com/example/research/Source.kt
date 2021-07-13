package com.example.research

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ExpandableListView

class Source : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)

        val title: String = intent.getStringExtra("NAME").toString()
        setTitle(title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.source_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.details_source -> openDetailsPage()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDetailsPage(): Boolean{
        intent = Intent(this, SourceDetails::class.java)
        //extras hinzufügen um datenbank nicht neu aufrufen zu müssen
        startActivity(intent)
        return true
    }
}