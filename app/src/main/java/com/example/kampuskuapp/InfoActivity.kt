package com.example.kampuskuapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kampuskuapp.R

class InfoActivity : AppCompatActivity() {

    private lateinit var tvInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        tvInfo = findViewById(R.id.tvInfo)
        tvInfo.text = "Aplikasi Kampusku\nVersi 1.0\nDikembangkan oleh Shabrina Dzakiyah Adani"
    }
}
