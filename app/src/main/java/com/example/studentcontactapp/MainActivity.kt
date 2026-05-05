package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.entity.StudentEntity
import com.example.studentcontactapp.databinding.ActivityMainBinding
import com.example.studentcontactapp.utils.PrefManager
import kotlinx.coroutines.launch

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        database = AppDatabase.getDatabase(this)

        if (!prefManager.isLoggedIn()) {
            moveToLoginActivity()
            return
        }

        setupRecyclerView()
        setupSearchView()
        setupButtons()
        checkAndInsertSampleData()
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(
            students = emptyList(),
            onEditClick = { student ->
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("EXTRA_STUDENT_ID", student.id)
                startActivity(intent)
            },
            onDeleteClick = { student ->
                showDeleteConfirmation(student)
            },
            onItemClick = { student ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("EXTRA_NIM", student.nim)
                startActivity(intent)
            }
        )

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = studentAdapter
        }

        // Bonus: Swipe to Delete
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                lifecycleScope.launch {
                    val students = database.studentDao().getAllStudents()
                    val student = students[position]
                    showDeleteConfirmation(student)
                    loadStudents() // Refresh to undo swipe visually if canceled (though dialog makes it easier to just reload)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvStudents)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchStudents(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchStudents(newText ?: "")
                return true
            }
        })
    }

    private fun setupButtons() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditStudentActivity::class.java))
        }

        binding.btnNotes.setOnClickListener {
            startActivity(Intent(this, NotesListActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        
        binding.btnHome.setOnClickListener {
            loadStudents()
        }
    }

    private fun loadStudents() {
        lifecycleScope.launch {
            val students = database.studentDao().getAllStudents()
            studentAdapter.updateData(students)
        }
    }

    private fun searchStudents(query: String) {
        lifecycleScope.launch {
            val results = if (query.isEmpty()) {
                database.studentDao().getAllStudents()
            } else {
                database.studentDao().searchStudents(query)
            }
            studentAdapter.updateData(results)
        }
    }

    private fun showDeleteConfirmation(student: StudentEntity) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Data?")
            .setMessage("Hapus \"${student.name}\"? Tindakan ini tidak dapat dibatalkan.")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    database.studentDao().deleteById(student.id)
                    loadStudents()
                    Toast.makeText(this@MainActivity, "Data dihapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
                loadStudents() // Refresh to restore swiped item if necessary
            }
            .show()
    }

    private fun checkAndInsertSampleData() {
        lifecycleScope.launch {
            if (database.studentDao().getStudentCount() == 0) {
                val sampleData = listOf(
                    StudentEntity(name = "Ahmad Fauzi", nim = "2024001", prodi = "Teknik Informatika", email = "ahmad@example.com", semester = 4),
                    StudentEntity(name = "Budi Santoso", nim = "2024002", prodi = "Teknik Elektro", email = "budi@example.com", semester = 2),
                    StudentEntity(name = "Clara Wijaya", nim = "2024003", prodi = "Sistem Informasi", email = "clara@example.com", semester = 6)
                )
                database.studentDao().insertAll(sampleData)
                loadStudents()
            }
        }
    }

    private fun moveToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
