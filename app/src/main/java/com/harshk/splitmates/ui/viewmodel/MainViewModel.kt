package com.harshk.splitmates.ui.viewmodel

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.drive.model.File
import com.harshk.splitmates.core.mvi.MVIBaseViewModel
import com.harshk.splitmates.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
) : MVIBaseViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {
    var googleAccount: GoogleSignInAccount? = null

    init {
        setEvent(MainContract.Event.LoadAccount)
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

    private fun createFile(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.createFile(name)
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            googleAccount = useCase()
        }
    }

    override fun createInitialState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            MainContract.Event.LoadAccount -> loadAccount()
            is MainContract.Event.OnCreateFile -> createFile(event.name)
            MainContract.Event.OnAddDialogShow -> currentState.isAddDialogShowing.value = true
            MainContract.Event.OnAddDialogHide -> currentState.isAddDialogShowing.value = false
        }
    }
}