package com.example.studentcontactapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.databinding.ActivityDetailBinding
import com.example.studentcontactapp.utils.FileHelper
import kotlinx.coroutines.launch

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var studentNim: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentNim = intent.getStringExtra("EXTRA_NIM") ?: ""
        
        if (studentNim.isNotEmpty()) {
            loadStudentData()
            setupNoteSection()
        } else {
            Toast.makeText(this, "Data mahasiswa tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadStudentData() {
        val database = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            // Fetch by NIM (since we passed NIM from MainActivity)
            // Note: In a real app, passing ID is better. 
            // For now we search by NIM as it's what we have.
            val students = database.studentDao().searchStudents(studentNim)
            if (students.isNotEmpty()) {
                val student = students[0]
                binding.tvStudentName.text = student.name
                binding.tvStudentNimDept.text = getString(R.string.welcome_message, "${student.nim} - ${student.prodi}")
            }
        }
    }

    private fun setupNoteSection() {
        updateNoteStatus()
        
        if (FileHelper.isNoteExists(this, studentNim)) {
            val content = FileHelper.loadNote(this, studentNim)
            binding.etNote.setText(content)
        }

        binding.btnSaveNote.setOnClickListener {
            val content = binding.etNote.text.toString()
            if (content.isNotEmpty()) {
                FileHelper.saveNote(this, studentNim, content)
                Toast.makeText(this, R.string.note_saved_msg, Toast.LENGTH_SHORT).show()
                updateNoteStatus()
            }
        }

        binding.btnLoadNote.setOnClickListener {
            if (FileHelper.isNoteExists(this, studentNim)) {
                val content = FileHelper.loadNote(this, studentNim)
                binding.etNote.setText(content)
                Toast.makeText(this, R.string.note_loaded_msg, Toast.LENGTH_SHORT).show()
                updateNoteStatus()
            } else {
                Toast.makeText(this, R.string.status_no_note, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateNoteStatus() {
        if (FileHelper.isNoteExists(this, studentNim)) {
            val size = FileHelper.getNoteSize(this, studentNim)
            binding.tvStatus.text = getString(R.string.status_saved, size)
        } else {
            binding.tvStatus.text = getString(R.string.status_no_note)
        }
    }
}
