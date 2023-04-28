package com.harshk.splitmates.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.Permission
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class GoogleService(private val context: Context) {
    fun initializeFolders(): List<String>? {
        val folders = DRIVE_BASE_PATH.split("/")
        val drive = getDrive() ?: return null

        val query =
            "mimeType='application/vnd.google-apps.folder' and name='$DRIVE_BASE_PATH' and trashed=false"
        val files = drive.Files().list().setQ(query).setFields("files(id)").execute()
        if (files.files.isNotEmpty()) {
            return files.files.map { it.id }
        }

        val ids = ArrayList<String>()
        for (folder in folders) {
            val file = com.google.api.services.drive.model.File().apply {
                name = folder
                mimeType = "application/vnd.google-apps.folder"
            }
            val createdFolder = drive.Files().create(file).execute()
            ids.add(createdFolder.id)
        }
        return ids
    }

    fun getAccount(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun signOut() {
        val client = getGoogleSignInClient()
        client.signOut()
    }

    fun listFilesInFolder(folderId: String): List<com.google.api.services.drive.model.File> {
        val drive = getDrive() ?: return emptyList()
        val query = "'$folderId' in parents and trashed = false"
        val files = mutableListOf<com.google.api.services.drive.model.File>()
        var nextPageToken: String? = null
        do {
            try {
                val request = drive.files().list()
                    .setQ(query)
                    .setFields("nextPageToken, files(id, name, createdTime, modifiedTime)")
                    .setPageToken(nextPageToken)
                val response = request.execute()
                files.addAll(response.files)
                nextPageToken = response.nextPageToken
            } catch (e: java.lang.Exception) {
                Log.e("listFilesInFolder > 47", "$e")
            }
        } while (nextPageToken != null)
        return files
    }

    fun listFiles(): List<com.google.api.services.drive.model.File> {
        val drive = getDrive() ?: return emptyList()
        val list = drive.Files().list().execute()
        val files = ArrayList<com.google.api.services.drive.model.File>()
        for (file in list.files) {
            files.add(file)
        }
        return files
    }

    fun createFile(file: File): com.google.api.services.drive.model.File? {
        return try {
            val googleFile = com.google.api.services.drive.model.File()
            googleFile.name = file.name
            googleFile.parents = initializeFolders()
            val fileContent = FileContent("text/plain", file)
            val drive = getDrive()
            val files = drive?.Files()
            val task = files?.create(googleFile, fileContent)?.setFields("id")
            val finalFile = task?.execute()
            finalFile
        } catch (e: java.lang.Exception) {
            Log.e("createFile > 55", "$e")
            null
        }
    }

    @Throws(IOException::class)
    fun shareFile(realFileId: String?, realUser: String?): String? {
        val drive = getDrive() ?: return null
        val userPermission: Permission = Permission()
            .setType("user")
            .setRole("writer")
        userPermission.emailAddress = realUser
        return try {
            val permission = drive
                .permissions()
                .create(realFileId, userPermission)
                .setFileId("id")
                .execute()
            permission.id
        } catch (e: GoogleJsonResponseException) {
            System.err.println("shareFile > Unable to modify permission: " + e.details)
            throw e
        }
    }

    fun getDrive(): Drive? {
        val account = getAccount()
        return account?.let {
             val credential = GoogleAccountCredential.usingOAuth2(
                    context, listOf(DriveScopes.DRIVE_FILE)
                )
                credential.selectedAccount = it.account
                return Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential
                )
                    .setApplicationName("Hisab")
                    .build()
        }
    }
    fun getGoogleSignInClient(): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("880517077043-2mrova5jp9cpjms67thmdo4qkgllicg0.apps.googleusercontent.com")
            .requestEmail()
            .requestScopes(
                Scope(DriveScopes.DRIVE_FILE),
                Scope(DriveScopes.DRIVE)
            )
            .build()

        return GoogleSignIn.getClient(context, signInOptions)
    }

    fun listPermissions(fileId: String): ArrayList<Permission>? {
        val drive = getDrive()
        val permissions = drive?.Permissions()?.list(fileId)?.execute() ?: return null
        val list = ArrayList<Permission>()
        for (p in permissions.permissions) {
            val perm = drive.Permissions().get(fileId, p.id).execute()
            perm?.let {
                list.add(perm)
            }
        }
        Log.e("listPermissions > 115", "$list")
        return list
    }
}