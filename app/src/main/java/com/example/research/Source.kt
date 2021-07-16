package com.example.research

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ExpandableListView
import androidx.annotation.RequiresApi
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Source : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var openNotes: FloatingActionButton
    private lateinit var link: String
    private lateinit var id: String
    private lateinit var projectId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)

        val title: String = intent.getStringExtra("NAME").toString()
        link= intent.getStringExtra("LINK").toString()
        id = intent.getStringExtra("ID").toString()
        setTitle(title)

        webView = findViewById(R.id.webView)
        val uri = Uri.parse(link)
        webView.loadUrl(uri.toString())

        webView.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (view != null) {
                    if (request != null) {
                        view.loadUrl(request.url.toString())
                    }
                }
                return true
            }

        }
        openNotes = findViewById(R.id.open_notes_button)
        openNotes.setOnClickListener(fun(_:View){
            intent = Intent(this, NotesActivity::class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
        })
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
            R.id.details_source -> openDetailsPage()
            R.id.open_browser -> openPageInBrowser()
            R.id.delete_source -> deleteSource()
            R.id.edit_source_item -> editSource()
            android.R.id.home -> finishApp()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteSource(): Boolean {
        val db = DatabaseOpenHelper(this)
        db.deleteSource(id.toInt())
        finish()
        return true
    }

    private fun editSource(): Boolean {
        val intent = Intent(this, AddNewSource::class.java)
        intent.putExtra("EDIT", true)
        intent.putExtra("ID", id)
        startActivity(intent)
        return true
    }

    private fun finishApp():Boolean{
        finish()
        return true
    }

    private fun openDetailsPage(): Boolean{
        intent = Intent(this, SourceDetails::class.java)
        //extras hinzufügen um datenbank nicht neu aufrufen zu müssen
        startActivity(intent)
        return true
    }
    private fun openPageInBrowser():Boolean {
        val uri = Uri.parse(link)
        val webIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(webIntent)
        return true
    }
}