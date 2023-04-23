package com.harshk.splitmates.domain

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.drive.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
) : ViewModel() {
    var isLoaded = MutableLiveData(false)
    var googleAccount: GoogleSignInAccount? = null
    var splitFiles: MutableLiveData<List<File>> = MutableLiveData(emptyList())

    init {
        viewModelScope.launch {
            googleAccount = useCase.invoke()
            isLoaded.value = true
        }
    }

    fun signIn(activity: AppCompatActivity) {
        val activityLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                viewModelScope.launch {
                    googleAccount = useCase.invoke()
                    isLoaded.value = true
                }
            }
        }
        val intent = useCase.getGoogleClient().signInIntent
        isLoaded.value = false
        activityLauncher.launch(intent)
    }

    fun loadFile() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = useCase.loadFiles()
            splitFiles.postValue(list)
        }
    }

    fun createFile() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.createFile()
        }
    }
}