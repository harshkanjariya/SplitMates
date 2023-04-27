package com.harshk.splitmates.domain.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.datasource.MainDataSource
import com.harshk.splitmates.domain.model.Group
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val dataSource: MainDataSource
) {
    operator fun invoke(): GoogleSignInAccount? {
        return dataSource.getGoogleAccount()
    }
    fun getGoogleClient(): GoogleSignInClient {
        return dataSource.getGoogleClient()
    }

    fun loadGroups(): List<Group> {
        return dataSource.loadFiles()
    }

    fun createFile(name: String): File? {
        return dataSource.createFile(name)
    }
}