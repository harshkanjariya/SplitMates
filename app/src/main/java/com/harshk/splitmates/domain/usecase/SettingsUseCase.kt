package com.harshk.splitmates.domain.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.datasource.MainDataSource
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val dataSource: MainDataSource
) {
    fun listFiles(): List<File> {
        return dataSource.loadFiles()
    }
}