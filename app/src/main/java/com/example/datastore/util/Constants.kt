package com.example.datastore.util

import android.content.Context
import android.widget.Toast

object Constants {
    const val DB_NAME = "MyDb"
    const val TABLE_NAME = "Student_table"
    const val ID = "_id"
    const val NAME = "name"
    const val LAST_NAME = "last_name"
}
fun Context.toastMy(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}