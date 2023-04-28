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


class GoogleService(private val context: Context) {
    fun getAccount(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun signOut() {
        val client = getGoogleSignInClient()
        client.signOut()
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
}