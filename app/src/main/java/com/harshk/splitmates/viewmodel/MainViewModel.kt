package com.harshk.splitmates.viewmodel

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.drive.model.File
import com.harshk.splitmates.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
) : ViewModel() {
    var googleAccount: GoogleSignInAccount? = null

    init {
        viewModelScope.launch {
            googleAccount = useCase()
        }
    }

    fun signIn(activity: FragmentActivity) {
        val activityLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == FragmentActivity.RESULT_OK) {
                viewModelScope.launch {
                    googleAccount = useCase()
                }
            }
        }
        val intent = useCase.getGoogleClient().signInIntent
        activityLauncher.launch(intent)
    }

    fun createFile() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.createFile()
        }
    }
}