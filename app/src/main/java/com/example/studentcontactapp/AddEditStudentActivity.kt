package com.example.studentcontactapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.entity.StudentEntity
import com.example.studentcontactapp.databinding.ActivityAddEditStudentBinding
import kotlinx.coroutines.launch

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditStudentBinding
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getIntExtra("EXTRA_STUDENT_ID", -1)
        if (studentId != -1) {
            binding.tvTitle.text = "Edit Mahasiswa"
            loadStudentData()
        }

        binding.btnSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun loadStudentData() {
        val database = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val student = database.studentDao().getStudentById(studentId)
            student?.let {
                binding.etName.setText(it.name)
                binding.etNim.setText(it.nim)
                binding.etEmail.setText(it.email)
                binding.etSemester.setText(it.semester.toString())
                
                val prodiArray = resources.getStringArray(R.array.prodi_array)
                val prodiIndex = prodiArray.indexOf(it.prodi)
                if (prodiIndex != -1) {
                    binding.spProdi.setSelection(prodiIndex)
                }
            }
        }
    }

    private fun saveStudent() {
        val name = binding.etName.text.toString()
        val nim = binding.etNim.text.toString()
        val prodi = binding.spProdi.selectedItem.toString()
        val email = binding.etEmail.text.toString()
        val semesterStr = binding.etSemester.text.toString()

        if (name.isEmpty() || nim.isEmpty() || email.isEmpty() || semesterStr.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val semester = semesterStr.toInt()
        val database = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            if (studentId == -1) {
                val newStudent = StudentEntity(name = name, nim = nim, prodi = prodi, email = email, semester = semester)
                database.studentDao().insert(newStudent)
                Toast.makeText(this@AddEditStudentActivity, "Data mahasiswa ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                val updatedStudent = StudentEntity(id = studentId, name = name, nim = nim, prodi = prodi, email = email, semester = semester)
                database.studentDao().update(updatedStudent)
                Toast.makeText(this@AddEditStudentActivity, "Data mahasiswa diperbarui", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
