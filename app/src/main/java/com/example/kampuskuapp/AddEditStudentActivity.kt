package com.example.kampuskuapp
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kampuskuapp.R
import com.example.kampuskuapp.helper.DatabaseHelper
import com.example.kampuskuapp.model.Student
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class AddEditStudentActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etNim: EditText
    private lateinit var etName: EditText
    private lateinit var etBirthDate: EditText
    private lateinit var etAddress: EditText
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var btnSave: Button

    private var studentId: String? = null // studentId as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        dbHelper = DatabaseHelper(this)

        etNim = findViewById(R.id.etNim)
        etName = findViewById(R.id.etName)
        etBirthDate = findViewById(R.id.etBirthDate)
        etAddress = findViewById(R.id.etAddress)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        btnSave = findViewById(R.id.btnSave)

        // Set click listener for birth date field
        etBirthDate.setOnClickListener {
            showDatePicker()
        }

        // Retrieve the studentId from the intent if available
        studentId = intent.getStringExtra("student_id")

        if (studentId != null) {
            loadStudentData(studentId!!)
        }

        btnSave.setOnClickListener {
            saveStudentData()
        }
    }

    private fun showDatePicker() {
        // Create a Date Picker dialog
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Lahir")
            .build()

        datePicker.addOnPositiveButtonClickListener {
            // Format date to "dd/MM/yyyy"
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = sdf.format(Date(it))
            etBirthDate.setText(date)
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    private fun loadStudentData(id: String) {
        val student = dbHelper.getAllStudents().find { it.nim == id }
        student?.let {
            etNim.setText(it.nim)
            etName.setText(it.name)
            etBirthDate.setText(it.birthDate)
            etAddress.setText(it.address)
            if (it.gender == "Male") {
                rbMale.isChecked = true
            } else {
                rbFemale.isChecked = true
            }
            etNim.isEnabled = false // Disable editing NIM
        }
    }

    private fun saveStudentData() {
        val nim = etNim.text.toString()
        val name = etName.text.toString()
        val birthDate = etBirthDate.text.toString()
        val gender = if (rbMale.isChecked) "Laki-Laki" else "Perempuan"
        val address = etAddress.text.toString()

        if (nim.isNotEmpty() && name.isNotEmpty()) {
            if (studentId == null) {
                // Adding new student
                dbHelper.addStudent(Student(nim = nim, name = name, birthDate = birthDate, gender = gender, address = address))
                showToast("Student added successfully")
            } else {
                // Updating existing student
                dbHelper.updateStudent(Student(nim = nim, name = name, birthDate = birthDate, gender = gender, address = address))
                showToast("Student updated successfully")
            }
            finish()
        } else {
            showToast("NIM and Name cannot be empty")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
