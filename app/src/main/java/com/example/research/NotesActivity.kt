package com.example.research

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class NotesActivity : AppCompatActivity() {
    private lateinit var notes: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        notes = findViewById(R.id.edit_notes_text)
        notes.movementMethod = ScrollingMovementMethod()
    }
}