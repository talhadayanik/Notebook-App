package com.example.notebookapp.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.notebookapp.R
import com.example.notebookapp.models.Note

class NotesAdapter(private val context: Activity, private var list: MutableList<Note>) : ArrayAdapter<Note>(context, R.layout.custom_note_item_layout, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = context.layoutInflater.inflate(R.layout.custom_note_item_layout, null, true)

        val title = view.findViewById<TextView>(R.id.txtItemTitle)

        val note = list.get(position)

        title.text = "${note.title}"

        return view
    }

    public fun updateNotesList(newList: List<Note>){
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }
}