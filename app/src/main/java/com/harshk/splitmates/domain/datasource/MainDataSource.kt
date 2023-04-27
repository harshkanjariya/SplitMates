package com.harshk.splitmates.domain.datasource

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.model.Group


interface MainDataSource {
    fun getGoogleAccount(): GoogleSignInAccount?
    fun getGoogleClient(): GoogleSignInClient
    fun loadFiles(): List<Group>
    fun createFile(name: String): File?
    fun deleteFiles(files: List<Group>)
}