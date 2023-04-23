package com.harshk.splitmates

import android.content.Context
import android.util.Log
import java.io.File

class FileManager(context: Context) {
    val file = File(context.filesDir, "data.txt")

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }
}