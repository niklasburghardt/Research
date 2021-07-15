package com.example.research.database

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val DATABASE_NAME = "researchdata.db"
private const val DATABASE_VERSION = 1
private val TAG = DatabaseOpenHelper::class.java.simpleName

class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {
    //project table attributes
    private val projectId = "_id"
    private val projectTitle = "title"
    private val projectDescription = "description"
    private val projectDueDate = "due_date"

    private val tableProjectName = "project"

    //source table attributes
    private val sourceId = "_id"
    private val sourceTitle = "title"
    private val sourceNotes = "notes"
    private val sourceLastVisited = "last_visited"
    private val sourceLink = "link"
    private val sourceIsFavorite = "is_favorite"
    private val sourceProjectId = "project_id"

    private val tableSourceName = "source"

    //create project table
    private val tableProjectCreate="""
        CREATE TABLE $tableProjectName (
        $projectId INTEGER PRIMARY KEY AUTOINCREMENT,
        $projectTitle TEXT,
        $projectDescription TEXT NULLABLE,
        $projectDueDate TEXT);")
    """.trimIndent()

    private val tableSourceCreate="""
        CREATE TABLE $tableSourceName (
        $sourceId INTEGER PRIMARY KEY AUTOINCREMENT,
        $sourceTitle TEXT,
        $sourceNotes TEXT,
        $sourceLink TEXT,
        $sourceIsFavorite INTEGER,
        $sourceProjectId TEXT);")
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(tableProjectCreate)
            db.execSQL(tableSourceCreate)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $tableSourceName")
        }
        onCreate(db)
    }

    //daten zu project hinzufügen
    fun insertProject(title: String, description: String, dueDate: String) {
        var rowId = -1L
        try {
            Log.d(TAG, "Pfad: "+writableDatabase.path)

            val values = ContentValues()
            values.put(projectTitle, title)
            values.put(projectDescription, description)
            values.put(projectDueDate, dueDate)

            rowId = writableDatabase.insert(tableProjectName, null, values)
        }catch (e: SQLiteException){
            Log.d(TAG, "insert()", e)
        }finally {
            Log.d(TAG, "insert(): rowId=$rowId")
        }
    }

    fun insertSource(title: String, notes: String, link: String, is_favorite: Boolean, projectId: String){
        var rowId = -1L
        try {
            Log.d(TAG, "Pfad: "+writableDatabase.path)

            val values = ContentValues()
            values.put(sourceTitle, title)
            values.put(sourceNotes, notes)
            values.put(sourceLink, link)
            val isFavorite = if(is_favorite) 1 else 0
            values.put(sourceIsFavorite, isFavorite)
            values.put(sourceProjectId, projectId)

            rowId = writableDatabase.insert(tableSourceName, null, values)
        }catch (e: SQLiteException){
            Log.d(TAG, "insert()", e)
        }finally {
            Log.d(TAG, "insert(): rowId=$rowId")
        }
    }




}