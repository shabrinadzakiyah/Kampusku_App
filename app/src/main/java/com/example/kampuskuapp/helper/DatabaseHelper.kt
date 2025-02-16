package com.example.kampuskuapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.kampuskuapp.model.Student

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "students.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_STUDENT = "students"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_BIRTH_DATE = "birth_date"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_STUDENT (" +
                "$COLUMN_NIM TEXT PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_BIRTH_DATE TEXT," +
                "$COLUMN_GENDER TEXT," +
                "$COLUMN_ADDRESS TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        onCreate(db)
    }

    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NIM, student.nim)
        values.put(COLUMN_NAME, student.name)
        values.put(COLUMN_BIRTH_DATE, student.birthDate)
        values.put(COLUMN_GENDER, student.gender)
        values.put(COLUMN_ADDRESS, student.address)

        return db.insert(TABLE_STUDENT, null, values)
    }

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_STUDENT", null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    val student = Student(
                        nim = getString(getColumnIndexOrThrow(COLUMN_NIM)),
                        name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                        birthDate = getString(getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                        gender = getString(getColumnIndexOrThrow(COLUMN_GENDER)),
                        address = getString(getColumnIndexOrThrow(COLUMN_ADDRESS))
                    )
                    students.add(student)
                } while (moveToNext())
            }
            close()
        }

        Log.d("studentsssss", "getAllStudents: $students")
        return students
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, student.name)
        values.put(COLUMN_BIRTH_DATE, student.birthDate)
        values.put(COLUMN_GENDER, student.gender)
        values.put(COLUMN_ADDRESS, student.address)

        return db.update(TABLE_STUDENT, values, "$COLUMN_NIM=?", arrayOf(student.nim))
    }

    fun deleteStudent(nim: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_STUDENT, "$COLUMN_NIM=?", arrayOf(nim))
    }
}
