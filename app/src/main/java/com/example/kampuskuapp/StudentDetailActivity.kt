package com.example.kampuskuapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kampuskuapp.R
import com.example.kampuskuapp.helper.DatabaseHelper

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tvName: TextView
    private lateinit var tvBirthDate: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvAddress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        dbHelper = DatabaseHelper(this)

        tvName = findViewById(R.id.tvName)
        tvBirthDate = findViewById(R.id.tvBirthDate)
        tvGender = findViewById(R.id.tvGender)
        tvAddress = findViewById(R.id.tvAddress)

        val studentId = intent.getStringExtra("student_id").toString()
        loadStudentDetail(studentId)
    }

    private fun loadStudentDetail(nim: String) {
        val student = dbHelper.getAllStudents().find { it.nim == nim }
        student?.let {
            tvName.text = "Nama: ${it.name}"
            tvBirthDate.text = "Tanggal Lahir: ${it.birthDate}"
            tvGender.text = "Jenis Kelamin: ${it.gender}"
            tvAddress.text = "Alamat: ${it.address}"
        }
    }
}
