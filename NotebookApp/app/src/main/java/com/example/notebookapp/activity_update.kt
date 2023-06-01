package com.example.notebookapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class activity_update : AppCompatActivity() {

    lateinit var db : DB
    lateinit var txtTitle : TextView
    lateinit var txtDetails : TextView
    lateinit var txtUpdateDate : TextView
    lateinit var btnUpdateDate : Button
    lateinit var btnUpdate : Button
    lateinit var btnDelete : Button
    lateinit var selectedDate : Array<String>
    var newSelectedDate = ""
    var year = 0
    var month = 0
    var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        db = DB(this)
        txtTitle = findViewById(R.id.txtUpdateTitle)
        txtDetails = findViewById(R.id.txtUpdateDetails)
        txtUpdateDate = findViewById(R.id.txtUpdateDate)
        btnUpdateDate = findViewById(R.id.btnUpdateDate)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        selectedDate = MainActivity.selectedNote.date.split(".").toTypedArray()

        txtTitle.text = MainActivity.selectedNote.title
        txtDetails.text = MainActivity.selectedNote.detail
        txtUpdateDate.text = MainActivity.selectedNote.date

        btnUpdateDate.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    Log.d("i", i.toString()) // yıl
                    Log.d("i2", (i2 + 1).toString()) // ay
                    Log.d("i3", i3.toString()) // gün
                    var ay = "${i2+1}"
                    if ( i2+1 < 10 ) {
                        ay = "0${i2+1}"
                    }
                    newSelectedDate = "$i3.$ay.$i"
                    txtUpdateDate.text = newSelectedDate
                    year = i
                    month = i2
                    day = i3
                },
                if (newSelectedDate == ""){selectedDate[2].toInt()}else{year},
                if (newSelectedDate == ""){selectedDate[1].toInt()-1}else{month},
                if (newSelectedDate == ""){selectedDate[0].toInt()}else{day},
            )
            datePickerDialog.show()
        })

        btnUpdate.setOnClickListener {
            db.updateNote(MainActivity.selectedNote.nid, txtTitle.text.toString(), txtDetails.text.toString(), newSelectedDate)
            MainActivity.isDBChanged = true
            finish()
        }

        btnDelete.setOnClickListener {
            db.deleteNote(MainActivity.selectedNote.nid)
            MainActivity.isDBChanged = true
            finish()
        }
    }
}