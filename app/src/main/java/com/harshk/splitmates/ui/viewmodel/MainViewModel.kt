package com.harshk.splitmates.ui.viewmodel

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.harshk.splitmates.core.mvi.MVIBaseViewModel
import com.harshk.splitmates.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
) : MVIBaseViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {

    private val _isLoaded = MutableStateFlow<Boolean>(false)
    val isLoaded = _isLoaded.asStateFlow()

    var googleAccount: GoogleSignInAccount? = null

    init {
        setEvent(MainContract.Event.LoadAccount)
    }

    override fun createInitialState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.LoadAccount -> loadAccount()
            is MainContract.Event.OnCreateFile -> createFile(event.name)
            is MainContract.Event.OnAddDialogShow -> currentState.isAddDialogShowing.value = true
            is MainContract.Event.OnAddDialogHide -> currentState.isAddDialogShowing.value = false
            is MainContract.Event.LoadGroups -> loadGroups()
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

    private fun createFile(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val file = useCase.createFile(name)
            if (file != null) {
                currentState.isAddDialogShowing.value = false
                setEffect(
                    MainContract.Effect.OnFileCreated(file)
                )
            }
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            googleAccount = useCase()
            _isLoaded.emit(true)
            if (googleAccount != null) {
                setEvent(MainContract.Event.LoadGroups)
            }
        }
    }

    private fun loadGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = useCase.loadGroups()
            currentState.groups.value = value
        }
    }
}