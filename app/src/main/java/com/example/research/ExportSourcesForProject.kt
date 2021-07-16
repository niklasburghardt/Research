package com.example.research

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.internal.ViewUtils.getContentView
import java.io.FileOutputStream
import java.io.OutputStream

class ExportSourcesForProject : AppCompatActivity() {
    private lateinit var exportText: TextView
    private lateinit var showTitlesSwitch: CheckBox
    private lateinit var showNotesSwitch: CheckBox
    private lateinit var cancleExport: Button
    private lateinit var exportButton: Button
    private lateinit var projectId: String

    private lateinit var links: ArrayList<String>
    private lateinit var titles: ArrayList<String>
    private lateinit var notes: ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export_sources_for_project)
        projectId = intent.getStringExtra("ID").toString()
        val projectName = intent.getStringExtra("PROJECT").toString()
        setTitle("Export $projectName")

        exportText = findViewById(R.id.sources_to_export)
        showTitlesSwitch = findViewById(R.id.show_source_titles)
        showNotesSwitch = findViewById(R.id.show_source_notes)
        cancleExport = findViewById(R.id.cancle_export_button)
        exportButton = findViewById(R.id.export_sources_button)

        links = ArrayList()
        titles = ArrayList()
        notes = ArrayList()

        getSources()
        setExportText()
        showNotesSwitch.setOnCheckedChangeListener { buttonView, isChecked -> setExportText() }
        showTitlesSwitch.setOnCheckedChangeListener { buttonView, isChecked -> setExportText() }
        cancleExport.setOnClickListener{finish()}
        exportButton.setOnClickListener(fun(_:View){
            val intent = Intent(this, PdfPrintAdapter::class.java)
            startActivity(intent)
        })


    }


    private fun setExportText() {
        exportText.text = ""
        var textToExport: String = ""
        for(i: Int in 0 until titles.size){
            if(showTitlesSwitch.isChecked){
                textToExport += "${titles[i]}: \n"
            }
            textToExport += "${links[i]}\n"
            if(showNotesSwitch.isChecked){
                textToExport += "${notes[i].trim()}\n"
            }
            if(showTitlesSwitch.isChecked or showNotesSwitch.isChecked){
                textToExport += "\n"
            }

        }
        exportText.text = textToExport
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun createPdfDocument() {
        val document = PdfDocument();
        val pageInfo = PdfDocument.PageInfo.Builder(2100, 2970, 1).create();
        val page: PdfDocument.Page = document.startPage(pageInfo);
        //alles auf die pdf schreiben was man braucht
        val canvas = Canvas()
        val paint = Paint()
        paint.strokeWidth = 3f
        paint.color = Color.BLUE
        page.canvas.drawText("Test", 1, 1, 0.5f, 0.5f, paint)

        page.canvas.drawPaint(paint)


        document.finishPage(page);
        document.close();

    }

    private fun getSources(){
        val db = DatabaseOpenHelper(this)
        val cursor: Cursor = db.getSourcesForProject(projectId.toInt())
        if(cursor.count == 0){
            return
        }
        while(cursor.moveToNext()){
            titles.add(cursor.getString(0))
            links.add(cursor.getString(1))
            notes.add(cursor.getString(2))
        }

    }
}