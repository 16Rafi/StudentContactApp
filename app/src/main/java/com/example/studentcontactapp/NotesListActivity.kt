package com.example.studentcontactapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentcontactapp.databinding.ActivityNotesListBinding
import com.example.studentcontactapp.utils.FileHelper

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class NotesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val notes = FileHelper.getAllNotes(this)
        val adapter = NotesAdapter(notes)
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = adapter
    }
}
