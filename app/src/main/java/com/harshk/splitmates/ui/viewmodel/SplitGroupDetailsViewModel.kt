package com.harshk.splitmates.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.harshk.splitmates.core.mvi.MVIBaseViewModel
import com.harshk.splitmates.domain.model.Group
import com.harshk.splitmates.domain.usecase.LoadMembersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplitGroupDetailsViewModel @Inject constructor(
    val loadMembersUseCase: LoadMembersUseCase
) : MVIBaseViewModel<SplitGroupDetailsContract.State, SplitGroupDetailsContract.Event, SplitGroupDetailsContract.Effect>() {
    override fun createInitialState(): SplitGroupDetailsContract.State {
        return SplitGroupDetailsContract.State()
    }

    override fun handleEvent(event: SplitGroupDetailsContract.Event) {
        when (event) {
            is SplitGroupDetailsContract.Event.LoadMembers -> loadMembers()
            is SplitGroupDetailsContract.Event.SetupGroup -> setupGroup(event.group)
        }
    }
    private fun setupGroup(group: Group) {
        currentState.group.value = group
        setEvent(SplitGroupDetailsContract.Event.LoadMembers)
    }

    private fun loadMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = loadMembersUseCase(currentState.group.value.id)
            if (list != null) {
                currentState.members.value = list
            }
        }
    }

}