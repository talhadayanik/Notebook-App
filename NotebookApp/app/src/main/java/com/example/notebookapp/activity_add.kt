package com.example.notebookapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.Calendar

class activity_add : AppCompatActivity() {

    lateinit var db : DB
    lateinit var txtTitle : EditText
    lateinit var txtDetails : EditText
    lateinit var btnDate : Button
    lateinit var btnAdd : Button
    lateinit var txtDate : TextView
    var selectedDate = ""
    var year = 0
    var month = 0
    var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = DB(this)
        txtTitle = findViewById(R.id.txtUpdateTitle)
        txtDetails = findViewById(R.id.txtUpdateDetails)
        btnDate = findViewById(R.id.btnAddDate)
        btnAdd = findViewById(R.id.btnAdd)
        txtDate = findViewById(R.id.txtAddDate)

        txtDate.text = selectedDate

        val calendar = Calendar.getInstance()
        btnDate.setOnClickListener(View.OnClickListener {
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
                    selectedDate = "$i3.$ay.$i"
                    txtDate.text = selectedDate
                    year = i
                    month = i2
                    day = i3
                },
                if (selectedDate == ""){calendar.get(Calendar.YEAR)}else{year},
                if (selectedDate == ""){calendar.get(Calendar.MONTH)}else{month},
                if (selectedDate == ""){calendar.get(Calendar.DAY_OF_MONTH)}else{day},
            )
            datePickerDialog.show()
        })

        btnAdd.setOnClickListener {
            db.addNote(txtTitle.text.toString(), txtDetails.text.toString(), selectedDate)
            MainActivity.isDBChanged = true
            finish()
        }
    }
}