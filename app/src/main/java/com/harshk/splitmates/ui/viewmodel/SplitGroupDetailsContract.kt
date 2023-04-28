package com.harshk.splitmates.ui.viewmodel

import com.harshk.splitmates.core.mvi.UiEffect
import com.harshk.splitmates.core.mvi.UiEvent
import com.harshk.splitmates.core.mvi.UiState
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.domain.model.Member
import kotlinx.coroutines.flow.MutableStateFlow

class SplitGroupDetailsContract {
    sealed class Event: UiEvent {
        data class SetupGroup(val group: Group): Event()

        object LoadMembers: Event()
    }

    sealed class Effect: UiEffect

    data class State(
        val group: MutableStateFlow<Group> = MutableStateFlow(Group()),
        val members: MutableStateFlow<List<Member>> = MutableStateFlow(emptyList())
    ) : UiState
}