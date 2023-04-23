package com.harshk.splitmates.domain

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val dataSource: MainDataSource
) {
    fun invoke(): GoogleSignInAccount? {
        return dataSource.getUser()
    }
    fun getGoogleClient(): GoogleSignInClient {
        return dataSource.getGoogleClient()
    }

    fun loadFiles(): List<File> {
        return dataSource.loadFiles()
    }

    fun createFile() {
        return dataSource.createFile()
    }
}