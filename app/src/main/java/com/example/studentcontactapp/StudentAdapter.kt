package com.example.studentcontactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.entity.StudentEntity
import com.example.studentcontactapp.databinding.ItemStudentBinding

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class StudentAdapter(
    private var students: List<StudentEntity>,
    private val onEditClick: (StudentEntity) -> Unit,
    private val onDeleteClick: (StudentEntity) -> Unit,
    private val onItemClick: (StudentEntity) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.binding.tvStudentName.text = student.name
        holder.binding.tvStudentNim.text = student.nim
        holder.binding.tvInitial.text = student.name.take(1).uppercase()

        holder.binding.btnEdit.setOnClickListener { onEditClick(student) }
        holder.binding.btnDelete.setOnClickListener { onDeleteClick(student) }
        holder.itemView.setOnClickListener { onItemClick(student) }
    }

    override fun getItemCount(): Int = students.size

    fun updateData(newStudents: List<StudentEntity>) {
        students = newStudents
        notifyDataSetChanged()
    }
}
