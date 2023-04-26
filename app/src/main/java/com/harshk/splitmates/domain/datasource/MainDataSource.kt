package com.harshk.splitmates.domain.datasource

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.model.SettingListItem


interface MainDataSource {
    fun getGoogleAccount(): GoogleSignInAccount?
    fun getGoogleClient(): GoogleSignInClient
    fun loadFiles(): List<File>
    fun createFile()
    fun deleteFiles(files: List<SettingListItem>)
}