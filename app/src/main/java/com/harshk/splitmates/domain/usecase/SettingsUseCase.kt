package com.harshk.splitmates.domain.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.datasource.MainDataSource
import com.harshk.splitmates.domain.model.SettingListItem
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val dataSource: MainDataSource
) {
    fun listFiles(): List<SettingListItem> {
        return dataSource.loadFiles().map {
            SettingListItem(
                id = it.id,
                name = it.name
            )
        }
    }
}