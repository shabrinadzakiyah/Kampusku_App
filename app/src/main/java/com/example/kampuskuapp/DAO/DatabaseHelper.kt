package com.example.kampuskuapp.DAO

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "kampusku.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_MAHASISWA = "mahasiswa"

        private const val CREATE_TABLE_MAHASISWA = (
                "CREATE TABLE $TABLE_MAHASISWA (" +
                        "nomor TEXT PRIMARY KEY, " +
                        "nama TEXT, " +
                        "alamat TEXT, " +
                        "jurusan TEXT" +
                        ")"
                )
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_MAHASISWA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade as needed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        onCreate(db)
    }
}
