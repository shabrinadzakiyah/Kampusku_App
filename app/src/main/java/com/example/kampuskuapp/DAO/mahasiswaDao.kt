package com.example.kampuskuapp.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.kampuskuapp.model.Mahasiswa

class MahasiswaDao(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insert(mahasiswa: Mahasiswa): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nomor", mahasiswa.nomor)
            put("nama", mahasiswa.nama)
            put("alamat", mahasiswa.alamat)
            put("jurusan", mahasiswa.jurusan)
        }
        return db.insert("mahasiswa", null, values)
    }

    fun update(mahasiswa: Mahasiswa): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nama", mahasiswa.nama)
            put("alamat", mahasiswa.alamat)
            put("jurusan", mahasiswa.jurusan)
        }
        return db.update(
            "mahasiswa",
            values,
            "nomor = ?",
            arrayOf(mahasiswa.nomor)
        )
    }

    fun delete(mahasiswa: Mahasiswa): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            "mahasiswa",
            "nomor = ?",
            arrayOf(mahasiswa.nomor)
        )
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val db = dbHelper.readableDatabase
        val mahasiswaList = mutableListOf<Mahasiswa>()

        val cursor: Cursor = db.query(
            "mahasiswa",
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val nomorIndex = getColumnIndex("nomor")
                val namaIndex = getColumnIndex("nama")
                val alamatIndex = getColumnIndex("alamat")
                val jurusanIndex = getColumnIndex("jurusan")

                val nomor = getString(nomorIndex)
                val nama = getString(namaIndex)
                val alamat = getString(alamatIndex)
                val jurusan = getString(jurusanIndex)

                mahasiswaList.add(Mahasiswa(nomor, nama, alamat, jurusan))
            }
        }
        cursor.close()
        return mahasiswaList
    }

    fun getMahasiswaByNomor(nomor: String): Mahasiswa? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "mahasiswa",
            null,
            "nomor = ?",
            arrayOf(nomor),
            null,
            null,
            null
        )

        val mahasiswa: Mahasiswa? = if (cursor.moveToFirst()) {
            val namaIndex = cursor.getColumnIndex("nama")
            val alamatIndex = cursor.getColumnIndex("alamat")
            val jurusanIndex = cursor.getColumnIndex("jurusan")

            val nama = cursor.getString(namaIndex)
            val alamat = cursor.getString(alamatIndex)
            val jurusan = cursor.getString(jurusanIndex)
            Mahasiswa(nomor, nama, alamat, jurusan)
        } else {
            null
        }
        cursor.close()
        return mahasiswa
    }
}
