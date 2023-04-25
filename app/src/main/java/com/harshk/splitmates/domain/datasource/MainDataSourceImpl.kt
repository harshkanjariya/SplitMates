package com.harshk.splitmates.domain.datasource

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.FileManager
import com.harshk.splitmates.GoogleService
import com.harshk.splitmates.domain.datasource.MainDataSource
import javax.inject.Inject


class MainDataSourceImpl @Inject constructor(
    private val googleService: GoogleService,
    private val fileManager: FileManager,
) : MainDataSource {
    override fun getUser(): GoogleSignInAccount? {
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