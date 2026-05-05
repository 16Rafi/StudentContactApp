package com.example.studentcontactapp.utils

import android.content.Context
import java.io.File

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
object FileHelper {
    private const val FILE_PREFIX = "note_"
    private const val FILE_SUFFIX = ".txt"

    fun saveNote(context: Context, studentNim: String, content: String) {
        val fileName = "$FILE_PREFIX$studentNim$FILE_SUFFIX"
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
    }

    fun loadNote(context: Context, studentNim: String): String {
        val fileName = "$FILE_PREFIX$studentNim$FILE_SUFFIX"
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            context.openFileInput(fileName).bufferedReader().use { it.readText() }
        } else {
            ""
        }
    }

    fun deleteNote(context: Context, studentNim: String): Boolean {
        val fileName = "$FILE_PREFIX$studentNim$FILE_SUFFIX"
        val file = File(context.filesDir, fileName)
        return if (file.exists()) file.delete() else false
    }

    fun isNoteExists(context: Context, studentNim: String): Boolean {
        val fileName = "$FILE_PREFIX$studentNim$FILE_SUFFIX"
        return File(context.filesDir, fileName).exists()
    }

    fun getNoteSize(context: Context, studentNim: String): Long {
        val fileName = "$FILE_PREFIX$studentNim$FILE_SUFFIX"
        return File(context.filesDir, fileName).length()
    }

    fun getAllNotes(context: Context): List<Pair<String, Long>> {
        return context.fileList()
            .filter { it.startsWith(FILE_PREFIX) && it.endsWith(FILE_SUFFIX) }
            .map { fileName ->
                val file = File(context.filesDir, fileName)
                fileName to file.length()
            }
    }
}
