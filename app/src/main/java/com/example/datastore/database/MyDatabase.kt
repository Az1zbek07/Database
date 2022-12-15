package com.example.datastore.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.datastore.model.Student
import com.example.datastore.util.Constants.DB_NAME
import com.example.datastore.util.Constants.ID
import com.example.datastore.util.Constants.LAST_NAME
import com.example.datastore.util.Constants.NAME
import com.example.datastore.util.Constants.TABLE_NAME

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $NAME TEXT NOT NULL, $LAST_NAME TEXT NOT NULL)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun saveStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.apply {
            put(NAME, student.name)
            put(LAST_NAME, student.lastName)
        }
        database.apply {
            insert(TABLE_NAME, null, contentValues)
            close()
        }
    }

    override fun getAllStudents(): List<Student> {
        val db = this.readableDatabase
        val studentList = mutableListOf<Student>()
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                val student = Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                studentList.add(student)
            }while (cursor.moveToNext())
        }
        return studentList
    }

    override fun updateStudent(student: Student) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.apply {
            put(ID, student.id)
            put(NAME, student.name)
            put(LAST_NAME, student.lastName)
        }
        db.update(
            TABLE_NAME,
            contentValues,
            "$ID = ?",
            arrayOf(student.id.toString())
        )
        db.close()
    }

    override fun getStudentById(id: Int): Student {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(ID, NAME, LAST_NAME),
            "$ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return Student(cursor?.getInt(0), cursor?.getString(1), cursor?.getString(2))
    }

    override fun deleteStudent(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID = ?", arrayOf(id.toString()))
        db.close()
    }

    override fun deleteAllStudents() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    override fun searchByName(name: String) {
        TODO("Not yet implemented")
    }
}