package com.example.research

import android.database.Cursor
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getStringOrNull
import com.example.research.database.DatabaseOpenHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception


class NotesActivity : AppCompatActivity() {
    private lateinit var notes: EditText
    private lateinit var finishNotes: FloatingActionButton
    private lateinit var db: DatabaseOpenHelper
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        db = DatabaseOpenHelper(this)
        id = intent.getStringExtra("ID").toString()
        Log.d("TESTID", id)
        val notesText = intent.getStringExtra("NOTES")

        notes = findViewById(R.id.edit_notes_text)
        notes.movementMethod = ScrollingMovementMethod()
        notes.setText(viewData())


        finishNotes = findViewById(R.id.finishNotes)
        finishNotes.setOnClickListener(fun(_: View){
            println(notes.text.toString())

            db.updateSourceNotes(id.toInt(), notes.text.toString())
            finish()
        })
    }

    private fun viewData():String?{
        Log.d("IDTOINT", id)
        val cursor: Cursor = db.viewNotes(id.toInt())
        if(cursor.count == 0){
            return ""
        }
        var text: String = ""
        while(cursor.moveToNext()){
            text = cursor.getString(2)
        }
        return text
    }
}