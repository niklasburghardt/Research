package com.example.research

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ExpandableListView
import androidx.annotation.RequiresApi

class Source : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)

        val title: String = intent.getStringExtra("NAME").toString()
        val link: String = intent.getStringExtra("LINK").toString()
        setTitle(title)

        webView = findViewById(R.id.webView)
        val uri = Uri.parse("https://www.github.com")
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