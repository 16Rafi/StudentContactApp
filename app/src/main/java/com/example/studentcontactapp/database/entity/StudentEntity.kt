package com.example.studentcontactapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val nim: String,
    val prodi: String,
    val email: String,
    val semester: Int,
    val createdAt: Long = System.currentTimeMillis()
)
