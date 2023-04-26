package com.harshk.splitmates.utils

import android.content.Context
import android.util.Log
import java.io.File

class FileManager(val context: Context) {
    private val files = HashMap<String, File>()

    fun addFile(name: String) {
        val file = File(context.filesDir, "$name.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        files[name] = file
    }

    fun getFile(name: String): File {
        if (!files.containsKey(name)) {
            addFile(name)
        }
        return files[name]!!
    }
}