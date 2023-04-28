package com.harshk.splitmates.domain.datasource

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.domain.model.Member
import com.harshk.splitmates.utils.DRIVE_BASE_PATH
import com.harshk.splitmates.utils.FileManager
import com.harshk.splitmates.utils.GoogleService
import javax.inject.Inject


class MainDataSourceImpl @Inject constructor(
    private val googleService: GoogleService,
    private val fileManager: FileManager,
) : MainDataSource {
    override fun getGoogleAccount(): GoogleSignInAccount? {
        return googleService.getAccount()
    }

    override fun getGoogleClient(): GoogleSignInClient {
        return googleService.getGoogleSignInClient()
    }

    override fun createFile(name: String): File? {
        return googleService.createFile(fileManager.getFile(name))
    }

    override fun deleteFiles(files: List<Group>) {
        val drive = googleService.getDrive()
        for (file in files) {
            drive?.Files()?.delete(file.id)?.execute()
        }
    }

    override fun loadMembers(fileId: String): List<Member>? {
        return googleService.listPermissions(fileId)?.map {
            Member(
                email = it.displayName ?: ""
            )
        }
    }

    override fun loadFiles(): List<Group> {
        val ids = googleService.initializeFolders() ?: return emptyList()
        return googleService.listFilesInFolder(ids[ids.size - 1]).map {
            Group(
                id = it.id,
                name = it.name.substring(0, it.name.length - 4),
            )
        }
    }
}