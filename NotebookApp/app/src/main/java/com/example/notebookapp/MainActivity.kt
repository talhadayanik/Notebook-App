package com.example.notebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.notebookapp.models.Note
import com.example.notebookapp.adapters.NotesAdapter

class MainActivity : AppCompatActivity() {

    lateinit var db : DB
    lateinit var fabAdd : FloatingActionButton
    lateinit var listNotes : ListView
    lateinit var notes : MutableList<Note>

    companion object{
        lateinit var selectedNote : Note
        var isDBChanged = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DB(this)
        fabAdd = findViewById(R.id.fabAdd)
        listNotes = findViewById(R.id.listNotes)
        notes = mutableListOf()

        fabAdd.setOnClickListener {
            val i = Intent(this, activity_add::class.java)
            startActivity(i)
        }

        listNotes.setOnItemClickListener { parent, view, position, id ->
            selectedNote = notes[position]
            var i = Intent(this, activity_update::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        if(isDBChanged){
            val customAdapter = NotesAdapter(this, notes)
            listNotes.adapter = customAdapter
            customAdapter.updateNotesList(db.getAllNotes())
            isDBChanged = false
        }

    }
}