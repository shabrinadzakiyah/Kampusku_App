package com.example.kampuskuapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kampuskuapp.helper.DatabaseHelper
import com.example.kampuskuapp.model.Student

class StudentListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val students = mutableListOf<Student>()
    private val studentNames = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        listView = findViewById(R.id.listViewStudents)
        dbHelper = DatabaseHelper(this)

        loadStudents()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedStudent = students[position]
            showStudentOptionsDialog(selectedStudent)
        }
    }

    private fun loadStudents() {
        students.clear()
        studentNames.clear()

        students.addAll(dbHelper.getAllStudents())
        studentNames.addAll(students.map { it.name })

        if (studentNames.isEmpty()) {
            // Show a Toast message if no students are found
            Toast.makeText(this, "Data kosong", Toast.LENGTH_SHORT).show()
        } else {
            // Update the ListView if there are students
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNames)
            listView.adapter = adapter
        }
    }

    private fun showStudentOptionsDialog(student: Student) {
        val options = arrayOf("Lihat Data", "Update Data", "Hapus Data")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(student.name)
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> { // Lihat Data
                    val intent = Intent(this, StudentDetailActivity::class.java)
                    intent.putExtra("student_id", student.nim)
                    startActivity(intent)
                }
                1 -> { // Update Data
                    val intent = Intent(this, AddEditStudentActivity::class.java)
                    intent.putExtra("student_id", student.nim)
                    startActivity(intent)
                }
                2 -> { // Hapus Data
                    dbHelper.deleteStudent(student.nim)
                    loadStudents()
                    Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.show()
    }
}
