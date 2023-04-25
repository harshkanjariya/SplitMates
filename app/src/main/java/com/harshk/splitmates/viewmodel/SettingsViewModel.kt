package com.harshk.splitmates.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshk.splitmates.domain.model.SettingListItem
import com.harshk.splitmates.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {
    var splitFiles = MutableLiveData<List<SettingListItem>>()

    fun loadFiles() {
        viewModelScope.launch {
            splitFiles.value = useCase.listFiles()
        }
    }
}