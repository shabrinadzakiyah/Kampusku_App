package com.example.kampuskuapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var btnViewStudents: Button
    private lateinit var btnAddStudent: Button
    private lateinit var btnInfo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnViewStudents = findViewById(R.id.btnViewStudents)
        btnAddStudent = findViewById(R.id.btnAddStudent)
        btnInfo = findViewById(R.id.btnInfo)

        btnViewStudents.setOnClickListener {
            val intent = Intent(this, StudentListActivity::class.java)
            startActivity(intent)
        }

        btnAddStudent.setOnClickListener {
            val intent = Intent(this, AddEditStudentActivity::class.java)
            startActivity(intent)
        }

        btnInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}
