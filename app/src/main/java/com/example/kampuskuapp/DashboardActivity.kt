package com.example.kampuskuapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Inisialisasi komponen UI
        val btnLihatData: Button = findViewById(R.id.btn_lihat_data)
        val btnInputData: Button = findViewById(R.id.btn_input_data)
        val btnInformasi: Button = findViewById(R.id.btn_informasi)

        // Menambahkan listener untuk tombol lihat data
        btnLihatData.setOnClickListener {
            navigateTo(LihatDataActivity::class.java)
        }

        // Menambahkan listener untuk tombol input data
        btnInputData.setOnClickListener {
            navigateTo(InputMahasiswaActivity::class.java)
        }

        // Menambahkan listener untuk tombol informasi
        btnInformasi.setOnClickListener {
            navigateTo(TentangAplikasiActivity::class.java)
        }
    }

    private fun <T> navigateTo(activityClass: Class<T>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
