package com.example.kampuskuapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kampuskuapp.adapter.MahasiswaAdapter
import com.example.kampuskuapp.model.Mahasiswa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import com.example.kampuskuapp.DAO.DatabaseHelper

class ListMahasiswaActivity : AppCompatActivity() {

    private lateinit var rvMahasiswa: RecyclerView
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_mahasiswa)

        // Inisialisasi DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Inisialisasi RecyclerView
        rvMahasiswa = findViewById(R.id.rv_mahasiswa)
        rvMahasiswa.layoutManager = LinearLayoutManager(this)

        // Load data mahasiswa dari database
        loadMahasiswa()
    }

    private fun loadMahasiswa() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = databaseHelper.readableDatabase
            val cursor = db.query(
                "mahasiswa",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val mahasiswaList = mutableListOf<Mahasiswa>()
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

            withContext(Dispatchers.Main) {
                // Set adapter untuk RecyclerView
                mahasiswaAdapter = MahasiswaAdapter(mahasiswaList)
                rvMahasiswa.adapter = mahasiswaAdapter
            }
        }
    }
}
