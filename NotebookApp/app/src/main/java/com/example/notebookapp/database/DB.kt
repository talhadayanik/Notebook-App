package com.example.notebookapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notebookapp.models.Note

class DB(context: Context) : SQLiteOpenHelper(context, DBName, null, Version  ) {

    companion object {
        private val DBName = "notes.db"
        private val Version = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val noteTable = "CREATE TABLE \"notes\" (\n" +
                "\t\"nid\"\tINTEGER UNIQUE,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"detail\"\tTEXT,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"nid\" AUTOINCREMENT)\n" +
                ");"
        p0?.execSQL(noteTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val notesTableDrop = "DROP TABLE IF EXISTS notes"
        p0?.execSQL(notesTableDrop)
        onCreate(p0)
    }

    fun addNote( title:String, detail: String, date: String) : Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("detail", detail)
        values.put("date", date)
        val affectedRow = db.insert("notes", null, values)
        db.close()
        return affectedRow
    }

    fun deleteNote(nid: Int) : Int {
        val db = this.writableDatabase
        val status = db.delete("notes", "nid = $nid", null )
        db.close()
        return status
    }

    fun updateNote(nid: Int, title:String, detail: String, date: String) : Int {
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("title", title)
        content.put("detail", detail)
        content.put("date", date)
        val status = db.update("notes", content, "nid = $nid", null)
        db.close()
        return status
    }

    fun getAllNotes() : MutableList<Note> {
        val db = this.readableDatabase
        val arr = mutableListOf<Note>()
        val cursor = db.query("notes",null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val nid = cursor.getInt(0)
            val title = cursor.getString(1)
            val detail = cursor.getString(2)
            val date = cursor.getString(3)
            val note = Note(nid, title, detail, date)
            arr.add(note)
        }
        db.close()
        return arr
    }

}