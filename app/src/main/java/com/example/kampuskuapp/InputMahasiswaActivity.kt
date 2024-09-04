package com.example.kampuskuapp

import android.content.ContentValues
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kampuskuapp.model.Mahasiswa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.lifecycleScope
import com.example.kampuskuapp.DAO.DatabaseHelper
import com.example.kampuskuapp.databinding.ActivityInputMahasiswaBinding

class InputMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputMahasiswaBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInputMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Mengatur padding untuk menghindari overlap dengan sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSimpan.setOnClickListener {
            val nim = binding.etNim.text.toString()
            val nama = binding.etNama.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val jurusan = binding.etJurusan.text.toString()

            // Buat objek Mahasiswa
            val mahasiswa = Mahasiswa(nim, nama, alamat, jurusan)

            // Panggil fungsi untuk menyimpan data ke database
            saveMahasiswa(mahasiswa)

            // Setelah data tersimpan, kembali ke halaman sebelumnya
            finish()
        }
    }

    private fun saveMahasiswa(mahasiswa: Mahasiswa) {
        // Menyimpan data mahasiswa ke database menggunakan DatabaseHelper di background thread dengan coroutines
        lifecycleScope.launch(Dispatchers.IO) {
            val db = databaseHelper.writableDatabase
            val values = ContentValues().apply {
                put("nomor", mahasiswa.nomor)
                put("nama", mahasiswa.nama)
                put("alamat", mahasiswa.alamat)
                put("jurusan", mahasiswa.jurusan)
            }
            db.insert("mahasiswa", null, values)
        }
    }
}
