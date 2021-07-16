package com.example.research.database

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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

    fun viewSourceData(project_title: String, sortBy: String):Cursor{
        val db = readableDatabase
        val query: String = "SELECT * FROM $tableSourceName, $tableProjectName WHERE project.title == source.project_id AND project.title == '$project_title' ORDER BY $sortBy"
        val cursor: Cursor = db.rawQuery(query, null)

        return cursor
    }
    fun viewProjectData():Cursor{
        val db = readableDatabase
        val query: String = "SELECT * FROM $tableProjectName"
        val cursor: Cursor = db.rawQuery(query, null)

        return cursor
    }
    fun viewNotes(id: Int):Cursor{
        val db = readableDatabase
        val query: String = "SELECT * FROM $tableSourceName WHERE $sourceId == $id"
        val cursor: Cursor = db.rawQuery(query, null)

        return cursor
    }
    fun getAmountOfSources(id: Int):Cursor{
        val db = readableDatabase
        val query: String = "SELECT COUNT(*) FROM project, source WHERE project.title = source.project_id AND project._id == $id"
        val cursor: Cursor = db.rawQuery(query, null)

        return cursor
    }

    fun getSoureById(id: Int): Cursor{
        val db = readableDatabase
        val query: String = "SELECT * FROM $tableSourceName WHERE _id = $id"
        val cursor: Cursor = db.rawQuery(query, null)

        return cursor
    }
    fun getProjectById(id: Int): Cursor{
        val db = readableDatabase
        val query: String = "SELECT * FROM $tableProjectName WHERE _id = $id"
        val cursor: Cursor = db.rawQuery(query, null)

        return cursor
    }

    fun updateSourceNotes(id: Int, newNotesText: String){
        val db = writableDatabase
        val values = ContentValues()
        values.put(sourceNotes, newNotesText)
        val sourceUpdated = db.update(
            tableSourceName,
            values, "$sourceId = ?", arrayOf(id.toString())
        )
        Log.d(TAG, "update(): id=$id -> $sourceUpdated")
    }
    fun updateEditedSource(id: Int, newTitle: String, newLink: String, newIsFavorite: Boolean, newProject: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(sourceId, id)
        values.put(sourceTitle, newTitle)
        values.put(sourceLink, newLink)
        values.put(sourceIsFavorite, newIsFavorite)
        values.put(sourceProjectId, newProject)

        val sourceUpdated = db.update(
            tableSourceName,
            values, "$sourceId = ?", arrayOf(id.toString())
        )
        Log.d(TAG, "updated(): ID= $id")
    }

    fun updateEditedProject(id: Int, newTitle: String, newDescription: String, newDueDate: String){
        val db= writableDatabase
        val values = ContentValues()
        values.put(projectId, id)
        values.put(projectTitle, newTitle)
        values.put(projectDescription, newDescription)
        values.put(projectDueDate, newDueDate)

        val projectEditet = db.update(
            tableProjectName,
        values, "$projectId = ?", arrayOf(id.toString())
        )
        Log.d(TAG, "updated(): Id=$id")
    }
    fun deleteProject(id: Int){
        val db = writableDatabase
        val projectDeleted = db.delete(
            tableProjectName,
            "$projectId = ?",
            arrayOf(id.toString())
        )
        Log.d("TAG", "deleted")
    }
    fun deleteSource(id: Int){
        val db = writableDatabase
        val sourceDeleted = db.delete(
            tableSourceName,
            "$sourceId = ?",
            arrayOf(id.toString())
        )
    }




}