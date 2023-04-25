package com.harshk.splitmates.domain.datasource

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.FileManager
import com.harshk.splitmates.GoogleService
import javax.inject.Inject


class MainDataSourceImpl @Inject constructor(
    private val googleService: GoogleService,
    private val fileManager: FileManager,
) : MainDataSource {
    override fun getGoogleAccount(): GoogleSignInAccount? {
        return googleService.getUser()
    }

    override fun getGoogleClient(): GoogleSignInClient {
        return googleService.getGoogleSignInClient()
    }

    override fun createFile() {
        googleService.createFile(
            fileManager.file
        )
    }

    override fun loadFiles(): List<File> {
        val drive = googleService.getDrive() ?: return emptyList()
        val list = drive.Files().list().execute()
        val files = ArrayList<File>()
        for (file in list.files) {
            files.add(file)
        }
        return files
    }
}