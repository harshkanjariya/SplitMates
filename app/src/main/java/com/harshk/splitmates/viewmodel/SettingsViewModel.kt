package com.harshk.splitmates.viewmodel

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.usecase.MainUseCase
import com.harshk.splitmates.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    var isLoaded = MutableLiveData(false)
    var splitFiles: MutableLiveData<List<File>> = MutableLiveData(emptyList())

    init {
        viewModelScope.launch {
            splitFiles.value = useCase.listFiles()
            isLoaded.value = true
        }
    }
}