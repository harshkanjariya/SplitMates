package com.harshk.splitmates.ui.viewmodel

import com.google.api.services.drive.model.File
import com.harshk.splitmates.core.mvi.UiEffect
import com.harshk.splitmates.core.mvi.UiEvent
import com.harshk.splitmates.core.mvi.UiState
import kotlinx.coroutines.flow.MutableStateFlow

class MainContract {
    sealed class Event: UiEvent {
        object LoadAccount: Event()

        object OnAddDialogShow: Event()
        object OnAddDialogHide : Event()

        data class OnCreateFile(val name: String): Event()
    }

    sealed class Effect: UiEffect {
        data class OnFileCreated(val file: File): Effect()
    }

    data class State(
        val name: String = "",
        val isAddDialogShowing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    ): UiState
}